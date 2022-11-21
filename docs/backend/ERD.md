# DB ERD 한눈에 보기

## ERD

![prolog_table](https://user-images.githubusercontent.com/64204666/196094763-215c5a4a-1a67-461a-b836-742474638d00.png)
[ERD 간략 설명](https://github.com/woowacourse/prolog/wiki/ERD-%EA%B0%84%EB%9E%B5-%EC%84%A4%EB%AA%85)

## 최신화

### 최신화 주기

코드를 작성하면서, 엔티티의 필드에 수정을 했거나 새로운 엔티티를 생성한 부분이 있다면 꼭! 아래 방법을 통해서 최신화를 진행해 주시기 바랍니다.

### 최신화 방법

인텔리제이를 이용해 ERD를 추출하는 방법입니다.

1. 인텔리제이를 통해 RDS에 연결합니다.

   ![ERD1](https://user-images.githubusercontent.com/64204666/196094782-b2e117a1-ea10-432d-a1bc-1dcd54d5adb4.png)


![ERD2](https://user-images.githubusercontent.com/64204666/196094789-041e5990-6dfa-446c-9965-b60b2ec697d1.png)

부여받은 Host 정보, User, Password를 입력합니다.

1. DB가 성공적으로 연결되면, 다이어그램을 시각화합니다.

   ![ERD3](https://user-images.githubusercontent.com/64204666/196094797-05666967-8029-4e5f-afdb-1daba4298638.png)

   ![ERD5](https://user-images.githubusercontent.com/64204666/196094934-a2d4b0e0-9fc5-4c55-8d71-63bc171f70c4.png)

2. 시각화한 다이어그램을 이미지 파일로 추출합니다.

   ![ERD4](https://user-images.githubusercontent.com/64204666/196094802-029530f1-d36c-424e-b34c-0fa40577ad6f.png)

3. 기능 추가/수정 PR 을 보낼때, 본 문서의 맨 위 이미지 파일을 같이 교체해 보냅니다.
