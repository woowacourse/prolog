import { SerializedStyles } from '@emotion/cache/node_modules/@emotion/utils';
import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const Container = styled.div<{ css?: SerializedStyles }>`
  flex-shrink: 0;
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.4rem;
  margin-top: 0.5rem;

  & > svg {
    margin-right: 0.25rem;
  }

  ${({ css }) => css};
`;

const Count = styled.span`
  vertical-align: top;
`;

export { Container, Count };
