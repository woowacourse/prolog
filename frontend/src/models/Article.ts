export interface ArticleRequest {
  title: string;
  url: string;
  image: string;
}

export interface ArticleType {
  id: number;
  userName: string;
  title: string;
  url: string;
  createdAt: string;
}

export interface ArticleMetaOgRequest {
  url: string;
}
