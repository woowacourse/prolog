import styled from '@emotion/styled';

export const CircularProgress = styled.div<{ value: number }>`
  width: 16px;
  height: 16px;
  border-radius: 50%;
  background: conic-gradient(
    hsl(42, 100%, 55%) ${({ value }) => Math.round(value * 100)}%,
    #eaeaea 0
  );
  box-shadow: inset 0px 0 1px 1px rgba(0, 0, 0, 0.05);
`;
