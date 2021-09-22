@api
Feature: 스터디로그 오버뷰 기능

  Background: 사전 작업
    Given 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고
    And "브라운"이 로그인을 하고

  Scenario: 해당 유저의 태그 목록 조회하기
    Given 포스트 여러개를 작성하고
    When "브라운"의 태그 목록을 조회하면
    Then 해당 유저의 태그 목록이 조회된다

  Scenario: 해당 유저의 포스트 목록 월별로 조회하기
    Given 포스트 여러개를 작성하고
    When "브라운"의 이번 달 포스트 목록을 조회하면
    Then 해당 유저의 포스트 목록이 조회된다
