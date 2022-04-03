import styled from '@emotion/styled';

import { COLOR } from '../../constants';

export const AbilityGraphContainer = styled.div`
  width: 100%;
  margin: 2rem 0;
  display: flex;
  flex-direction: column;
  align-items: baseline;

  > div {
    width: 100%;
    margin: 0 auto;

    #ability-graph-wrapper {
      width: 100%;
    }
  }
`;

export const Title = styled.span`
  margin: 0;
  margin-bottom: 0.2rem;
  display: block;

  color: ${COLOR.BLACK_900};
  font-size: 1.6rem;
`;
