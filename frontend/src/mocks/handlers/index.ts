import { articlesHandler } from './articles';
import { commentsHandler } from './comment';
import { levellogHandler } from './levellog';
import { popularStudyLogHandler } from './popularStudyLog';
import { roadmapHandler } from './roadmap';

export const handlers = [
  ...popularStudyLogHandler,
  ...commentsHandler,
  ...levellogHandler,
  ...roadmapHandler,
  ...articlesHandler,
];
