@api
Feature: 태그 관련 기능

  Background: 사전 작업
    Given 미션 여러개를 생성하고
    And "웨지"가 로그인을 하고

  Scenario: 태그 작성하기
    When 포스트를 작성하면
    Then 태그도 작성된다

  Scenario: 태그 목록 조회하기
    Given 포스트 여러개를 작성하고
    When 태그 목록을 조회하면
    Then 태그 목록을 받는다