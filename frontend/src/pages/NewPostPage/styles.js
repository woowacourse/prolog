import styled from '@emotion/styled';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

// TODO: section 으로 바꾸기 -> aria-label 주기
const SelectBoxWrapper = styled.div`
  margin: 3rem 0;
`;

const Post = styled.div`
  margin-bottom: 4.8rem;
`;

const SubmitButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};
  font-weight: 500;
  margin: 0;

  &:hover {
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;

export { SelectBoxWrapper, Post, SubmitButtonStyle };
