import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const Container = styled.div`
  width: 100%;
  height: 100%;

  > div {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  h2 {
    text-align: left;
    font-weight: 600;

    span {
      font-weight: 400;
      font-size: 1.6rem;
      padding-left: 0.5rem;
    }
  }

  button:disabled {
    opacity: 0.3;
  }

  button:not(:disabled):hover {
    filter: brightness(0.9);
  }

  > *:not(:last-child) {
    margin-bottom: 1rem;
  }
`;

const AbilityList = styled.ul`
  width: 100%;
  background-color: ${COLOR.WHITE};

  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  border-radius: 1rem;

  li {
    width: 100%;

    display: grid;
    grid-template-columns: 0.2fr 1fr 2fr 1fr;
    justify-content: center;
    align-items: center;

    padding: 0.5rem 2rem;
    border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

    :last-child {
      border: none;
    }
  }
`;

const openList = keyframes`
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

const SubAbilityList = styled.ul`
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

  overflow: hidden;

  li {
    padding-left: 3rem;

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
        `}
`;

const ManageButtonList = styled.div`
  display: flex;
  justify-content: right;
`;

const ArrowButton = styled.button`
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

const Button = styled.button`
  ${({ backgroundColor, color }) => `
    background-color: ${backgroundColor};
    color: ${color};
  `}

  ${({ borderColor }) => borderColor && `border: 1px solid ${borderColor};`}

  padding: 0.5rem 1.5rem;
  border-radius: 1rem;

  :not(:first-child) {
    margin-left: 0.5rem;
  }
`;

const FormContainer = styled.div`
  padding: 1rem 0 2rem;
  ${({ isParent }) => !isParent && 'padding-bottom: 1rem;'}

  > div {
    width: 100%;
    padding: 1rem 0 0 1rem;

    display: flex;
  }

  h3 {
    font-size: 1.6rem;
    font-weight: 500;
  }
`;

const ListForm = styled.form`
  display: grid;
  grid-template-columns: ${({ isParent }) => (isParent ? `1fr 2fr 1fr 0.8fr` : `1fr 3fr 0.8fr`)};
  justify-content: center;
  align-items: end;

  padding-top: 1rem;
  padding-bottom: 1rem;
  ${({ isParent }) => (isParent ? 'padding-left: 1rem;' : 'padding-left: 4rem;')}

  label {
    width: 100%;

    padding-right: 1rem;
  }

  input {
    display: block;
    width: 100%;
    background-color: ${COLOR.LIGHT_GRAY_100};

    border-radius: 0.5rem;
    border: 1px solid ${COLOR.LIGHT_GRAY_200};

    font-size: 1.4rem;
    padding: 0.4rem 0.5rem;
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

const ColorPicker = styled.div`
  display: flex;

  input[type='text'] {
    width: 13rem;
  }
`;

const ListHeader = styled.li`
  && {
    grid-template-columns: 1fr;
    background-color: ${COLOR.LIGHT_GRAY_200};
    border-top-left-radius: 1rem;
    border-top-right-radius: 1rem;
  }
`;

const EditingListItem = styled.li`
  && {
    ${({ isParent }) =>
      isParent ? 'grid-template-columns: 1fr' : 'grid-template-columns: 0.2fr 4fr'};
  }

  form {
    padding-left: 0;
  }
`;

const NoContent = styled.li`
  && {
    padding: 2rem 3rem;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    grid-template-columns: 1fr;

    h3 {
      font-size: 1.8rem;
    }

    div {
      padding: 1rem;
    }
  }

  text-align: center;
`;

const SubAbilityNameInput = styled.input`
  background-color: ${COLOR.WHITE}cc;
  border: none;
`;

const SubAbilityDescriptionInput = styled.input`
  width: calc(100% - 1.4rem);
`;

export const NoAbilityContainer = styled.div`
  width: 100%;
  height: 640px;

  background-image: url('../../assets/images/ability-template-body.png');
  background-size: 70%;
  background-repeat: no-repeat;
  background-position: 58% bottom;

  position: relative;
`;

export const FeButton = styled.button`
  width: 13rem;
  height: 12rem;

  background-image: url('../../assets/images/ability-fe.png');
  background-size: 60%;
  background-repeat: no-repeat;
  background-position: center center;

  :hover,
  :active {
    background-position: top;

    span {
      transform: scale(1.4);
      color: ${COLOR.RED_500};
    }
  }

  position: relative;

  span {
    color: ${COLOR.BLACK_900};

    font-size: 1.8rem;
    font-weight: bold;

    position: absolute;
    top: 0;
    left: -80%;
  }
`;

export const BeButton = styled.button`
  width: 13rem;
  height: 12rem;

  background-image: url('../../assets/images/ability-be.png');
  background-size: 60%;
  background-repeat: no-repeat;
  background-position: center center;

  :hover,
  :active {
    background-position: top;

    span {
      transform: scale(1.4);
      color: ${COLOR.DARK_BLUE_300};
    }
  }

  position: relative;

  span {
    color: ${COLOR.BLACK_900};

    font-size: 1.8rem;
    font-weight: bold;

    position: absolute;
    top: 0;
    right: -80%;
  }
`;

export const AnotherWay = styled.span`
  position: absolute;
  right: 0;
  top: 0;

  font-size: 1.4rem;
  color: ${COLOR.DARK_GRAY_600};
`;

export {
  Container,
  AbilityList,
  SubAbilityList,
  ManageButtonList,
  ArrowButton,
  Button,
  FormContainer,
  ListForm,
  ColorPicker,
  EditingListItem,
  ListHeader,
  NoContent,
  SubAbilityNameInput,
  SubAbilityDescriptionInput,
};
