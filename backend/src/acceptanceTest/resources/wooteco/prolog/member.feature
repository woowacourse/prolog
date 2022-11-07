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

  Scenario: 등업 요청 및 목록 조회
    Given "엘라"가 로그인을 하고
    Given "현구막"가 로그인을 하고
    Given "브라운"이 로그인을 하고
    And 등업을 요청하고
    Given "서니"이 로그인을 하고
    And 등업을 요청하고
    When 등업 요청 목록을 조회하면
    Then 등업 요청 목록을 조회한다
    | 브라운 |
    | 서니 |

  Scenario: 등업 요청 및 승인
    Given "브라운"이 로그인을 하고
    And 등업을 요청하고
    When "브라운"의 등업을 승인하면
    Then "브라운"의 등업을 확인한다
