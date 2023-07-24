import { SerializedStyles } from '@emotion/react';
import { BUTTON_SIZE, Container, Icon, Image } from './Button.styles';

interface ButtonProps {
  children?: React.ReactNode;
  size?: string;
  alt?: string;
  icon?: string;
  css?: SerializedStyles | SerializedStyles[];
  cssProps?: SerializedStyles;
  backgroundImageUrl?: string;
  type?: 'button' | 'submit' | 'reset';
  disabled?: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
}

const Button = ({
  children,
  size = BUTTON_SIZE.MEDIUM,
  alt = '',
  icon,
  css = { name: '', styles: '' },
  cssProps,
  backgroundImageUrl,
  type,
  disabled,
  onClick,
}: ButtonProps) => {
  return (
    <Container size={size} css={cssProps || css} onClick={onClick} type={type} disabled={disabled}>
      {icon && <Icon src={icon} alt={alt} size={size} hasText={!!children} />}
      {backgroundImageUrl && <Image backgroundImage={backgroundImageUrl} />}
      {children && <span>{children}</span>}
    </Container>
  );
};

export default Button;
