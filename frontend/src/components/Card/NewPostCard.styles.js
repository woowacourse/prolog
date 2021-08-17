import styled from '@emotion/styled';

const TitleInput = styled.input`
  width: 100%;
  height: 3rem;
  padding: 0;
  margin: 1rem 0;

  font-size: 3.4rem;
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
  color: #808080;
`;

const EditorWrapper = styled.div`
  *.tui-editor-defaultUI {
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
