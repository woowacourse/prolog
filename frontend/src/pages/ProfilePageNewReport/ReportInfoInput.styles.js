import { css } from '@emotion/react';
import styled from '@emotion/styled';

import COLOR from '../../constants/color';

const Label = styled.label`
  position: relative;
  width: 100%;

  display: block;
  margin-left: 0.6rem;
  margin-bottom: 0.2rem;

  color: ${COLOR.BLACK_900};
  font-size: 1.8rem;

  > span {
    position: absolute;
    right: 0;
    top: 5%;
    margin-right: 1rem;

    font-size: 1.2rem;
    color: ${COLOR.DARK_GRAY_700};
  }
`;

const inputStyle = css`
  width: 100%;

  border: 0.1rem solid ${COLOR.LIGHT_GRAY_500};
  border-radius: 0.6rem;

  font-size: 1.4rem;
  line-height: 1.5;
  color: ${COLOR.BLACK_600};
`;

const Title = styled.input`
  height: 4.5rem;
  padding: 0 1rem;
  margin-bottom: 2rem;
  ${inputStyle}
`;

const Desc = styled.textarea`
  height: 10rem;
  padding: 1rem 1rem 0;
  ${inputStyle}

  resize: none;
`;

export { Label, Title, Desc };
