import { MouseEvent, useContext, useRef } from 'react';
import { ChangeEvent, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { PATH } from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';
import { useEditLevellogMutation, useGetLevellog } from '../queries/levellog';
import { ALERT_MESSAGE } from '../../constants';
import useSnackBar from '../useSnackBar';
import useQnAInputList from './useQnAInputList';
import useBeforeunload from '../useBeforeunload';
import { UserContext } from '../../contexts/UserProvider';

const useEditLevellog = () => {
  const { id } = useParams<{ id: string }>();
  const {
    user: { userId },
  } = useContext(UserContext);

  const history = useHistory();
  const { openSnackBar } = useSnackBar();
  const editorContentRef = useRef<any>(null);

  useBeforeunload(editorContentRef);

  const {
    QnAList,
    setQnAList,
    onChangeAnswer,
    onChangeQuestion,
    onDeleteQnA,
    onAddQnA,
  } = useQnAInputList();
  const EditLevellogQnAListProps = {
    QnAList,
    onChangeAnswer,
    onChangeQuestion,
    onDeleteQnA,
    onAddQnA,
  };

  const [title, setTitle] = useState('');
  const onChangeTitle = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const { data: levellog, isLoading } = useGetLevellog(
    { id },
    {
      onSuccess: (levellog) => {
        if (levellog.author.id !== userId) {
          openSnackBar(ALERT_MESSAGE.CANNOT_EDIT_OTHERS);
          history.push(`${PATH.LEVELLOG}/${id}`);
        }

        setTitle(levellog.title);
        setQnAList(
          levellog.levelLogs.map((log) => ({ question: log.question, answer: log.answer }))
        );
      },
    }
  );
  const { mutate: editLevellogRequest } = useEditLevellogMutation(
    { id },
    {
      onSuccess: () => {
        openSnackBar(SUCCESS_MESSAGE.EDIT_POST);
        history.push(`${PATH.LEVELLOG}/${id}`);
      },
    }
  );

  const editLevellog = (e: MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (title.length === 0) {
      alert(ALERT_MESSAGE.NO_TITLE);
      return;
    }

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    if (QnAList.length < 1) {
      alert(ALERT_MESSAGE.NO_QNA);
      return;
    }

    if (QnAList.some((QnA) => QnA.answer.length < 1 || QnA.question.length < 1)) {
      alert(ALERT_MESSAGE.NO_QUESTION_AND_ANSWER);
      return;
    }

    editLevellogRequest({
      title,
      content: editorContentRef.current.getInstance().getMarkdown(),
      levelLogs: QnAList,
    });
  };

  return {
    editorContentRef,
    title,
    onChangeTitle,
    content: levellog?.content,
    EditLevellogQnAListProps,
    isLoading,
    editLevellog,
  };
};

export default useEditLevellog;
