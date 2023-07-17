@api
Feature: 레벨 로그 관련 기능

    Background: 사전 작업
        Given "브라운"이 로그인을 하고

    Scenario: 레벨 로그 작성하기
        When 레벨로그를 작성하면
        Then 레벨로그가 조회된다

    Scenario: 레벨 로그 삭제하기
        When 레벨로그를 작성하고
        And 레벨로그를 삭제하면
        Then 레벨로그가 삭제된다

    Scenario: 레벨 로그 수정하기
        When 레벨로그를 작성하고
        And 레벨로그를 수정하면
        Then 레벨로그가 수정된다


    Scenario: 레벨 로그 목록 조회하기
        When 레벨로그를 여러개 작성하면
        Then 레벨로그가 여러개 조회된다

