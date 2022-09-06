import { popularStudyLogHandler } from './popularStudyLog';
import { commentsHandler } from './comment';
import { levellogHandler } from './levellog';

export const handlers = [...popularStudyLogHandler, ...commentsHandler, ...levellogHandler];
