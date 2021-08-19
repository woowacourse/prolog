import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
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
  width: 3.8rem;
  height: 3.8rem;
  border-radius: 1.3rem;
`;

const Nickname = styled.span`
  margin-left: 0.8rem;
  font-size: 1.6rem;
  line-height: 1.5;
  color: ${COLOR.DARK_GRAY_900};
`;

export { Container, Image, Nickname };
