import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  & > *:not(:last-child) {
    margin-bottom: 2rem;
  }

  .tui-editor-contents {
    font-size: 1.6rem;
  }
`;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Mission = styled.div`
  font-size: 2rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
`;

const IssuedDate = styled.div`
  color: ${COLOR.DARK_GRAY_800};
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
  padding: 0.8rem;
  cursor: pointer;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

const ViewerWrapper = styled.div`
  word-break: break-all;
  
  .tui-editor-contents h1, .tui-editor-contents h2, .tui-editor-contents h3, .tui-editor-contents h4, .tui-editor-contents h5, .tui-editor-contents h6 {
      margin-top: 24px;
      margin-bottom: 16px;
      font-weight: 600;
      line-height: 1.25;
  }
  
  .tui-editor-contents h1 {
    padding-bottom: .3em;
    font-size: 2em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }
  
  .tui-editor-contents h2 {
    padding-bottom: .3em;
    font-size: 1.5em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }
  
  .tui-editor-contents h3 {
    font-size: 1.25em;
  }
      
  .tui-editor-contents ul > li::before {
    background-color: #222;
  }
  
  .tui-editor-contents ol > li::before {
    color: #222;  
  }
`;

export { CardInner, SubHeader, Mission, Title, Tags, IssuedDate, ProfileChipStyle, ViewerWrapper };
