@api
Feature: 질문 관련 기능

    Background: 사전 작업
        Given "2025 백엔드 레벨1" 강의를 생성하고
        And "로또" 미션 등록을 하고

    Scenario: 질문 생성하기
        When "JUnit5와 AssertJ의 주요 차이점은 무엇인가?" 질문을 생성하면
        Then 질문이 생성된다

    Scenario: 질문 조회하기
        Given "JUnit5와 AssertJ의 주요 차이점은 무엇인가?" 질문을 생성하고
        When 질문을 조회하면
        Then 질문이 조회된다
