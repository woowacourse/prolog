package wooteco.prolog.studylog.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studyLogDocument.application.StudyLogDocumentService;
import wooteco.prolog.studylog.application.dto.PostRequest;
import wooteco.prolog.studylog.application.dto.PostResponse;
import wooteco.prolog.studylog.application.dto.PostsResponse;
import wooteco.prolog.studylog.application.dto.search.StudyLogSearchParameters;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Post;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.PostRepository;
import wooteco.prolog.studylog.domain.repository.PostSpecification;
import wooteco.prolog.studylog.exception.PostArgumentException;
import wooteco.prolog.studylog.exception.PostNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final StudyLogDocumentService studyLogDocumentService;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;

    public PostsResponse findPostsWithFilter(StudyLogSearchParameters studyLogSearchParameters) {

        final String searchKeyword = studyLogSearchParameters.getSearch();
        final Pageable pageable = studyLogSearchParameters.getPageable();

        List<Long> studyLogIds = Collections.emptyList();
        if (isSearch(searchKeyword)) {
            studyLogIds = studyLogDocumentService.findBySearchKeyword(searchKeyword, pageable);
        }

        if (studyLogSearchParameters.hasOnlySearch()) {
            return PostsResponse.of(postRepository.findByIdIn(studyLogIds, pageable));
        }

        Page<Post> posts = postRepository.findAll(
            makeSpecifications(studyLogSearchParameters, studyLogIds),
            pageable);

        return PostsResponse.of(posts);
    }

    private Specification<Post> makeSpecifications(
        StudyLogSearchParameters studyLogSearchParameters, List<Long> studyLogIds
    ) {
        return PostSpecification.findByLevelIn(studyLogSearchParameters.getLevels())
            .and(PostSpecification.equalIn("id", studyLogIds,
                isSearch(studyLogSearchParameters.getSearch())))
            .and(PostSpecification.equalIn("mission", studyLogSearchParameters.getMissions()))
            .and(PostSpecification.findByTagIn(studyLogSearchParameters.getTags()))
            .and(PostSpecification.findByUsernameIn(studyLogSearchParameters.getUsernames()))
            .and(PostSpecification.distinct(true));
    }

    private boolean isSearch(String searchKeyword) {
        return Objects.nonNull(searchKeyword) && !searchKeyword.isEmpty();
    }

    public PostsResponse findPostsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return PostsResponse.of(postRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<PostResponse> insertPosts(Member member, List<PostRequest> postRequests) {
        if (postRequests.isEmpty()) {
            throw new PostArgumentException();
        }

        return postRequests.stream()
            .map(postRequest -> insertPost(member, postRequest))
            .collect(Collectors.toList());
    }

    private PostResponse insertPost(Member member, PostRequest postRequest) {
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        Mission mission = missionService.findById(postRequest.getMissionId());

        Post requestedPost = new Post(member,
            postRequest.getTitle(),
            postRequest.getContent(),
            mission,
            tags.getList());

        Post createdPost = postRepository.save(requestedPost);
        studyLogDocumentService.save(createdPost.toStudyLogDocument());

        return PostResponse.of(createdPost);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    @Transactional
    public void updatePost(Member member, Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);
        Mission mission = missionService.findById(postRequest.getMissionId());
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        post.update(postRequest.getTitle(), postRequest.getContent(), mission, tags);
        studyLogDocumentService.save(post.toStudyLogDocument());
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        postRepository.delete(post);
        studyLogDocumentService.delete(post.toStudyLogDocument());
    }
}
