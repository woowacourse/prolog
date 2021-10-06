import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.span`
  width: fit-content;
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.LIGHT_GRAY_200};
  color: ${({ color }) => color ?? COLOR.BLACK_800};
  padding: 0.4rem 0.8rem 0.4rem 1.2rem;
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
      background-color: ${COLOR.RED_100};

      svg {
        stroke: ${COLOR.RED_600};
      }
    }
  }
`;

const ChipText = styled.span`
  font-size: 1.4rem;
  text-align: center;
  color: inherit;
`;

export { Container, ChipText };
