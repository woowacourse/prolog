import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  justify-content: center;
  margin: 25px 0px;
`;

export const Main = styled.main`
  display: flex;
  flex-direction: column;
  gap: 30px;
  max-width: 112rem;
  overflow-x: hidden;
  padding: 0 4rem;
`;

export const Title = styled.h2`
  margin-bottom: 10px;
`;

export const RoadmapContainer = styled.div`
  overflow-x: auto;
  margin: 0 -4rem;
`;

export const CircularProgress = styled.div<{ value: number }>`
  width: 16px;
  height: 16px;
  border-radius: 50%;
  background: conic-gradient(hsl(42, 100%, 55%) ${({ value }) => Math.round(value * 100)}%, #eaeaea 0);
  box-shadow: inset 0px 0 1px 1px rgba(0, 0, 0, 0.05);
`;
