export interface KeywordResponse {
  keywordId: number;
  order: number;
  importance: number;
  parentKeywordId: number;
  description: string;
  childrenKeywords: KeywordResponse[] | null;
}

export interface KeywordListResponse {
  data: KeywordResponse[];
}

export interface Quiz {
  quizId: number;
  question: string;
}

export interface QuizListResponse {
  data: Quiz[];
}
