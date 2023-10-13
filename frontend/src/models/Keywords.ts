// Request

type SessionAndKeywordId = {
  keywordId: number;
};

export type RoadmapRequest = {
  curriculumId: number;
};

export type KeywordRequest = SessionAndKeywordId;
export type ChildKeywordListRequest = SessionAndKeywordId;
export type QuizListByKeywordRequest = SessionAndKeywordId;

// Response
export interface CurriculumResponse {
  id: number;
  name: string;
}
export interface CurriculumListResponse {
  data: CurriculumResponse[];
}

export interface KeywordResponse {
  name: string;
  keywordId: number;
  order: number;
  importance: number;
  parentKeywordId: number | null;
  description: string;
  // 해당 키워드의 총 퀴즈의 갯수
  totalQuizCount: number;
  // 내가 완료한 해당 키워드의 퀴즈의 갯수
  doneQuizCount: number;
  // 키워드에 대해 추천 포스트 URL 목록
  recommendedPosts: RecommendedPostResponse[];
  childrenKeywords: KeywordResponse[];
}

export interface RecommendedPostResponse {
  id: number;
  url: string;
}

export interface RoadmapResponse {
  data: KeywordResponse[];
}

export interface SubKeywordListResponse {
  childrenKeywords: KeywordResponse[];
}

export interface Quiz {
  quizId: number;
  question: string;
  // 퀴즈에 대해서 답변을 달았는지 여부
  isLearning: boolean;
}

export interface QuizListResponse {
  keywordId: number;
  data: Quiz[];
}
