@api
Feature: 역량 기능

    Scenario: 역량 추가 하기
    Given "브라운"이 로그인을 하고
    When 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍"의 자식역량 "언어"를 추가하고
    When 부모역량 "디자인"을 추가하고
    When "디자인"의 자식역량 "TDD"를 추가하면
    Then 역량이 추가된다.
