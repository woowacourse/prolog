import styled from '@emotion/styled';

const Container = styled.div`
  height: 4.8rem;
  border: 1px solid #a7a7a7;
  border-radius: 1.6rem;
  background-color: #ffffff;
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
  color: #383838;
`;

export { Container, Image, Nickname };
