import styled from '@emotion/styled';
import { COLOR } from '../../../constants';

export const Container = styled.div`
  width: 100%;
  height: 640px;
  position: relative;

  background-image: url('../../assets/images/select-default-ability-bg.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center 140px;

  text-align: center;
`;

export const Header = styled.div`
  padding-top: 3rem;

  > #title {
    font-size: 2.1rem;
    font-weight: 500;
  }

  > #subtitle {
    font-size: 2.4rem;
    font-weight: bold;
  }
`;

export const ButtonWrapper = styled.div`
  width: 100%;
  position: absolute;
  bottom: 10rem;
  left: 0;

  display: flex;
  justify-content: center;
`;

export const FeButton = styled.button`
  width: 13rem;
  height: 12rem;

  background-image: url('../../assets/images/ability-fe.png');
  background-size: 60%;
  background-repeat: no-repeat;
  background-position: center bottom;

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
  background-position: center bottom;

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

export const Guide = styled.span`
  position: absolute;
  right: 0.3rem;
  top: 0;

  font-size: 1.4rem;
  font-weight: 500;
  color: ${COLOR.DARK_GRAY_600};
`;
