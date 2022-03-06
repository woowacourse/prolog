@api
Feature: 강의 관련 기능

  Scenario: 강의 생성기능
    When "새로운이름" 강의를 추가하면
    Then "새로운이름" 강의가 추가된다

  Scenario: 강의 목록 조회하기
    Given 강의 여러개를 작성하고
    When 강의 목록을 조회하면
    Then 강의 목록을 받는다