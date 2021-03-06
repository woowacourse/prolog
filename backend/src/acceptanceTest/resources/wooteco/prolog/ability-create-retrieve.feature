@api
Feature: 역량 기능

  Background: 사전 작업
    Given "브라운"이 로그인을 하고

  Scenario: 역량 추가하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And 부모역량 "디자인"을 추가하고
    And "디자인"의 자식역량 "TDD"를 추가하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: 중복된 이름의 역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When 부모역량 "프로그래밍"을 추가하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 자식 역량 이름을 부모 역량과 같게 등록 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍"의 자식역량 "프로그래밍"을 추가하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 부모역량과 다른 색상의 자식역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍"의 자식역량 "프로그래밍과 색상이 다른 사고력"을 추가하면
    Then 부모역량과 자식역량의 색상 불일치 예외가 발생한다.

  Scenario: 다른 부모역량과 중복된 색상의 역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When 부모역량 "프로그래밍과 색상이 같은 창의력"을 추가하면
    Then 부모역량 색상 중복 관련 예외가 발생한다.

  Scenario: 부모역량 조회하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When 부모 역량 목록을 조회하면
    Then 부모 역량 목록을 받는다.

  Scenario: 사용자의 역량 정보 조회하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.