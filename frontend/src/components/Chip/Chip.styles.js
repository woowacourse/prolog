import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  width: fit-content;
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.LIGHT_GRAY_200};
  color: ${({ color }) => color ?? COLOR.BLACK_900};
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
  color: ${COLOR.DARK_GRAY_800};
`;

export { Container, ChipText };
