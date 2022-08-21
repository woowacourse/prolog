import { MouseEvent } from 'react';
import { ChangeEvent, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { PATH } from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';
import { useEditLevellogMutation, useGetLevellog } from '../queries/levellog';
import useNewLevellog from './useNewLevellog';

const useEditLevellog = () => {
  const { id } = useParams<{ id: string }>();
  const history = useHistory();

  const { NewLevellogQnAListProps, editorContentRef, setQnAList } = useNewLevellog();
  const [title, setTitle] = useState('');
  const onChangeTitle = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const { data: levellog, isLoading } = useGetLevellog(
    { id },
    {
      onSuccess: (levellog) => {
        setTitle(levellog.title);
        setQnAList(levellog.levelLogs);
      },
    }
  );
  const { mutate: editLevellog } = useEditLevellogMutation(
    { id },
    {
      onSuccess: () => {
        alert(SUCCESS_MESSAGE.EDIT_POST);
        history.push(`${PATH.LEVELLOG}/${id}`);
      },
    }
  );

  const onEditLevellog = (e: MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    editLevellog({
      title,
      content: editorContentRef.current.getInstance().getMarkdown(),
      levelLogs: NewLevellogQnAListProps.QnAList,
    });
  };

  return {
    editorContentRef,
    title,
    onChangeTitle,
    content: levellog?.content,
    NewLevellogQnAListProps,
    isLoading,
    onEditLevellog,
    id,
  };
};

export default useEditLevellog;
