type Role = 'UNVALIDATED' | 'CREW' | 'COACH' | 'ADMIN';

export interface Author {
  id: number;
  username: string;
  nickname: string;
  imageUrl: string;
  role: Role;
}

export interface Mission {
  id: number;
  name: string;
  session?: Session;
}

export interface Session {
  sessionId: number;
  name: string;
}

export interface Tag {
  id: number;
  name: string;
}

export interface Question {
  id: number;
  content: string;
}

export interface Answer {
  questionId: number;
  answerContent: string;
}

export interface QuestionAnswer {
  id: number;
  answerContent: string;
  questionId: number;
  questionContent: string;
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
  answers: Answer[];
  createdAt: Date;
  updatedAt?: Date;
  read: boolean;
  viewCount: number;
  liked: boolean;
  likesCount: number;
  scrap: boolean;
  scrapedCount: number;
  commentCount: number;
}

export type TempSavedStudyLog = Pick<
  Studylog,
  'id' | 'author' | 'title' | 'content' | 'session' | 'mission' | 'tags' | 'answers'
>;

export type TempSavedStudyLogForm = Pick<TempSavedStudyLog, 'title' | 'content' | 'tags'> & {
  missionId: number | null;
  sessionId: number | null;
  answers: Answer[];
};

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
  answers: Answer[];
};

export const studyLogCategory = {
  allResponse: '전체',
  frontResponse: '프론트엔드',
  backResponse: '백엔드',
  androidResponse: '안드로이드',
} as const;

export type StudyLogResponse = Record<keyof typeof studyLogCategory, StudyLogList>;
