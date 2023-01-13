// Request

type SessionAndKeywordId = {
  sessionId: number;
  keywordId: number;
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
  parentKeywordId: number;
  description: string;
  childrenKeywords: KeywordResponse[] | null;
}

export interface TopKeywordListResponse {
  data: KeywordResponse[];
}
export interface SubKeywordListResponse {
  childrenKeywords: KeywordResponse[];
}

export interface Quiz {
  quizId: number;
  question: string;
}

export interface QuizListResponse {
  data: Quiz[];
}
