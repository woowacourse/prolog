@api
Feature: 역량 기능

  Background: 사전 작업
    Given "브라운"이 로그인을 하고

  Scenario: (백엔드) 기본 역량 등록하기
    And 관리자가 기본 역량 "Java"을 "be" 과정으로 추가하고
    And "be" 과정으로 기본 역량을 등록하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: (백엔드) 기본 역량 삭제하기
    And 관리자가 기본 역량 "Java"을 "be" 과정으로 추가하고
    And "be" 과정으로 기본 역량을 등록하고
    And "Java" 역량을 삭제하고
    When "브라운"의 역량 목록을 조회하면
    Then "Java" 역량이 포함되지 않은 목록을 받는다.

  Scenario: (프론트엔드) 기본 역량 등록하기
    And 관리자가 기본 역량 "JavaScript"을 "fe" 과정으로 추가하고
    And "fe" 과정으로 기본 역량을 등록하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: (프론트엔드) 기본 역량 삭제하기
    And 관리자가 기본 역량 "JavaScript"을 "fe" 과정으로 추가하고
    And "fe" 과정으로 기본 역량을 등록하고
    And "JavaScript" 역량을 삭제하고
    When "브라운"의 역량 목록을 조회하면
    Then "JavaScript" 역량이 포함되지 않은 목록을 받는다.

  Scenario: 잘못된 과정으로 기본 역량 등록 시 예외 발생
    When "잘못된" 과정으로 기본 역량을 등록하면
    Then 기본 역량 조회 실패 관련 예외가 발생한다.