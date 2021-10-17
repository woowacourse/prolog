@api
Feature: 리포트 기능

  Background: 사전 작업
    Given 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고
    And "브라운"이 로그인을 하고
    And 3개의 스터디로그를 작성하고
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And 부모역량 "디자인"을 추가하고
    And "디자인"의 자식역량 "TDD"를 추가하고


  Scenario: 리포트 등록하기
    When 리포트를 등록하면
    Then 리포트가 등록된다

  Scenario: 리포트 수정하기
    And 리포트를 등록하고
    And 리포트를 수정하면
    Then 리포트가 수정된다
