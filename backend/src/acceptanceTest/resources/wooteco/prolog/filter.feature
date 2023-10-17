@api
Feature: 필터 관련 기능

  Background: 사전 작업
    Given "현구막"이 크루역할로 로그인을 하고
    And "브라운"이 크루역할로 로그인을 하고
    And "서니"가 크루역할로 로그인을 하고

  Scenario: 필터 목록 조회하기
    When 필터요청이 들어오면
    Then nickname을 기준으로 멤버데이터들을 오름차순 정렬하여 반환한다
