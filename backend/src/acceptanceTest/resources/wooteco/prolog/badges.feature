@api
Feature: 배지 관련 기능

    Background: 사전 작업
        Given 세션 여러개를 생성하고
        And 미션 여러개를 생성하고

    Scenario: 존재하지 않는 멤버로 배지 조회하기
        When 존재하지 않는 멤버의 배지를 조회하면
        Then 존재하지 않는 멤버 관련 예외가 발생한다

    Scenario: 배지목록 조회하기
        Given "브라운"이 크루역할로 로그인을 하고
        And 여러개의 스터디로그를 작성하고
        When 배지를 조회하면
        Then 열정왕 배지를 부여한다.


