import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const Form = styled.form`
  > button {
    position: absolute;
    bottom: 2rem;
    left: 50%;
    transform: translateX(-50%);
    width: 90%;

    background-color: ${COLOR.DARK_BLUE_300};
    color: ${COLOR.WHITE};
    font-size: 1.4rem;
    border-radius: 1rem;

    :hover {
      background-color: ${COLOR.DARK_BLUE_300};
      color: ${COLOR.WHITE};
    }

    :disabled {
      background-color: ${COLOR.LIGHT_BLUE_300};
      color: ${COLOR.DARK_GRAY_800};
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
    color: ${COLOR.BLACK_300};

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

  > p {
    margin-top: 3rem;
    text-align: center;
  }

  ul {
    width: 100%;
    max-height: 47rem;
    margin-bottom: 2rem;

    overflow: auto;

    ::-webkit-scrollbar {
      display: none;
    }
  }

  span {
    display: block;
    text-align: left;

    padding: 0.2rem 1rem;
    margin-bottom: 0.5rem;

    font-size: 1.5rem;
    font-weight: 400;
    color: ${COLOR.BLACK_700};
  }
`;

const DeleteGuide = styled.span`
  &&& {
    padding: 0 1rem;
    font-size: 1.3rem;
    margin-top: -0.5rem;
    color: ${COLOR.LIGHT_BLUE_900};
  }
`;

const StudyLog = styled.li`
  label {
    width: 100%;
    padding: 1rem;
    margin: 1.5rem auto 2rem;
    height: 11rem;

    border-radius: 1rem;
    border: ${({ isChecked }) =>
      isChecked ? `1px solid ${COLOR.LIGHT_BLUE_200}` : `1px solid ${COLOR.LIGHT_GRAY_800}`};
    ${({ isChecked }) => isChecked && `background-color: ${COLOR.LIGHT_BLUE_100}`};

    display: flex;
    align-items: center;

    div {
      margin: 0 1rem;

      p {
        font-size: 1.3rem;
        color: ${({ isChecked }) =>
          isChecked ? `${COLOR.DARK_GRAY_700}` : `${COLOR.DARK_GRAY_400}`};
      }

      h4 {
        font-size: 1.6rem;
        font-weight: 500;

        overflow: hidden;
        text-overflow: ellipsis;
        word-break: break-all; // 영문 자르기용
        word-wrap: break-word;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
      }
    }
  }
`;

const ReadMoreButton = styled.button`
  width: 100%;
  height: 2.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_100};
  background-color: ${COLOR.LIGHT_GRAY_100};
  font-size: 1.4rem;
  text-align: center;
`;

export {
  Form,
  DeleteGuide,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
  StudyLog,
  ReadMoreButton,
};
