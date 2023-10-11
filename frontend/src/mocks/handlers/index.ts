import { commentsHandler } from './comment';
import { essayAnswerHandler } from './essayAnswers';
import { roadmapHandler } from './roadmap';
import { levellogHandler } from './levellog';
import { popularStudyLogHandler } from './popularStudyLog';

export const handlers = [
  ...commentsHandler,
  ...essayAnswerHandler,
  ...roadmapHandler,
  ...levellogHandler,
  ...popularStudyLogHandler,
];
