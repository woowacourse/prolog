package wooteco.prolog.post.service;

import org.springframework.stereotype.Service;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.dao.StudyLogDao;
import wooteco.prolog.category.web.controller.dto.CategoryResponse;
import wooteco.prolog.post.web.controller.dto.PostRequest;
import wooteco.prolog.post.web.controller.dto.PostResponse;

import java.util.List;

@Service
public class PostService {
    private final StudyLogDao studyLogDao;

    public PostService(StudyLogDao studyLogDao) {
        this.studyLogDao = studyLogDao;
    }

    public List<PostResponse> findAll() {
        List<Post> all = studyLogDao.findAll();
        return null;
    }

    public List<PostResponse> insertLogs(List<PostRequest> postRequests) {
//        studyLogDao.insert(studyLogs)
        return null; // TODO : 첫번째 카드만 반환함
    }

    public PostResponse findById(Long logId) {
        studyLogDao.findById(logId);
        return null;
    }

    public List<CategoryResponse> findAllCategories() {
        studyLogDao.findAllCategories();
        return null;
    }
}
