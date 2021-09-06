import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const ButtonList = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};
  margin-right: 1rem;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.RED_300};

  &:hover {
    background-color: ${COLOR.RED_400};
  }
`;

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  & > *:not(:last-child) {
    margin-bottom: 2rem;
  }

  .tui-editor-contents {
    font-size: 1.6rem;
  }
`;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const SubHeaderRightContent = styled.div`
  display: flex;
  align-items: center;

  & > button {
    margin-left: 0.7rem;
    font-size: 1.5rem;
    color: ${COLOR.BLACK_OPACITY_600};
  }
`;

const Mission = styled.div`
  font-size: 2rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
`;

const IssuedDate = styled.div`
  color: ${COLOR.DARK_GRAY_800};
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
  padding: 0.8rem;
  cursor: pointer;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

const ViewerWrapper = styled.div`
  word-break: break-all;
`;

export {
  ButtonList,
  EditButtonStyle,
  DeleteButtonStyle,
  CardInner,
  SubHeader,
  SubHeaderRightContent,
  Mission,
  Title,
  Tags,
  IssuedDate,
  ProfileChipStyle,
  ViewerWrapper,
};
