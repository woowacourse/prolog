/** @jsxImportSource @emotion/react */

import { SerializedStyles } from '@emotion/cache/node_modules/@emotion/utils';
import { ReactComponent as ViewIcon } from '../../assets/images/view.svg';
import styled from "@emotion/styled";
import {COLOR} from "../../constants";

const Container = styled.div<{ css?: SerializedStyles }>`
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.4rem;
  margin-left: 1rem;

  & > svg {
    margin-right: 0.25rem;
  }

  ${({ css }) => css};
`;

const Count = styled.span`
  vertical-align: bottom;
`;


const ViewCount = ({ css, count }: { css?: SerializedStyles; count: number }) => (
  <Container css={css}>
    <ViewIcon width="2rem" height="2rem" />
    <Count>{count}</Count>
  </Container>
);

export default ViewCount;
