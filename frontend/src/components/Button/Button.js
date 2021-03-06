import PropTypes from 'prop-types';
import { BUTTON_SIZE, Container, Icon, Image } from './Button.styles';

const Button = ({
  children,
  size,
  alt,
  icon,
  css,
  cssProps,
  backgroundImageUrl,
  onClick,
  type,
  disabled,
}) => {
  return (
    <Container
      size={size}
      icon={icon}
      css={cssProps || css}
      onClick={onClick}
      type={type}
      disabled={disabled}
    >
      {icon && <Icon src={icon} alt={alt} size={size} hasText={!!children} />}
      {backgroundImageUrl && <Image backgroundImage={backgroundImageUrl} />}
      {children && <span size={size}>{children}</span>}
    </Container>
  );
};

Button.propTypes = {
  children: PropTypes.node,
  size: PropTypes.string,
  alt: PropTypes.string,
  icon: PropTypes.string,
  css: PropTypes.object,
  cssProps: PropTypes.object,
  backgroundImageUrl: PropTypes.string,
  onClick: PropTypes.func,
  type: PropTypes.string,
  disabled: PropTypes.bool,
};

Button.defaultProps = {
  size: BUTTON_SIZE.MEDIUM,
};

export default Button;
