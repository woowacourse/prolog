import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comment';

export const handlers = [...popularStudyLogHandler, ...commentsHandler];
