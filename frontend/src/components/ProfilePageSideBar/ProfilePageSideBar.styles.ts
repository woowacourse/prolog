import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import MEDIA_QUERY from '../../constants/mediaQuery';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 20rem;

  ${MEDIA_QUERY.md} {
    display: grid;
    grid-template-columns: auto 1fr;
    grid-template-rows: auto 1fr;
    gap: 1.2rem;
    max-width: none;
    height: 284px;
    margin-bottom: 1.2rem;

    & > * {
      width: 100%;
    }
    & > :first-child {
      grid-row: 1 / 3;
    }
  }
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${COLOR.WHITE};
  border-radius: 1.6rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  padding-bottom: 1.8rem;
  height: fit-content;
  width: fit-content;

  ${MEDIA_QUERY.md} {
    height: 100%;
  }
`;

const Image = styled.img`
  width: 20rem;
  height: 20rem;
  border-top-left-radius: 1.6rem;
  border-top-right-radius: 1.6rem;
  padding: 2rem;
`;

const Nickname = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  border-top: none;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const UpdateButton = styled.button`
  border-radius: 8px;
`;

const RssFeedUrl = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  border-top: none;
`;

const Role = styled.div`
  margin-bottom: 1rem;
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const RoleButton = styled.button`
  margin-top: 0.3rem;
  font-size: 1.2rem;
  color: ${COLOR.DARK_GRAY_700};
`;

const RoleLabel = styled.div`
  margin-top: 1.5rem;
  font-size: 1.5rem;
  color: ${COLOR.DARK_GRAY_700};
`;

const RssLinkLabel = styled.div`
  margin-top: 1rem;
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const MenuList = styled.ul`
  margin-top: 1rem;
  display: flex;
  flex-direction: column;
  background-color: ${COLOR.WHITE};
  border-radius: 1.6rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  padding: 1.6rem 0;

  ${MEDIA_QUERY.md} {
    height: 100%;
    padding: 0;
    margin: 0;
    justify-content: center;
  }
`;

const MenuItem = styled.li<{ isSelectedMenu: boolean }>`
  height: 4rem;
  display: flex;
  align-items: center;
  position: relative;

  ${({ isSelectedMenu }) =>
    isSelectedMenu &&
    css`
      font-weight: 700;
    `}

  & > button {
    ${({ isSelectedMenu }) =>
      isSelectedMenu &&
      css`
        color: ${COLOR.DARK_GRAY_900};
        font-weight: 500;
      `}
  }
  &:before {
    ${({ isSelectedMenu }) =>
      isSelectedMenu &&
      css`
        position: absolute;
        content: '';
        background-color: ${COLOR.LIGHT_GRAY_900};
        height: inherit;
        width: 0.5rem;
      `}
  }
`;

const MenuButton = styled.button`
  width: 100%;
  padding: 1rem;
  display: flex;
  align-items: center;
  gap: 0.6rem;

  font-size: 1.6rem;
  font-weight: 300;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const NicknameInput = styled.input`
  padding: 0.2rem 0.5rem;
  font-size: 1.5rem;
  outline: none;
  border-radius: 0.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_900};
  width: 12rem;
`;

const NicknameWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  word-break: break-all;
  align-items: center;
`;

const RssFeedInput = styled.input`
  padding: 0.2rem 0.5rem;
  font-size: 1.6rem;
  outline: none;
  border-radius: 0.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_900};
  width: 12rem;
`;

const RssFeedWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  word-break: break-all;
  align-items: center;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};
  color: ${COLOR.BLACK_800};
  margin: 0 0.5rem;
  width: 5rem;
  height: 3rem;
  flex-shrink: 0;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

export {
  Profile,
  Image,
  Nickname,
  RssFeedUrl,
  Role,
  UpdateButton,
  RoleButton,
  RoleLabel,
  RssLinkLabel,
  MenuList,
  MenuItem,
  MenuButton,
  Container,
  NicknameInput,
  NicknameWrapper,
  RssFeedInput,
  RssFeedWrapper,
  EditButtonStyle,
};
