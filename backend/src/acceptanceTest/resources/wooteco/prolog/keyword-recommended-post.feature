@api
Feature: 로드맵 키워드 추천 포스트 관련 기능

    Background: 사전 작업
        Given "2022 백엔드 레벨1" 세션을 생성하고 - 1번 세션
        And 1번 세션에 "자바"라는 키워드를 순서 1, 중요도 2로 작성하고

    Scenario: 키워드 추천 포스트 생성하기
        When 1번 키워드에 대해 추천 포스트 "https://javajavajava"를 작성하면
        Then 추천 포스트가 생성된다

    Scenario: 키워드 추천 포스트 수정하기
        Given 1번 키워드에 대해 추천 포스트 "https://javajavajava"를 작성하고
        When 1번 키워드에 대한 1번 추천 포스트를 "https://java2java2"로 수정하면
        Then 추천 포스트가 수정된다

    Scenario: 키워드 추천 포스트 삭제하기
        When 1번 키워드에 대한 1번 추천 포스트를 삭제하면
        Then 추천 포스트가 삭제된다
