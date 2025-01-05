import { SerializedStyles } from '@emotion/serialize';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div<{ css?: SerializedStyles }>`
  height: 4.8rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  border-radius: 1.6rem;
  background-color: ${COLOR.WHITE};
  padding: 0.5rem;
  box-sizing: border-box;
  display: inline-flex;
  align-items: center;

  ${({ css }) => css}
`;

const Image = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 12px;
  //border: 1px solid ${COLOR.LIGHT_GRAY_100};
`;

const Nickname = styled.span`
  flex-shrink: 0;

  padding: 0 5px;
  max-width: 100px;

  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;

  font-size: 1.6rem;
  line-height: 1.5;
  color: ${COLOR.DARK_GRAY_900};
`;

export { Container, Image, Nickname };
