import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comment';
import { levellogHandler } from './levellog';
import { roadmapHandler } from './keywords';
import { articlesHandler } from './articles';

export const handlers = [
  ...popularStudyLogHandler,
  ...commentsHandler,
  ...levellogHandler,
  ...roadmapHandler,
  ...articlesHandler,
];
