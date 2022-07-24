import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comments';

export const handlers = [...popularStudyLogHandler, ...commentsHandler];
