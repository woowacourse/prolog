@api
Feature: 포스트 관련 기능

  Background: 사전 작업
    Given 미션 여러개를 생성하고
    And "브라운"이 로그인을 하고

  Scenario: 포스트 작성하기
    When 포스트를 작성하면
    Then 포스트가 작성된다

  Scenario: 포스트 수정하기
    Given 포스트 여러개를 작성하고
    When 1번째 포스트를 수정하면
    Then 1번째 포스트가 수정된다

  Scenario: 다른 계정으로 포스트 수정하기
    Given 포스트 여러개를 작성하고
    And "웨지"가 로그인을 하고
    When 1번째 포스트를 수정하면
    Then 에러 응답을 받는다

  Scenario: 포스트 목록 조회하기
    Given 포스트 여러개를 작성하고
    When 포스트 목록을 조회하면
    Then 포스트 목록을 받는다

  Scenario: 포스트 단건 조회하기
    Given 포스트 여러개를 작성하고
    When 1번째 포스트를 조회하면
    Then 1번째 포스트가 조회된다

#  Scenario: 포스트 삭제하기
#    * 포스트 여러개를 작성하고
#    * 1번째 포스트를 삭제하면
#    * 1번째 포스트가 삭제된다
