@api
Feature: 스터디로그 오버뷰 기능

  Scenario: 나의 태그 목록 조회하기
    Given "브라운"이 로그인을 하고
    When 나의 태그 목록을 조회하면
    Then 나의 태그 목록이 조회된다
