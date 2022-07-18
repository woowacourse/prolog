import styled from '@emotion/styled';
import { COLOR } from '../../../constants';

export const Wrapper = styled.div`
  width: 35rem;
  min-height: 20rem;
  max-height: 40rem;
  position: absolute;
  right: 10%;
  bottom: 30%;

  border: 2px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};
  border-radius: 1rem;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.25);

  z-index: 2;
`;

export const Header = styled.div`
  width: 100%;
  min-height: 7rem;
  max-height: 8rem;
  padding: 1.2rem;

  text-align: center;

  border-bottom: 2px solid ${COLOR.LIGHT_GRAY_900};
  background-color: ${COLOR.WHITE};
  border-top-left-radius: 1rem;
  border-top-right-radius: 1rem;

  > #selectBox-title {
    margin: 0;
    display: block;

    font-size: 1.4rem;
    font-weight: 500;
  }

  > .ability-title {
    display: block;
    font-size: 1.2rem;
    overflow: auto;
  }
`;

export const AbilityList = styled.ul`
  width: 100%;
  min-height: 12rem;
  max-height: 32rem;
  padding: 1rem;

  display: flex;
  flex-direction: column;
  overflow-y: auto;

  background-color: ${COLOR.WHITE};
  border-bottom-left-radius: 1rem;
  border-bottom-right-radius: 1rem;

  > li:last-of-type {
    border: none;
  }
`;

export const Ability = styled.li`
  width: 100%;
  height: fit-content;

  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

  label {
    width: 100%;
    min-height: 4rem;

    display: flex;
    align-items: center;
    gap: 0.7rem;
  }

  :last-of-type {
    margin-bottom: 3rem;
  }
`;

export const ColorCircle = styled.div`
  display: inline-block;
  width: 14px;
  height: 14px;
  margin-top: 2px;

  background-color: ${({ backgroundColor }) => backgroundColor ?? 'transparent'};
  border: 1px solid ${COLOR.BLACK_OPACITY_100};
  border-radius: 7px;
`;

export const AbilityName = styled.span`
  width: 25rem;
  padding: 1rem 0;
  font-size: 1.2rem;
`;

export const EmptyAbilityGuide = styled.p`
  width: 100%;
  height: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Footer = styled.div`
  width: 100%;
  height: 3.5rem;
  position: absolute;
  bottom: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  background-color: ${COLOR.WHITE};
  border-bottom-left-radius: 1rem;
  border-bottom-right-radius: 1rem;

  > button {
    width: 98%;
    padding: 0.5rem;
    font-size: 1.4rem;
    background-color: ${COLOR.LIGHT_BLUE_600};
    border-radius: 1rem;

    :hover {
      color: ${COLOR.WHITE};
      background-color: ${COLOR.LIGHT_BLUE_900};
    }
  }
`;
