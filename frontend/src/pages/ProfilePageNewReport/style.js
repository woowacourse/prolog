import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Form = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  > div {
    display: flex;
    align-items: center;

    > input {
      width: 2rem;
      height: 2rem;
      margin-right: 0.5rem;
    }

    > label {
      font-size: 1.4rem;
    }
  }

  > button {
    border-radius: 0.8rem;
  }
`;

const InfoSection = styled.section`
  width: 100%;
  padding: 0 0 1rem;

  border-radius: 0.5rem;

  > label {
    display: inline-block;
    margin-left: 0.6rem;
    margin-bottom: 0.2rem;
  }
`;

const inputStyle = css`
  width: 100%;
  margin-bottom: 1rem;

  border: 1px solid ${COLOR.BLACK_OPACITY_400};
  border-radius: 0.5rem;

  font-size: 1.4rem;
  line-height: 1.5;
  color: ${COLOR.BLACK_600};
`;

const Title = styled.input`
  height: 4.5rem;
  padding: 0 1rem;
  ${inputStyle}
`;

const Desc = styled.textarea`
  height: 10rem;
  padding: 1rem 1rem 0;
  ${inputStyle}

  resize: none;
`;

export { Form, InfoSection, Title, Desc };
