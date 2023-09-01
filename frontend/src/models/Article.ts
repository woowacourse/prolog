export interface ArticleRequest {
  title: string;
  url: string;
  imageUrl: string;
}

export interface ArticleType {
  id: number;
  userName: string;
  title: string;
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
