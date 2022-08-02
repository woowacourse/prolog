import { css, SerializedStyles } from '@emotion/react';
import styled from '@emotion/styled';

const FlexBox = styled.div<{
  flexDirection?: 'row' | 'column';
  justifyContent?:
    | 'flex-start'
    | 'flex-end'
    | 'center'
    | 'space-between'
    | 'space-around'
    | 'initial'
    | 'inherit';
  alignItems?:
    | 'stretch'
    | 'center'
    | 'flex-start'
    | 'flex-end'
    | 'baseline'
    | 'initial'
    | 'inherit';
  css?: SerializedStyles;
}>`
  display: flex;
  ${({ flexDirection, justifyContent, alignItems }) => css`
    flex-direction: ${flexDirection};
    justify-content: ${justifyContent};
    align-items: ${alignItems};
  `}

  ${({ css }) => css}
`;

export default FlexBox;
