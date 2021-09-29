@api
Feature: 멤버 관련 기능

  Scenario: 자신의 멤버정보 조회하기
    Given "브라운"이 로그인을 하고
    When 자신의 멤버 정보를 조회하면
    Then 멤버 정보가 조회된다

  Scenario: 자신의 정보를 수정하기
    Given "브라운"이 로그인을 하고
    When 자신의 닉네임을 "brown"으로 수정하면
    Then "브라운"의 닉네임이 "brown"으로 수정

  Scenario: 사용자의 역량 정보 조회하기
    Given "브라운"이 로그인을 하고
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.