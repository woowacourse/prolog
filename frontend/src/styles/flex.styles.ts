import { css } from '@emotion/react';
import { CSSProperties } from '@emotion/react/node_modules/@emotion/serialize';

export const getFlexStyle = ({
  columnGap = '0px',
  rowGap = '0px',
  justifyContent = 'normal',
  alignItems = 'normal',
  flexGrow,
  flexWrap,
  flexShrink,
  flexDirection = 'row',
}: Pick<
  CSSProperties,
  | 'columnGap'
  | 'rowGap'
  | 'justifyContent'
  | 'alignItems'
  | 'flexGrow'
  | 'flexWrap'
  | 'flexShrink'
  | 'flexDirection'
>) => css`
  display: flex;

  column-gap: ${columnGap};
  row-gap: ${rowGap};

  justify-content: ${justifyContent};
  align-items: ${alignItems};

  flex-grow: ${flexGrow};
  flex-wrap: ${flexWrap};
  flex-shrink: ${flexShrink};
  flex-direction: ${flexDirection};
`;

export const FlexStyle = css`
  display: flex;
`;

export const FlexRowStyle = css`
  flex-direction: row;
`;


export const FlexColumnStyle = css`
  flex-direction: column;
`;

export const JustifyContentCenterStyle = css`
  justify-content: center;
`;

export const JustifyContentEndStyle = css`
  justify-content: flex-end;
`;

export const JustifyContentSpaceBtwStyle = css`
  justify-content: space-between;
`;

export const AlignItemsCenterStyle = css`
  align-items: center;
`;

export const AlignItemsEndStyle = css`
  align-items: flex-end;
`;

export const AlignItemsBaseLineStyle = css`
  align-items: baseline;
`;
