import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comment';
import { levellogHandler } from './levellog';
import { roadmapHandler } from './keywords';

export const handlers = [
  ...popularStudyLogHandler,
  ...commentsHandler,
  ...levellogHandler,
  ...roadmapHandler,
];
