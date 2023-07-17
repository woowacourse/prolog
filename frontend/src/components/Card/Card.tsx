import { SerializedStyles } from '@emotion/react';
import { Container, Title } from './Card.styles';

interface Props {
  children: JSX.Element;
  title?: string;
  size: string;
  cssProps?: SerializedStyles;
  css?: SerializedStyles;
  onClick?: () => void;
}

const Card = ({ title, children, size, cssProps, css, onClick }: Props) => {
  return (
    <div>
      {title && <Title>{title} </Title>}
      <Container size={size} css={cssProps || css} onClick={onClick}>
        {children}
      </Container>
    </div>
  );
};

export default Card;
