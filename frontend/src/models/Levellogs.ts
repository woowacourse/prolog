export interface LevellogRequest {
  title: string;
  content: string;
  levelLogs: QnAType[];
}

export interface QnAType {
  question: string;
  answer: string;
}
