package wooteco.prolog.studylog.domain;

import java.util.function.Predicate;

public enum Curriculum {

//    ALL("전체", Curriculum::isAll),
    FRONTEND(".*프론트엔드.*", Curriculum::isFrontEnd),
    BACKEND(".*백엔드.*", Curriculum::isBackEnd);

    private final String regexPattern;
    private final Predicate<String> predicate;

    Curriculum(String regexPattern, Predicate<String> predicate) {
        this.regexPattern = regexPattern;
        this.predicate = predicate;
    }

    public boolean findCurriculum(String name) {
        return this.predicate.test(name);
    }

    /**
     *  전체 카테고리가 추가되면 해당 로직을 활성화 할 계획
     *  현재는 해당 로직을 사용하지 않고 프론트엔드 + 백엔드 데이터 20개를 리스트로 반환하여 공통 로직 처리
     *  @author rookie(wishoon)
     */
//    private static boolean isAll(String name) {
//        return false;
//    }

    private static boolean isFrontEnd(String inputName) {
        return inputName.matches(FRONTEND.regexPattern);
    }

    private static boolean isBackEnd(String inputName) {
        return inputName.matches(BACKEND.regexPattern);
    }

    public String getRegexPattern() {
        return regexPattern;
    }
}
