@api
Feature: 세션 멤버 기능
  Scenario: 강의에 자신을 등록하는 기능
    Given "브라운" 이 로그인을 하공
    Given "강의1" 추가하면
    When 1 강의에 자신을 등록하면
    Then 강의에 내가 추가된다.
