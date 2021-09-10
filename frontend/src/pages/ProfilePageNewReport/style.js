import styled from '@emotion/styled';

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
  width: 1.8rem;
  height: 1.8rem;
  margin-right: 0.5rem;

  //safari 브라우저 input 사이즈 적용이 안되서 추가
  -webkit-transform: scale(1.2, 1.2);
`;

const FormButtonWrapper = styled.div`
  width: 100%;

  > button {
    width: 100%;
    margin: 0 0.5rem;

    font-size: 1.4rem;
  }
`;

export { Form, Checkbox, FormButtonWrapper };
