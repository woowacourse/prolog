export interface ArticleRequest {
  title: string;
  url: string;
  imageUrl: string;
}

export interface ArticleType {
  id: number;
  userName: string;
  title: string;
  isBookMarked: false;
  url: string;
  createdAt: string;
  imageUrl: string;
}

export interface MetaOgRequest {
  url: string;
}

export interface MetaOgResponse {
  imageUrl: string;
  title: string;
}

export interface ArticleBookmarkPutRequest {
  articleId: number;
  bookmark: boolean;
}

export type Course = '프론트엔드' | '백엔드' | '안드로이드';

export type CourseFilter = Course | '전체보기';
