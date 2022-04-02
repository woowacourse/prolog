import styled from '@emotion/styled';

export const Form = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  position: relative;

  > h2 {
    display: inline-block;
    margin: 0 auto 1rem;
  }

  > button {
    border-radius: 0.8rem;
  }

  > section {
    width: 100%;
    margin-bottom: 2.5rem;
  }
`;

export const FormButtonWrapper = styled.div`
  width: 100%;
  margin-bottom: 4rem;
  display: flex;
  align-items: center;

  > button {
    width: 100%;
    margin: 0 0.5rem;

    font-size: 1.4rem;
  }
`;
