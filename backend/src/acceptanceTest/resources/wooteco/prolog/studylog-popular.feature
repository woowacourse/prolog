@api
Feature: 인기있는 학습로그 관련 기능

    Background: 사전 작업
        Given 세션 여러개를 생성하고
        And 미션 여러개를 생성하고
        And "브라운"이 크루역할로 로그인을 하고
        And "브라운"을 멤버그룹과 그룹멤버에 등록하고

    Scenario: 인기 있는 순서로 스터디로그 목록 조회하기
        Given 스터디로그 여러개를 작성하고
        When 로그인된 사용자가 2번째 스터디로그를 좋아요 하고
        When 인기 있는 스터디로그 목록을 "2"개만큼 갱신하고
        Then 인기있는 스터디로그 목록 요청시 id "2, 1" 순서로 조회된다
