export interface ArticleRequest {
  title: string;
  description: string;
  url: string;
  imageUrl: string;
}

export interface ArticleType {
  id: number;
  userName: string;
  title: string;
  isBookmarked: boolean;
  isLiked: boolean;
  url: string;
  createdAt: string;
  publishedAt: string;
  imageUrl: string;
}

export interface MetaOgRequest {
  url: string;
}

export interface MetaOgResponse {
  imageUrl: string;
  title: string;
  description: string;
}

export interface ArticleBookmarkPutRequest {
  articleId: number;
  bookmark: boolean;
}

export interface ArticleLikePutRequest {
  articleId: number;
  like: boolean;
}

export type Course = '프론트엔드' | '백엔드' | '안드로이드';

export type CourseFilter = Course | '전체보기';
