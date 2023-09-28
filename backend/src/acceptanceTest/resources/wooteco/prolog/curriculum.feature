@api
Feature: 커리큘럼 관련 기능

  Background: 사전 작업
    Given "수달"이 크루역할로 로그인을 하고

  Scenario: 커리큘럼 생성하기
    When 커리큘럼을 생성하면
    Then 커리큘럼이 생성된다

  Scenario: 커리큘럼 목록 조회하기
    When 커리큘럼을 생성하고
    And 커리쿨럼을 조회하면
    Then 커리큘럼이 조회된다

  Scenario: 커리큘럼 수정하기
    When 커리큘럼을 생성하고
    And 1번 커리쿨럼을 수정하면
    Then 커리큘럼이 수정된다

  Scenario: 커리큘럼 삭제하기
    When 커리큘럼을 생성하고
    And 1번 커리쿨럼을 삭제하면
    Then 커리큘럼이 삭제된다

