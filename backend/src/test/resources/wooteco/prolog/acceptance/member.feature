@api
Feature: 멤버 관련 기능

  Scenario: 자신의 멤버정보 조회하기
    Given "브라운"이 로그인을 하고
    When 자신의 멤버 정보를 조회하면
    Then 멤버 정보가 조회된다