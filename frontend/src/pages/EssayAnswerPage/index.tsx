/** @jsxImportSource @emotion/react */

import Content from './Content';
import {Button, BUTTON_SIZE} from '../../components';
import {ButtonList} from './styles';

import {MainContentStyle} from '../../PageRouter';
import useEssayAnswer from "../../hooks/EssayAnswer/useEssayAnswer";
import {DeleteButtonStyle} from "../LevellogPage/styles";
import {CONFIRM_MESSAGE} from "../../constants";

const EssayAnswerPage = () => {
  const {
    essayAnswer,
    deleteEssayAnswer,
    isCurrentUserAuthor,
    isLoading,
  } = useEssayAnswer();

  return (
    <div css={MainContentStyle}>
      {isCurrentUserAuthor && (
        <ButtonList>
          {[
            {
              title: '삭제',
              cssProps: DeleteButtonStyle,
              onClick: (e) => {
                if (!essayAnswer) return;
                if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;

                deleteEssayAnswer(essayAnswer.id, e);
              },
            },
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
          <Content essayAnswer={essayAnswer} />
        </>
      )}
    </div>
  );
};

export default EssayAnswerPage;
