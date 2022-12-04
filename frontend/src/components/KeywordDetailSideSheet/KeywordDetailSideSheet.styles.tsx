import styled from '@emotion/styled';

export const Root = styled.main`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0 30px;
`;

export const DescriptionSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const QuizSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 16px;

  ol {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }
`;
