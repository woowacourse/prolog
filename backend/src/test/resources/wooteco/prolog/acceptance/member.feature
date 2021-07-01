@api
Feature: 멤버 관련 기능

  Scenario: 멤버정보 조회하기
    Given "브라운"이 로그인을 하고
    When "서니"이 로그인을 하고
    When "브라운"의 멤버 정보를 조회하면
    Then 멤버 정보가 조회된다

  Scenario: 자신의 멤버정보 조회하기
    Given "브라운"이 로그인을 하고
    When 자신의 멤버 정보를 조회하면
    Then 멤버 정보가 조회된다

  Scenario: 자신의 정보를 수정하기
    Given "브라운"이 로그인을 하고
    When "브라운"이 자신의 유저네임을 "brown"으로 수정하면
    Then 유저네임이 "brown"으로 수정