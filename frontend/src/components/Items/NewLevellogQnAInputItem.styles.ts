import styled from '@emotion/styled';
import TextareaAutosize from 'react-textarea-autosize';

import { COLOR } from '../../constants';
import {
  AlignItemsCenterStyle,
  FlexColumnStyle,
  FlexStyle,
  JustifyContentCenterStyle,
} from '../../styles/flex.styles';
import { ReactComponent as MinusIcon } from '../../assets/images/minus_icon.svg';

const QnAInputBasicStyle = {
  padding: '1rem',
  border: 'none',

  ':focus': {
    border: 'none',
    'outline-color': `${COLOR.LIGHT_BLUE_200}`,
  },
};

export const Container = styled.div`
  position: relative;
`;
export const QnAForm = styled.form`
  ${FlexStyle}
  ${FlexColumnStyle}
  background-color: ${COLOR.LIGHT_BLUE_200};
  border-radius: 2rem;
`;
export const QnAQuestionWrapper = styled.div`
  ${FlexStyle}
  ${AlignItemsCenterStyle}

  gap:1rem;
  padding: 1rem;
`;
export const QnAQuestionIndex = styled.span`
  ${FlexStyle}
  ${JustifyContentCenterStyle}

  width: 50px;
  font-weight: bold;
`;
export const QnAQuestionInput = styled.input`
  ${QnAInputBasicStyle}
  width: 100%;

  border-radius: 1rem;
`;
export const QnAAnswerTextarea = styled(TextareaAutosize)`
  ${QnAInputBasicStyle}
`;
export const DeleteQnAButton = styled(MinusIcon)`
  position: absolute;
  top: -10px;
  right: -10px;

  width: 24px;
  height: 24px;

  cursor: pointer;
`;
