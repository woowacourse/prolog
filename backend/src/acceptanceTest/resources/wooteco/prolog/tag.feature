@api
Feature: 태그 관련 기능

    Background: 사전 작업
        Given 세션 여러개를 생성하고
        And 미션 여러개를 생성하고
        And "웨지"가 크루역할로 로그인을 하고

    Scenario: 태그 작성하기
        When 스터디로그를 작성하면
        Then 태그도 작성된다

    Scenario: 태그 목록 조회하기
        Given 스터디로그 여러개를 작성하고
        When 태그 목록을 조회하면
        Then 태그 목록을 받는다
