import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Title = styled.h2`
  width: 100%;
  margin-bottom: 1rem;

  font-size: 2.1rem;
  text-align: left;
`;

export const DeleteButton = styled.button`
  width: 98%;
  height: 3.6rem;
  margin: 0 auto;
  margin-top: 5rem;

  display: flex;
  justify-content: center;
  align-items: center;

  border: none;
  border-radius: 1.6rem;
  background-color: ${COLOR.RED_200};

  font-size: 1.4rem;
  line-height: 1.5;

  :hover {
    background-color: ${COLOR.RED_600};
    color: white;

    cursor: pointer;
  }
`;
