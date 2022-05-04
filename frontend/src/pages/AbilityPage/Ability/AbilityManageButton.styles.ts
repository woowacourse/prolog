import styled from '@emotion/styled';

export const Wrapper = styled.div`
  margin-left: 0.5rem;
  min-width: 17rem;
  text-align: right;

  button {
    margin-left: 0.2rem;
  }
`;

// 통일시킬 필요가 있음, 또는 컴포넌트화하기
export const Button = styled.button<{
  backgroundColor?: string;
  borderColor?: string;
  fontSize?: string;
}>`
  ${({ backgroundColor, color }) => `
    background-color: ${backgroundColor};
    color: ${color};
  `}

  ${({ borderColor }) => borderColor && `border: 1px solid ${borderColor};`}
  font-size: ${({ fontSize }) => (fontSize ? ` ${fontSize}` : '1.4rem')};

  padding: 0.5rem 1.5rem;
  border-radius: 0.8rem;

  :not(:first-of-child) {
    margin-left: 0.5rem;
  }
`;
