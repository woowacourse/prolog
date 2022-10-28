import { TempSavedStudyLogForm } from '../../models/Studylogs';
import { useCreateTempSavedStudylog, useFetchTempSavedStudylog } from './../queries/studylog';

export default function useTempSavedStudylog() {
  const {
    data: tempSavedStudylog,
    remove: removeCachedTempSavedStudylog,
  } = useFetchTempSavedStudylog();

  const createTempSavedStudylogMutation = useCreateTempSavedStudylog();

  const createTempSavedStudylog = (body: TempSavedStudyLogForm) => {
    createTempSavedStudylogMutation.mutate(body);
  };

  return {
    tempSavedStudylog,
    createTempSavedStudylog,
    removeCachedTempSavedStudylog,
  };
}
