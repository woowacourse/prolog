import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { COLOR } from '../../constants';

const ArticlePageContainer = styled.form`
  width: 100%;
  height: auto;
  max-width: 650px;

  padding: 9% 4%;
  margin: 0 auto;
`;

const Title = styled.div`
  font-size: 3.5rem;
  text-align: center;

  margin-bottom: 9rem;
`;

const InputContainer = styled.div``;

const Input = styled.input`
  width: 100%;

  margin: 0 0 24px 0;
  padding: 0 18px;

  font-size: 15px;
  line-height: 48px;

  outline: none;
  border: none;
  border-radius: 8px;

  background-color: #ffffff;

  &:disabled {
    background: #f0f0f0;
  }
`;

const Label = styled.label`
  display: inline-block;
  padding: 5px 0;
  font-size: 15px;
  font-weight: 500;
  line-height: 1.6;
  color: #4e5968;
`;

const SubmitButtonStyle = css`
  width: 30%;

  margin: 0 auto;
  padding: 1rem 0;

  border-radius: 1.6rem;

  background-color: ${COLOR.LIGHT_BLUE_300};

  :hover {
    background-color: ${COLOR.LIGHT_BLUE_500};
  }
`;

const ThumbnailContainer = styled.div`
  text-align: center;
  
  margin-bottom: 24px;
`;

const ThumbnailImage = styled.img`
  width: 50%;
`;

export {
  ArticlePageContainer,
  Title,
  InputContainer,
  SubmitButtonStyle,
  Input,
  Label,
  ThumbnailContainer,
  ThumbnailImage,
};
