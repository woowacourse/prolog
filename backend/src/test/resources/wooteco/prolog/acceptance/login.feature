@api
Feature: 로그인 기능

    Scenario: 로그인하기
    When "브라운"이 로그인을 하면
    Then 액세스 토큰을 받는다