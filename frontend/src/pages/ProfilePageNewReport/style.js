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

    > label {
      font-size: 1.4rem;
    }
  }

  > button {
    border-radius: 0.8rem;
  }

  > section {
    width: 100%;
    margin-bottom: 2.5rem;
  }
`;

const Checkbox = styled.input`
  width: 2rem;
  height: 2rem;
`;

const FormButtonWrapper = styled.div`
  width: 100%;

  > button {
    width: 100%;
    margin: 0 0.5rem;
  }
`;

export { Form, Checkbox, FormButtonWrapper };
