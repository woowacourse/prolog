package wooteco.studylog.log.service;

import org.springframework.stereotype.Service;
import wooteco.studylog.log.StudyLog;
import wooteco.studylog.log.dao.StudyLogDao;
import wooteco.studylog.log.web.controller.dto.CategoryResponse;
import wooteco.studylog.log.web.controller.dto.LogRequest;
import wooteco.studylog.log.web.controller.dto.LogResponse;

import java.util.List;

@Service
public class StudyLogService {
    private final StudyLogDao studyLogDao;

    public StudyLogService(StudyLogDao studyLogDao) {
        this.studyLogDao = studyLogDao;
    }

    public List<LogResponse> findAll() {
        List<StudyLog> all = studyLogDao.findAll();
        return null;
    }

    public List<LogResponse> insertLogs(List<LogRequest> logRequests) {
//        studyLogDao.insert(studyLogs)
        return null; // TODO : 첫번째 카드만 반환함
    }

    public LogResponse findById(Long logId) {
        studyLogDao.findById(logId);
        return null;
    }

    public List<CategoryResponse> findAllCategories() {
        studyLogDao.findAllCategories();
        return null;
    }
}
