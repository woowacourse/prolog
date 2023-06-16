import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import MEDIA_QUERY from '../../constants/mediaQuery';

const DropdownToggledStyle = css`
  &:before {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 80;
    display: block;
    cursor: default;
    content: ' ';
    background: transparent;
  }
`;

const Container = styled.div`
  background-color: ${COLOR.LIGHT_GRAY_50};

  border: 1px solid ${COLOR.DARK_GRAY_400};
  border-radius: 1.4rem;
  padding: 0 3.2rem;
  display: flex;
  font-size: 1.4rem;
  height: inherit;
  align-items: center;

  ${({ isDropdownToggled }) => isDropdownToggled && DropdownToggledStyle}
  ${({ css }) => css && css}

  & > div:not(:last-child) {
    margin-right: 3.2rem;

    ${MEDIA_QUERY.xs} {
      margin-right: 2rem;
    }
  }

  @media screen and (max-width: 450px) {
    font-size: 1rem;
  }

  & > div {
    input[type='search'] {
      font-weight: 500;
      padding: 1rem;
      font-size: 1.4rem;
      border: 1px solid ${COLOR.LIGHT_GRAY_700};
      border-radius: 1rem;
      outline: none;

      :focus {
        border-color: ${COLOR.LIGHT_GRAY_700};
      }
    }

    & > button {
      display: flex;
      align-items: center;
      height: 100%;
      font-size: 1.6rem;
      text-align: center;
      color: ${COLOR.DARK_GRAY_500};

      ::after {
        content: '';
        width: 0;
        height: 0;
        transform: translateY(50%);
        margin-left: 0.2rem;
        border-top: 0.5rem solid ${COLOR.DARK_GRAY_500};
        border-bottom: 0.5rem solid transparent;
        border-left: 0.5rem solid transparent;
        border-right: 0.5rem solid transparent;
      }
    }

    :hover {
      color: ${COLOR.BLACK_600};

      ::after {
        border-top-color: ${COLOR.BLACK_600};
      }
    }
  }
`;

const FilterContainer = styled.div`
display: flex;

& > div:not(:last-child) {
  margin-right: 3.2rem;

  ${MEDIA_QUERY.xs} {
    margin-right: 2rem;
  }
}

@media screen and (max-width: 620px) {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}

& button {
  display: flex;
  align-items: center;
  height: 100%;
  text-align: center;
  color: ${COLOR.DARK_GRAY_500};

  ::after {
    content: '';
    width: 0;
    height: 0;
    transform: translateY(50%);
    margin-left: 0.2rem;
    border-top: 0.5rem solid ${COLOR.DARK_GRAY_500};
    border-bottom: 0.5rem solid transparent;
    border-left: 0.5rem solid transparent;
    border-right: 0.5rem solid transparent;
  }
}
`;

const FilterDetail = styled.button`
  display: flex;
  align-items: center;
  gap: 0.4rem;

  & > img {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

const ResetFilter = styled.div`
  margin-left: auto;
  color: ${COLOR.DARK_GRAY_500};
  cursor: pointer;

  flex-shrink: 0;
`;

const CheckIcon = styled.img`
  ${({ checked }) => !checked && 'visibility: hidden;'}
`;

const DropdownStyle = css`
  padding-top: 0;
`;

const SearchBarWrapper = styled.div`
  position: sticky;
  top: 0;
  padding-top: 1rem;
  background-color: white;
`;

const SearchBarStyle = css`
  width: 100%;
  height: 100%;
  background-color: white;
  margin-right: 1rem;
  padding: 0.5rem 0;

  & > input {
    margin-right: 0;
  }
`;

const MemberWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const MemberImage = styled.img`
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  margin-right: 0.7rem;
`;

const Nickname = styled.span`
  margin-left: 0.7rem;
  color: ${COLOR.DARK_GRAY_500};
  font-weight: 400;
`;

export const NoContent = styled.div`
  width: 100%;
  height: 8rem;
  padding: 1rem;
  color: ${COLOR.LIGHT_GRAY_600};

  display: flex;
  justify-content: center;
  align-items: center;
`;

const SessionInMission = styled.div`
  background-color: aliceblue;
  border-radius: 1rem;
  padding-left: 0.3rem;
  padding-right: 0.3rem;
  font-size: xx-small;
`;

const MissionName = styled.div`
  margin-top: 0.2rem;
  margin-left: 0.5rem;
`;

const MissionWrapper = styled.div`
  margin-top: 0.2rem;
  margin-left: 0.5rem;
`;

export {
  Container,
  FilterDetail,
  ResetFilter,
  CheckIcon,
  DropdownStyle,
  SearchBarWrapper,
  SearchBarStyle,
  MemberWrapper,
  MemberImage,
  Nickname,
  SessionInMission,
  MissionName,
  MissionWrapper,
  FilterContainer,
};
