import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const Container = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
`;

const ModalInner = styled.article`
  position: absolute;
  width: 50%;
  right: 0;
  bottom: 0;
  height: 80%;

  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  background-color: ${COLOR.WHITE};
  border-radius: 0.5rem;
  box-shadow: 0px 2px 4px 0px ${COLOR.BLACK_OPACITY_300};

  > button {
    position: absolute;
    bottom: 2rem;
    left: 50%;
    transform: translateX(-50%);
    width: 90%;

    font-size: 1.4rem;
    border-radius: 1rem;

    :hover {
      background-color: ${COLOR.LIGHT_BLUE_900};
      color: ${COLOR.WHITE};
    }
  }
`;

const TitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-center;
  padding: 1rem 2rem;

  height: 5rem;
  line-height: 1.5;
  background-color: ${COLOR.LIGHT_BLUE_300};

  h2 {
    font-size: 2rem;
    color: ${COLOR.BLACK_800};
  }

  button {
    font-size: 1.4rem;
    color: ${COLOR.BLACK_600};

    :hover {
      text-decoration: underline;
    }
  }
`;

const SelectBoxContainer = styled.section`
  width: 100%;
  padding: 2rem 1.5rem;
  border-bottom: 1px solid ${COLOR.DARK_GRAY_600};

  > h3 {
    padding: 0.2rem 1rem;

    font-size: 1.6rem;
    font-weight: 400;
    color: ${COLOR.BLACK_700};
  }

  > label {
    margin: 0 auto;
  }
`;

const StudyLogListContainer = styled.section`
  width: 100%;
  padding: 2rem;

  ul {
    width: 100%;
    max-height: 46rem;
    margin-bottom: 2rem;

    overflow: auto;

    ::-webkit-scrollbar {
      display: none;
    }

    label {
      width: 100%;
      padding: 1rem;
      margin: 1.5rem auto 2rem;
      height: 8rem;
      border: 1px solid ${COLOR.LIGHT_GRAY_800};
      border-radius: 1rem;

      display: flex;
      align-items: center;

      div {
        margin: 0 1rem;

        p {
          font-size: 1.3rem;
          color: ${COLOR.LIGHT_GRAY_900};
        }

        h4 {
          font-size: 1.6rem;
          font-weight: 500;
        }
      }
    }
  }

  span {
    display: block;
    text-align: left;

    padding: 0.2rem 1rem;

    font-size: 1.6rem;
    font-weight: 400;
    color: ${COLOR.BLACK_700};
  }
`;

export { Container, ModalInner, TitleContainer, SelectBoxContainer, StudyLogListContainer };
