import styled from '@emotion/styled';

const Container = styled.div`
  width: fit-content;
  background-color: ${({ backgroundColor }) => backgroundColor ?? '#efefef'};
  color: ${({ color }) => color ?? '#383838'};
  padding: 0.2rem 0.8rem;
  border-radius: 5rem;
  margin-right: 1rem;

  display: flex;
  justify-content: center;
  align-items: center;

  button {
    width: 1.6rem;
    height: 1.6rem;
    border-radius: 50%;
    background-color: inherit;
    margin-left: 0.3rem;

    display: inline-flex;
    justify-content: center;
    align-items: center;

    &:hover {
      background-color: #ffbdad;

      svg {
        stroke: #ff4020;
      }
    }
  }
`;

export { Container };
