@api
Feature: 역량 학습로그 맵핑 기능

  Background: 사전 작업
    Given "브라운"이 로그인을 하고
    And 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And 부모역량 "디자인"을 추가하고
    And "디자인"의 자식역량 "TDD"를 추가하고
    And "새로운 스터디로그" 스터디로그를 작성하고

  Scenario: 역량 설정하기
    Given "새로운 스터디로그" 학습로그에 "프로그래밍" 역량을 맵핑하고
    When "새로운 스터디로그" 학습로그에 "프로그래밍", "TDD" 역량을 맵핑하면
    Then "새로운 스터디로그" 학습로그에 "프로그래밍", "TDD" 역량이 맵핑된다

  Scenario: 역량 포함된 학습로그 목록 조회
    Given "새로운 스터디로그" 학습로그에 "프로그래밍", "TDD" 역량을 맵핑하고
    When "브라운"이 작성한 학습로그에 역량정보를 포함하여 조회하면
    Then "프로그래밍", "TDD" 역량이 맵핑된 "새로운 스터디로그" 학습로그가 조회된다

  Scenario: 역량이 맵핑된 학습로그만 조회
    Given "또다른 스터디로그" 스터디로그를 작성하고
    And "새로운 스터디로그" 학습로그에 "프로그래밍", "TDD" 역량을 맵핑하고
    When "브라운"이 작성한 역량이 맵핑된 학습로그를 조회하면
    Then "프로그래밍", "TDD" 역량이 맵핑된 "새로운 스터디로그" 학습로그가 조회된다

  Scenario: 중복된 이름의 역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When 부모역량 "프로그래밍"을 추가하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 자식 역량 이름을 부모 역량과 같게 등록 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍"의 자식역량 "프로그래밍"을 추가하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 부모역량과 다른 색상의 자식역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍"의 자식역량 "프로그래밍과 색상이 다른 사고력"을 추가하면
    Then 부모역량과 자식역량의 색상 불일치 예외가 발생한다.

  Scenario: 다른 부모역량과 중복된 색상의 역량 추가 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    When 부모역량 "프로그래밍과 색상이 같은 창의력"을 추가하면
    Then 부모역량 색상 중복 관련 예외가 발생한다.

  Scenario: 부모역량 조회하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When 부모 역량 목록을 조회하면
    Then 부모 역량 목록을 받는다.

  Scenario: 사용자의 역량 정보 조회하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: 역량 수정하기
    And 부모역량 "프로그래밍"을 추가하고
    When "프로그래밍" 역량을 이름 "브라운", 설명 "피의 전사 브라운", 색상 "붉은 색" 으로 수정하면
    Then 이름 "브라운", 설명 "피의 전사 브라운", 색상 "붉은 색" 역량이 포함된 역량 목록을 받는다.

  Scenario: 자식 역량 이름을 부모 역량과 같게 수정 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "언어" 역량을 이름 "프로그래밍", 설명 "피의 전사 브라운", 색상 "붉은 색" 으로 수정하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 부모 역량 수정 시 자식 역량 색상 함께 변경
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And "프로그래밍" 역량을 이름 "브라운", 설명 "피의 전사 브라운", 색상 "붉은 색" 으로 수정하고
    When 역량 목록을 조회하면
    Then "브라운"의 자식역량 "언어"도 "붉은 색"으로 바뀐다.

  Scenario: 자식 역량 수정 시 색상 변경 무시
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And "언어" 역량을 이름 "언어공주", 설명 "바다의 언어공주", 색상 "파란 색" 으로 수정하고
    When 역량 목록을 조회하면
    Then 자식역량 "언어공주"의 색상은 "파란 색"으로 바뀌지 않는다.

  Scenario: 다른 역량과 중복된 이름으로 수정 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    And 부모역량 "디자인"을 추가하고
    When "프로그래밍" 역량을 이름 "디자인", 설명 "피의 전사 브라운", 색상 "붉은 색" 으로 수정하면
    Then 역량 이름 중복 관련 예외가 발생한다.

  Scenario: 다른 역량과 중복된 색상으로 수정 시 예외 발생
    And 부모역량 "프로그래밍"을 추가하고
    And 부모역량 "디자인"을 추가하고
    And "프로그래밍" 역량을 이름 "프로그래밍", 설명 "피의 전사 브라운", 색상 "무지개 색" 으로 수정하고
    And "디자인" 역량을 이름 "디자인", 설명 "디자이너 브라운", 색상 "무지개 색" 으로 수정하면
    Then 부모역량 색상 중복 관련 예외가 발생한다.

  Scenario: (부모) 역량 삭제하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "프로그래밍" 역량이 포함되지 않은 목록을 받는다.

  Scenario: (자식) 역량 삭제하기
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And "언어" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "언어" 역량이 포함되지 않은 목록을 받는다.

  Scenario: 부모 역량 삭제 시 자식 역량도 모두 삭제
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And "프로그래밍" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then 비어있는 역량 목록을 받는다.

  Scenario: (백엔드) 기본 역량 등록하기
    And 관리자가 기본 역량 "Java"을 "be" 과정으로 추가하고
    And "be" 과정으로 기본 역량을 등록하고
    When 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: (백엔드) 기본 역량 삭제하기
    And 관리자가 기본 역량 "Java"을 "be" 과정으로 추가하고
    And "be" 과정으로 기본 역량을 등록하고
    And "Java" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "Java" 역량이 포함되지 않은 목록을 받는다.

  Scenario: (프론트엔드) 기본 역량 등록하기
    And 관리자가 기본 역량 "JavaScript"을 "fe" 과정으로 추가하고
    And "fe" 과정으로 기본 역량을 등록하고
    When 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: (프론트엔드) 기본 역량 삭제하기
    And 관리자가 기본 역량 "JavaScript"을 "fe" 과정으로 추가하고
    And "fe" 과정으로 기본 역량을 등록하고
    And "JavaScript" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "JavaScript" 역량이 포함되지 않은 목록을 받는다.

  Scenario: 잘못된 과정으로 기본 역량 등록 시 예외 발생
    When "잘못된" 과정으로 기본 역량을 등록하면
    Then 기본 역량 조회 실패 관련 예외가 발생한다.
