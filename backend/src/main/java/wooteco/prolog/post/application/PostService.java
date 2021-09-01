package wooteco.prolog.post.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.application.dto.CalendarPostResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostsResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.repository.PostRepository;
import wooteco.prolog.post.domain.repository.PostSpecification;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.post.exception.PostNotFoundException;
import wooteco.prolog.posttag.application.PostTagService;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.domain.Tags;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MissionService missionService;
    private final PostTagService postTagService;
    private final MemberService memberService;
    private final TagService tagService;

    public PostsResponse findPostsWithFilter(
            List<Long> levelIds,
            List<Long> missionIds,
            List<Long> tagIds,
            List<String> usernames,
            Pageable pageable) {

        Specification<Post> specs =
                PostSpecification.findByLevelIn(levelIds)
                .and(PostSpecification.equalIn("mission", missionIds))
                .and(PostSpecification.findByTagIn(tagIds))
                .and(PostSpecification.findByUsernameIn(usernames))
                .and(PostSpecification.distinct(true));

        Page<Post> posts = postRepository.findAll(specs, pageable);

        return PostsResponse.of(posts);
    }

    public PostsResponse findPostsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return PostsResponse.of(postRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<PostResponse> insertPosts(Member member, List<PostRequest> postRequests) {
        if (postRequests.size() == 0) {
            throw new PostArgumentException();
        }
        final Member byId = memberService.findById(member.getId());

        return postRequests.stream()
                .map(postRequest -> insertPost(byId, postRequest))
                .collect(toList());
    }

    private PostResponse insertPost(Member member, PostRequest postRequest) {
        final Member foundMember = memberService.findById(member.getId());
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        Mission mission = missionService.findById(postRequest.getMissionId());

        Post requestedPost = new Post(foundMember,
                postRequest.getTitle(),
                postRequest.getContent(),
                mission,
                tags.getList());

        Post createdPost = postRepository.save(requestedPost);
        foundMember.addTags(tags);

        return PostResponse.of(createdPost);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    @Transactional
    public void updatePost(Member member, Long postId, PostRequest postRequest) {
        final Member foundMember = memberService.findById(member.getId());
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(foundMember);
        final Tags originalTags = tagService.findByPostAndMember(post, foundMember);

        Mission mission = missionService.findById(postRequest.getMissionId());
        Tags newTags = tagService.findOrCreate(postRequest.getTags());

        post.update(postRequest.getTitle(), postRequest.getContent(), mission, newTags);
        foundMember.updateTags(originalTags, newTags);
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        final Member foundMember = memberService.findById(member.getId());
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(foundMember);

        postRepository.delete(post);
        final Tags tags = tagService.findByPostAndMember(post, foundMember);
        foundMember.removeTag(tags);
    }

    public List<CalendarPostResponse> findCalendarPosts(String username, LocalDate localDate) {
        final Member member = memberService.findByUsername(username);
        final LocalDateTime start = localDate.with(firstDayOfMonth()).atStartOfDay();
        final LocalDateTime end = localDate.with(lastDayOfMonth()).atTime(LocalTime.MAX);

        return postRepository.findByMemberBetween(member, start, end)
                .stream()
                .map(CalendarPostResponse::of)
                .collect(toList());
    }

    public int countPostByMember(Member member) {
        return postRepository.countByMember(member);
    }
}
