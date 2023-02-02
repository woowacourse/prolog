/** @jsxImportSource @emotion/react */

import Content from './Content';
import {Button, BUTTON_SIZE} from '../../components';
import {ButtonList} from './styles';

import {MainContentStyle} from '../../PageRouter';
import useEssayAnswer from "../../hooks/EssayAnswer/useEssayAnswer";

const EssayAnswerPage = () => {
  const {
    essayAnswer,
    isCurrentUserAuthor,
    isLoading,
  } = useEssayAnswer();

  console.log(essayAnswer);

  return (
    <div css={MainContentStyle}>
      {isCurrentUserAuthor && (
        <ButtonList>
          {[].map(({ title, cssProps, onClick }) => (
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
