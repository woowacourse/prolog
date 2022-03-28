import styled from '@emotion/styled';
import { COLOR } from '../../../constants';

export const Wrapper = styled.div`
  width: 35rem;
  height: 40rem;
  position: absolute;
  bottom: 2rem;
  right: 1rem;

  border: 2px solid ${COLOR.LIGHT_GRAY_900};
  background-color: ${COLOR.WHITE};
  border-radius: 1rem;

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
  height: 32rem;
  padding: 1rem;

  display: flex;
  flex-direction: column;
  overflow-y: auto;

  background-color: ${COLOR.WHITE};

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
