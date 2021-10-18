import { css } from '@emotion/react';
import styled from '@emotion/styled';

const FlexBox = styled.div`
  display: flex;
  ${({ flexDirection, justifyContent, alignItems }) => css`
    flex-direction: ${flexDirection};
    justify-content: ${justifyContent};
    align-items: ${alignItems};
  `}

  ${({ css }) => css}
`;

export default FlexBox;
