import { commentsHandler } from './comment';
import { essayAnswerHandler } from './essayAnswers';
import { roadmapHandler } from './keywords';
import { levellogHandler } from './levellog';
import { popularStudyLogHandler } from './popularStudyLog';

export const handlers = [
  ...commentsHandler,
  ...essayAnswerHandler,
  ...roadmapHandler,
  ...levellogHandler,
  ...popularStudyLogHandler,
];
