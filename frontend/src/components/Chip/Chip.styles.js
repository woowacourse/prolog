import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import { getTextColor } from '../../utils/textColorPicker';

const Container = styled.div`
  width: ${({ width }) => (width ? width : 'fit-content')};
  height: ${({ height }) => (height ? height : 'fit-content')};
  max-width: ${({ maxWidth }) => maxWidth};
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.LIGHT_GRAY_200};
  color: ${({ backgroundColor }) =>
    backgroundColor ? getTextColor(backgroundColor) : COLOR.BLACK_900};
  padding: 0.2rem 0.8rem;
  border-radius: 5rem;
  margin-right: 1rem;

  display: flex;
  justify-content: center;
  align-items: center;

  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;

  cursor: default;

  button {
    width: 1.6rem;
    height: 1.6rem;
    border-radius: 50%;
    background-color: inherit;
    margin-left: 0.3rem;

    display: inline-flex;
    justify-content: center;
    align-items: center;

    &:hover {
      background-color: ${COLOR.RED_100};

      svg {
        stroke: ${COLOR.RED_600};
      }
    }
  }
`;

const ChipText = styled.span`
  text-align: ${({ textAlign }) => (textAlign ? textAlign : 'center')};
  font-size: ${({ fontSize }) => (fontSize ? fontSize : '1.4rem')};

  color: inherit;

  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
`;

export { Container, ChipText };
