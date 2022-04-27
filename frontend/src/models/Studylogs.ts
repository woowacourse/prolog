type Role = 'UNVALIDATED' | 'CREW' | 'COACH' | 'ADMIN';
export interface Author {
  id: number;
  username: string;
  nickname: string;
  imageUrl: string;
  role: Role;
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
export interface Session {
  id: number;
  name: string;
}

export interface Tag {
  id: number;
  name: string;
}

// TODO: read, scrap => isRead, isScrapped로 변경
export interface Studylog {
  id: number;
  author: Author;
  content: string;
  mission?: Mission;
  session?: Session;
  title: string;
  tags: Tag[];
  createdAt: Date;
  updatedAt?: Date;
  read: boolean;
  scrap: boolean;
  viewCount: number;
  liked: boolean;
  likesCount: number;
}

export interface StudyLogList {
  data: Studylog[];
  totalSize: number;
  totalPage: number;
  currPage: number;
}

export type StudylogForm = Pick<Studylog, 'title' | 'tags'> & {
  content: string | null;
  missionId: number | null;
  sessionId: number | null;
};
