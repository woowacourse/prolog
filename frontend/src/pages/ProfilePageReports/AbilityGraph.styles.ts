import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Section = styled.section`
  > h3 {
    display: inline;
    margin-left: 0.6rem;
    margin-bottom: 0.3rem;
    text-align: left;

    font-size: 1.8rem;
    font-weight: 400;
    color: ${COLOR.BLACK_900};
  }
`;

export const Content = styled.div`
  margin-top: 1rem;
  height: 25rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 4rem;
  background-color: ${COLOR.WHITE};
  border-radius: 1rem;
  box-shadow: 0 0 3px ${COLOR.LIGHT_GRAY_200};
`;
