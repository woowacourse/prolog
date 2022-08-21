import { css, SerializedStyles } from '@emotion/react';
import styled from '@emotion/styled';
import { CSSProperties } from 'react';

const FlexBox = styled.div<
  Pick<CSSProperties, 'justifyContent' | 'flexDirection' | 'alignItems'> & {
    css?: SerializedStyles;
  }
>`
  display: flex;
  ${({ flexDirection, justifyContent, alignItems }) => css`
    flex-direction: ${flexDirection};
    justify-content: ${justifyContent};
    align-items: ${alignItems};
  `}

  ${({ css }) => css}
`;

export default FlexBox;
