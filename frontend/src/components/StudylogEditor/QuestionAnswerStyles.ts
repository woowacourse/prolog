import styled from '@emotion/styled';
import { COLOR } from '../../constants';
import { css } from '@emotion/react';

export const MainContainer = styled.div`
  gap: 1.5rem;
`;

export const AnswerTextArea = styled.textarea`
  width: 100%;
  min-height: 80px;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 1.2rem;
`;

export const NoQuestionMessage = styled.div`
  min-height: 20rem;
  align-content: center;
  text-align: center;
  font-size: 1.2rem;
  color: gray;
`;

export const AccordionHeader = styled.h2`
  padding: 1rem;
`;

export const AccordionButton = styled.button`
  color: ${COLOR.BLACK_700};
  font-size: 1.2rem;
  outline: none;

  :not(.collapsed) {
    background-color: ${COLOR.LIGHT_BLUE_500};
    color: ${COLOR.BLACK_700};
  }
  :focus {
    box-shadow: none;
  }
`;

export const AnswerBody = styled.div`
  padding: 1rem;
  margin-left: 1rem;
  margin-right: 1rem;
  margin-bottom: 1rem;
  font-size: 1.2rem;
  color: ${COLOR.BLACK_700};
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
`;
