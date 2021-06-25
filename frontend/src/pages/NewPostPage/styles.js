import styled from '@emotion/styled';
import { css } from '@emotion/react';

// TODO: section 으로 바꾸기 -> aria-label 주기
const SelectBoxWrapper = styled.div`
  margin: 3rem 0;
`;

const Flex = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 3rem 0;
`;

const Post = styled.li`
  margin-bottom: 4.8rem;
`;

const LogButtonStyle = css`
  background-color: #e0e0e0;
  font-weight: 500;
`;

const SubmitButtonStyle = css`
  width: 100%;
  background-color: #153147;
  color: #ffffff;
  font-weight: 500;
  margin: 0;
`;

export { SelectBoxWrapper, Flex, Post, LogButtonStyle, SubmitButtonStyle };
