@api
Feature: 로그인 기능

  Scenario: 미션 등록하기
    Given "브라운"이 로그인을 하고
    When "이런저런" 미션 등록을 하면
    Then 미션이 등록된다

  Scenario: 미션 조회하기
    Given "브라운"이 로그인을 하고
    And "이런저런" 미션 등록을 하고
    When 미션 목록을 조회하면
    Then 미션 목록을 받는다

  Scenario: 중복된 이름으로 미션 등록하기
    Given "브라운"이 로그인을 하고
    And "이런저런" 미션 등록을 하고
    When "이런저런" 미션 등록을 하면
    Then 미션을 실패한다