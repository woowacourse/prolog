# 로그인 기능 요구사항

- [x] POST - 클라이언트로부터 깃허브 코드를 받는다.
- [x] 깃허브 서버에 엑세스 토큰을 요청한다.
    - [ ] 유효하지 않은 코드일 경우 401과 에러 반환한다.

- [x] 엑세스 토큰으로 깃허브에게 사용자 정보를 요청한다.
- [ ] 서비스에 이미 등록된 멤버인지 확인한다.
    - [ ] 처음 방문한 사용자인 경우 사용자 정보를 DB에 저장한다.
    - [ ] 기존 멤버인 경우 DB에서 정보를 비교한다.

- [x] 토큰 생성
    - [x] 토큰에 사용자 정보를 넣는다.
    - [ ] DB 구현 후 payload 정보 변경 (githubId -> id)

- [x] 클라이언트에게 200 status code와 함께 토큰 전달].

- [ ] GET - 멤버 정보 요청에 응답한다.
    - [ ] header로 들어온 토큰을 검증한다.
    - [ ] 응답에 id, nickname, imageUrl, role이 포함된다.

- [ ] MEMBER
    - [ ] Long id
    - [ ] String nickname
    - [ ] Long githubId
    - [ ] String imageUrl
    - [ ] Role role
  
- [x] ROLE
    - [x] INVALID_MEMBER
    - [x] CREW
    - [x] COACH

- [x] TOKEN
    - [x] 토큰 생성 시간
    - [x] 토큰 만료 시간
    - [ ] 사용자 ID
    - [x] Role