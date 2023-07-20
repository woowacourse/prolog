import styled from '@emotion/styled';
import COLOR from '../../../constants/color';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.4rem;
  width: 30rem;
  height: 5rem;
  border-radius: 0.4rem;
  background-color: ${COLOR.WHITE};
  color: ${COLOR.DARK_GRAY_900};
  box-shadow: 0px 0px 10px 3px rgba(0, 0, 0, 0.1);
  z-index: 100;
  position: absolute;
  bottom: 3.2rem;
  left: 50%;
  transform: translateX(-50%);

  @keyframes fade {
    0% {
      opacity: 0;
      bottom: 0;
    }
    25% {
      opacity: 1;
      bottom: 3.2rem;
    }
    50% {
      opacity: 1;
      bottom: 3.2rem;
    }
    100% {
      opacity: 0;
      bottom: 0;
    }
  }
  @-webkit-keyframes fade {
    0% {
      opacity: 0;
      bottom: 0;
    }
    25% {
      opacity: 1;
      bottom: 3.2rem;
    }
    50% {
      opacity: 1;
      bottom: 3.2rem;
    }
    100% {
      opacity: 0;
      bottom: 0;
    }
  }

  animation: fade 3s;
  -webkit-animation: fade 3s;
`;

export { Container };
