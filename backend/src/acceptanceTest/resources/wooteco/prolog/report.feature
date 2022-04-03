@api
Feature: 리포트 기능

  Background: 사전 작업
    Given 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고
    And "브라운"이 로그인을 하고
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And 부모역량 "디자인"을 추가하고
    And "디자인"의 자식역량 "TDD"를 추가하고
    And "새로운 스터디로그" 스터디로그를 작성하고
    And "새로운 스터디로그" 학습로그에 "프로그래밍", "TDD" 역량을 맵핑하면

  Scenario: 리포트 등록하기
    When 리포트를 등록하면
    Then 리포트가 등록된다

  Scenario: 리포트 조회하기
    And 리포트를 등록하고
    When "브라운"의 리포트 목록을 조회하면
    Then 리포트 목록이 조회된다

  Scenario: 리포트 조회 시 삭제한 학습로그가 포함되어있음
    And 리포트를 등록하고
    And 1번째 스터디로그를 삭제하면
    And 1번째 리포트를 조회하면
    Then 리포트가 조회된다

  Scenario: 리포트 수정하기
    And 리포트를 등록하고
    When 리포트를 수정하면
    Then 리포트가 수정된다

#  Scenario: 단순 리포트 조회하기
#    And 리포트를 등록하고
#    When "브라운"의 단순 리포트 목록을 조회하면
#    Then 단순 리포트 목록이 조회된다
#
#  Scenario: 리포트 목록 조회하기
#    And 리포트를 등록하고
#    And 리포트를 등록하고
#    And 대표 리포트를 등록하고
#    When "브라운"의 리포트 목록을 조회하면
#    Then 대표리포트, 생성날짜 순으로 정렬되어 반환된다
