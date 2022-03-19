import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Container = styled.div`
  width: 100%;
  height: 100%;

  font-size: 1.4rem;
  position: relative;

  button:disabled {
    opacity: 0.3;
  }

  button:not(:disabled):hover {
    filter: brightness(0.9);
  }
`;

export const openList = keyframes`
  from {
    height: 0;
    max-height:0;
    opacity:0
  }

  to {
    height: 100%;
    max-height: 100%;
    opacity:1;
  }
`;

export const ManageButtonList = styled.div`
  margin-left: 0.5rem;

  button {
    margin-left: 0.2rem;
  }
`;

export const ArrowButton = styled.button`
  width: 32px;
  height: 32px;
  position: relative;

  cursor: pointer;

  ${({ isOpened }) =>
    isOpened &&
    `
    transform: rotate(90deg);
  `}

  ${({ isParent }) =>
    !isParent &&
    `
    visibility: hidden;
  `}

  :after {
    content: '';

    position: absolute;
    left: 50%;
    top: 50%;

    transform: translateX(-50%) translateY(-50%);

    border-top: 6px solid transparent;
    border-left: 6px solid black;
    border-right: 6px solid transparent;
    border-bottom: 6px solid transparent;
  }
`;

export const FormContainer = styled.div`
  padding: 1rem 0 2rem;
  ${({ isParent }) => !isParent && 'padding-bottom: 1rem;'}
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_100};

  > div {
    width: 100%;
    padding: 0.5rem 2rem;

    display: flex;
    align-items: end;
  }
`;

export const ListForm = styled.form`
  display: grid;
  grid-template-columns: ${({ isParent, isEditing }) =>
    isParent ? `1fr 2fr 1fr 0.8fr` : isEditing ? `0.2fr 1fr 3fr 1fr` : `1fr 3fr 1fr`};
  justify-content: space-around;
  align-items: end;
  grid-column-gap: 0.5rem;

  margin: 0 1rem;
  padding-top: 1rem;
  padding-bottom: 1rem;
  ${({ isParent }) => (isParent ? 'padding-left: 1rem;' : 'padding-left: 1rem;')}

  label {
    width: 100%;
    padding-right: 1rem;
  }

  input {
    width: 100%;
    padding: 0.4rem;

    display: block;
    background-color: ${COLOR.LIGHT_GRAY_100};
    border-radius: 0.5rem;
    border: 1px solid ${COLOR.LIGHT_GRAY_200};

    font-size: 1.4rem;

    ${({ isParent }) =>
      !isParent &&
      css`
        margin-top: 0.2rem;
        margin-left: -0.2rem;
        font-size: 1.3rem;
      `}
  }

  input[type='color'] {
    width: 3rem;
    height: 3rem;
    margin-right: 0.5rem;

    padding: 0;
    outline: none;
    border: none;

    -webkit-appearance: none;
  }

  input[type='color']::-webkit-color-swatch-wrapper {
    padding: 0;
  }
  input[type='color']::-webkit-color-swatch {
    border: 1px solid ${COLOR.LIGHT_GRAY_200};
    border-radius: 0.5rem;
  }
`;

export const ColorPicker = styled.div`
  display: flex;

  input[type='text'] {
    width: 13rem;
  }
`;

// 역량 추가하는 곳
export const EditingListItem = styled.li`
  && {
    ${({ isParent }) =>
      isParent ? 'grid-template-columns: 1fr' : 'grid-template-columns: 0.1fr 4fr'};
  }
`;

export const NoContent = styled.li`
  && {
    padding: 2rem 3rem;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    grid-template-columns: 1fr;

    h3 {
      margin-top: 1.5rem;
      font-size: 1.8rem;
    }

    div {
      padding: 1rem;
    }
  }

  text-align: center;
`;

export const FormButtonWrapper = styled.div`
  width: 98%;
  margin: 0 auto;
  margin-top: 5rem;

  display: flex;

  > button {
    width: 100%;
    margin: 0 0.5rem;

    font-size: 1.4rem;
  }
`;

export const ColorChip = styled.div`
  height: 3.2rem;
  position: relative;

  ${({ visibility }) => visibility && `visibility: ${visibility}`}

  :before {
    content: '';
    width: 1.3rem;
    height: 1.3rem;
    position: absolute;
    top: 1rem;
    left: 0.4rem;
    border-radius: 50%;
    ${({ backgroundColor }) => backgroundColor && `background-color: ${backgroundColor}`}
  }
`;

// ✅ Refactoring-Start
export const ListHeader = styled.div`
  margin: 1rem 0;
  position: relative;

  display: flex;
  align-items: flex-end;
  justify-content: space-between;

  h3 {
    margin-left: 0.6rem;

    font-size: 1.8rem;
    font-weight: 400;
    color: ${COLOR.BLACK_900};
  }
`;

export const AddAbilityButton = styled.button`
  padding: 0.4rem 1rem;
  font-size: 1.2rem;

  :not(:disabled):hover {
    border-radius: 0.8rem;
    background-color: ${COLOR.BLACK_OPACITY_100};
  }
`;

export const AbilityList = styled.ul`
  width: 100%;
  ${({ height }) =>
    height &&
    `
    min-height: ${height}; 
    max-height: 64rem;
    `}

  background-color: ${COLOR.WHITE};
  border: 2px solid ${COLOR.LIGHT_GRAY_400};
  border-radius: 0.5rem;

  overflow: auto;

  > div {
    width: 100%;
    padding: 0.5rem 2rem;
  }

  > span {
    ${({ height }) =>
      height &&
      `
    height: ${height}; 
    `}
    display: flex;
    justify-content: center;
    align-items: center;

    font-size: 1.4rem;
  }

  && {
    margin-bottom: 1rem;
  }

  /* TODO: 수정하기 */
  /* li:not(:last-child) {
    border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};
  } */
`;

export const AbilityItem = styled.li`
  width: 100%;
  min-height: 6rem;
  padding: 0.5rem;

  display: grid;
  grid-template-columns: 0.2fr 1fr 2fr 0.9fr;
  justify-content: center;
  align-items: center;

  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};
`;

// 통일시킬 필요가 있음, 또는 컴포넌트화하기
export const Button = styled.button`
  ${({ backgroundColor, color }) => `
    background-color: ${backgroundColor};
    color: ${color};
  `}

  ${({ borderColor }) => borderColor && `border: 1px solid ${borderColor};`}
  font-size: ${({ fontSize }) => (fontSize ? ` ${fontSize}` : '1.4rem')};

  padding: 0.5rem 1.5rem;
  border-radius: 0.8rem;

  :not(:first-of-child) {
    margin-left: 0.5rem;
  }
`;

export const SubAbilityList = styled.ul`
  width: 100%;
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

  overflow: hidden;

  /* 따로 스타일 빼기 */
  li {
    width: 100%;
    min-height: 6rem;
    padding-left: 3rem;

    display: grid;
    grid-template-columns: 0.2fr 1fr 2fr 0.9fr;
    justify-content: center;
    align-items: center;

    border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

    span {
      text-align: center;
    }
  }

  ${({ isOpened }) =>
    isOpened
      ? css`
          height: 100%;
          opacity: 1;
        `
      : css`
          height: 0;
          opacity: 0;
        `};
`;
