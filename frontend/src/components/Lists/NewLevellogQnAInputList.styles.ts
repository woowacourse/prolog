import styled from '@emotion/styled';

import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import { ReactComponent as PlusIcon } from '../../assets/images/plus_icon.svg';
import { COLOR } from '../../constants';

export const Container = styled.div`
  ${FlexStyle}
  ${FlexColumnStyle}
      gap:15px;
`;
export const QnAItemsWrapper = styled.div`
  ${FlexStyle}
  ${FlexColumnStyle}
      gap:15px;
`;
export const Label = styled.label`
  font-size: larger;
`;
export const AddQnAButton = styled(PlusIcon)`
  width: 40px;
  height: 40px;
  fill: ${COLOR.LIGHT_BLUE_500};

  align-self: flex-end;

  cursor: pointer;
  transition: all ease-in-out 0.1s;
  :hover {
    transform: scale(1.1);
    opacity: 0.6;
  }
`;
