@api
Feature: 아티클 관련 기능

    Background: 사전 작업
        Given "브라운"이 로그인을 하고

    Scenario: 아티클을 작성하기
        When 아티클을 작성하면
        Then 아티클이 작성된다

#    Scenario: 아티클을 전체 조회하기
#        Given 아티클을 여러개 작성하고
#        When 아티클을 전체 조회 하면
#        Then 아티클이 전체 조회 된다

    Scenario: 아티클을 수정하기
        Given 아티클을 여러개 작성하고
        When 1번 아티클을 수정하면
        Then 아티클이 수정된다

    Scenario: 아티클을 삭제하기
        Given 아티클을 여러개 작성하고
        When 1번 아티클을 삭제하면
        Then 아티클이 삭제된다

    Scenario: Url og태그 파싱하기
        When Url을 입력하면
        Then og태그를 파싱해서 반환한다.

    Scenario: 아티클을 북마크로 등록하기
        Given 아티클이 작성되어 있고
        When 1번 아티클에 북마크 요청을 보내면
        Then 아티클에 북마크가 등록된다

    Scenario: 아티클에 좋아요를 등록하기
        Given 아티클이 작성되어 있고
        When 1번 아티클에 좋아요 요청을 보내면
        Then 아티클에 좋아요가 등록된다

    Scenario: 아티클 조회수 추가하기
        Given 아티클이 작성되어 있고
        When 1번 아티클에 조회수 추가요청을 보내면
        Then 아티클의 조회수가 증가한다
