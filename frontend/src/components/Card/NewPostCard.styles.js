import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const TitleInput = styled.input`
  width: 100%;
  height: 5.4rem;
  padding: 0;
  margin: 1rem 0;

  font-size: 4.4rem;
  font-weight: 700;

  border: none;
  outline: none;

  &::placeholder {
    font-weight: 500;
  }
`;

const TitleCount = styled.div`
  display: flex;
  justify-content: flex-end;
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const EditorWrapper = styled.div`
  *.toastui-editor-defaultUI {
    border: none;
  }

  .CodeMirror pre.CodeMirror-placeholder,
  pre.CodeMirror-line {
    padding-left: 0;
  }

  * {
    font-size: 1.6rem;
  }
`;

export { TitleInput, TitleCount, EditorWrapper };
