import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comment';
import { levellogHandler } from './levellog';
import { profileHandler } from './profile';

export const handlers = [
  ...popularStudyLogHandler,
  ...commentsHandler,
  ...levellogHandler,
  ...profileHandler,
];
