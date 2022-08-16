/** @jsxImportSource @emotion/react */

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle } from './styles';

import { MainContentStyle } from '../../PageRouter';

import useLevellog from '../../hooks/Levellog/useLevellog';
import QnAList from './QnAList';

const StudylogPage = () => {
  const { levellog, deleteLevellog, isCurrentUserAuthor, isLoading } = useLevellog();

  return (
    <div css={MainContentStyle}>
      {isCurrentUserAuthor && (
        <ButtonList>
          {[
            // { title: '수정', cssProps: EditButtonStyle, onClick: goEditTargetPost },
            { title: '삭제', cssProps: DeleteButtonStyle, onClick: deleteLevellog },
          ].map(({ title, cssProps, onClick }) => (
            <Button
              key={title}
              size={BUTTON_SIZE.X_SMALL}
              type="button"
              cssProps={cssProps}
              onClick={onClick}
            >
              {title}
            </Button>
          ))}
        </ButtonList>
      )}
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <>
          <Content levellog={levellog} />
          <QnAList QnAList={levellog?.levelLogs} />
        </>
      )}
    </div>
  );
};

export default StudylogPage;
