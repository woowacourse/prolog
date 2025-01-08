@api
Feature: 스터디로그 관련 기능

    Background: 사전 작업
        Given 세션 여러개를 생성하고
        And 미션 여러개를 생성하고
        And "브라운"이 크루역할로 로그인을 하고

    Scenario: 스터디로그 작성하기
        When 스터디로그를 작성하면
        Then 스터디로그가 작성된다

    Scenario: 세션, 미션 없이 스터디로그 작성하기
        When 세션과 미션 없이 스터디로그를 작성하면
        Then 스터디로그가 작성된다

    Scenario: 스터디로그 세션 수정하기
        Given 스터디로그를 작성하고
        When 스터디로그 세션을 2로 수정하면
        Then 스터디로그 세션이 2로 수정된다

    Scenario: 스터디로그 미션 수정하기
        Given 스터디로그를 작성하고
        When 스터디로그 미션을 2로 수정하면
        Then 스터디로그 미션이 2로 수정된다
