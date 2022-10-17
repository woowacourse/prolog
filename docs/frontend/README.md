# 프롤로그 프론트엔드

<div align="middle">
  <img src="https://img.shields.io/badge/version-1.5.0-blue" alt="template version"/>
  <img src="https://img.shields.io/badge/Node.js-16.4.0-green"/>
  <img src="https://img.shields.io/badge/language-Typescript-blue.svg?style=flat-square"/>
  <img src="https://img.shields.io/badge/React-17.0.2-blue"/>
</div>

<br/>

## 시작하기

### NVM (Node Version Manager)

[NVM](https://github.com/nvm-sh/nvm)을 사용하여 Node.js 버전을 맞춘다. [설치 방법](https://fine-town-5b1.notion.site/e3f5ff4b98224947b6b6f8698f4a3821) <br>
프롤로그의 Node.js 버전은 16.4.0 이다.

<br />

### 서버 구동하기

프롤로그 프론트엔드 서버는 local과 dev가 있다. <br />
local은 **msw**를 통해 개발하고, dev는 **백엔드 서버**를 함께 구동하여 개발한다.

- local
  ```bash
  yarn start:local
  ```
- dev
  ```bash
  yarn start:dev
  ```
  [백엔드 서버 구동방법](https://fine-town-5b1.notion.site/c193094875eb4729bb8f48d9aebd86c4)

<br />

## 폴더 구조

```
📦src
 ┣ 📂apis # fetcher
 ┣ 📂assets # 정적 파일
 ┣ 📂components
 ┃ ┣ 📂@shared # UI 컴포넌트
 ┃ ┗ 📂DomainComponent(...) # 도메인 관련 컴포넌트
 ┣ 📂configs
 ┣ 📂constants
 ┣ 📂contexts
 ┣ 📂enumerations
 ┣ 📂hooks
 ┃ ┣ 📂Domain # 도메인 로직 관련 커스텀 훅
 ┃ ┣ 📂queries # 도메인 관련 api call 커스텀 훅
 ┃ ┗ 📜useCustomHook(...) # 범용적인 커스텀 훅
 ┣ 📂mocks
 ┣ 📂models # 도메인 관련 타입과 인터페이스
 ┣ 📂pages # 페이지 컴포넌트
 ┣ 📂redux
 ┣ 📂service
 ┣ 📂styles
 ┣ 📂types
 ┗ 📂utils
```

<br />

## 컨벤션

### 커밋 컨벤션

```
[feat:  Page 컴포넌트 구현]

- `npx create-react-app`
- 라이브러리 설치

```

- feat: 기능 구현
- chore: 라이브러리, 배포 관련
- fix: 에러 처리
- refactor: 리팩터링(기능 추가 x)
- docs: 문서

<br />

### 코드 컨벤션

- 컴포넌트 선언

```jsx
const App = () => {
  return <div></div>;
};

export default App;
```

- react-query를 이용한 api call custom hook 선언

```ts
//useQuery
export const useGetStudylogs = (data?, options?) =>
  useQuery([QUERY_KEY], () => getStudylogs(), options);

export const getStudylogs = async () => {
  const { data } = await client.get(url);

  return data;
};

//useMutation
export const useEditStudylog = (data?, options?) =>
  useMutation(() => editStudylog(), options);

export const editStudylog = () => client.put(url);
```
