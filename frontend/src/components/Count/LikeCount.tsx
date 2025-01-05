/** @jsxImportSource @emotion/react */

import { SerializedStyles } from '@emotion/cache/node_modules/@emotion/utils';
import { ReactComponent as LikeIcon } from '../../assets/images/heart.svg';
import styled from "@emotion/styled";
import {COLOR} from "../../constants";

const Container = styled.div<{ css?: SerializedStyles }>`
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.4rem;

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
    <LikeIcon width="2rem" height="2rem" />
    <Count>{count}</Count>
  </Container>
);

export default ViewCount;
