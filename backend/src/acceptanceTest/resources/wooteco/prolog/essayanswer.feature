@api
Feature: 로드맵 답변 관련 기능

    Background: 사전 작업
        Given "2022 백엔드 레벨1" 세션을 생성하고 - 1번 세션
        And 1번 세션에 "자바"라는 키워드를 순서 1, 중요도 1로 작성하고
        And 1번 세션, 1번 키워드에 퀴즈를 작성하고
        And "브라운"이 로그인을 하고

    Scenario: 답변 생성하기
        When 1번 퀴즈에 "varargs는 가변 인자"라는 답변을 생성하면
        Then 답변이 생성된다

    Scenario: 답변 조회하기
        Given 1번 퀴즈에 "varargs는 가변 인자"라는 답변을 생성하고
        When 1번 답변을 조회하면
        Then 답변이 조회된다

    Scenario: 답변 수정하기
        Given 1번 퀴즈에 "varargs는 가변 인자"라는 답변을 생성하고
        When 1번 답변을 "Integer은 wrapper 클래스"로 수정하면
        Then 답변이 수정된다

    Scenario: 답변 삭제하기
        Given 1번 퀴즈에 "varargs는 가변 인자"라는 답변을 생성하고
        When 1번 답변을 삭제하면
        Then 답변이 삭제된다

    Scenario: 퀴즈에 대한 모든 답변 조회하기
        Given 1번 퀴즈에 "varargs는 가변 인자"라는 답변을 생성하고
        When 1번 퀴즈에 대한 답변들을 조회하면
        Then 답변들이 조회된다
