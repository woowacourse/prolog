import { TempSavedStudyLogForm } from '../../models/Studylogs';
import { useCreateTempSavedStudylog, useFetchTempSavedStudylog } from './../queries/studylog';

export default function useTempSavedStudylog() {
  const { data } = useFetchTempSavedStudylog();
  const tempSavedStudylog = data;

  const createTempSavedStudylogMutation = useCreateTempSavedStudylog();

  const createTempSavedStudylog = (body: TempSavedStudyLogForm) => {
    createTempSavedStudylogMutation.mutate(body);
  };

  return {
    tempSavedStudylog,
    createTempSavedStudylog,
  };
}
