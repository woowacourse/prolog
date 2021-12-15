import { SerializedStyles } from '@emotion/cache/node_modules/@emotion/utils';
import { ReactComponent as ViewIcon } from '../../assets/images/view.svg';
import { Container, Count } from './ViewCount.styles';

const ViewCount = ({ css, count }: { css?: SerializedStyles; count: number }) => (
  <Container css={css}>
    <ViewIcon width="2rem" height="2rem" />
    <Count>{count}</Count>
  </Container>
);

export default ViewCount;
