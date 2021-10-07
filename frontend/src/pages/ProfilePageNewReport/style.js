import styled from '@emotion/styled';

const Form = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  > h2 {
    display: inline-block;
    margin: 0 auto 1rem;
  }

  > div {
    display: flex;
    align-items: center;
    margin-bottom: 1rem;

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
  margin-right: 1rem;
  -webkit-transform: scale(1.4);
`;

const FormButtonWrapper = styled.div`
  width: 100%;
  margin-top: 2rem;

  > button {
    width: 100%;
    margin: 0 0.5rem;

    font-size: 1.4rem;
  }
`;

export { Form, Checkbox, FormButtonWrapper };
