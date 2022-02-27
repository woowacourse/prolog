export interface Author {
  username: string;
  nickname: string;
  imageUrl: string;
}

export interface Level {
  id: number;
  name: string;
}

export interface Mission {
  id: number;
  name: string;
  level?: Level;
}

export interface Tag {
  id: number;
  name: string;
}

export interface Studylog {
  id: number;
  author: Author;
  content: string;
  mission: Mission;
  title: string;
  tags: Tag[];
  createdAt: Date;
  updatedAt?: Date;
  isRead: boolean;
  isScrapped: boolean;
  viewCount: number;
  liked: boolean;
  likesCount: number;
}
