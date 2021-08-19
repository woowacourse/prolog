import PropTypes from 'prop-types';
import COLOR from '../../constants/color';
import { BUTTON_SIZE, Container, Icon, Image } from './Button.styles';

const Button = ({
  children,
  size,
  alt,
  icon,
  css,
  backgroundImageUrl,
  onClick,
  type,
  disabled,
}) => {
  return (
    <Container size={size} icon={icon} css={css} onClick={onClick} type={type} disabled={disabled}>
      {icon && <Icon src={icon} alt={alt} size={size} hasText={!!children} />}
      {backgroundImageUrl && <Image backgroundImage={backgroundImageUrl} />}
      {children && <span size={size}>{children}</span>}
    </Container>
  );
};

Button.propTypes = {
  children: PropTypes.string,
  size: PropTypes.string,
  alt: PropTypes.string,
  icon: PropTypes.string,
  css: PropTypes.object,
  backgroundImageUrl: PropTypes.string,
  onClick: PropTypes.func,
  type: PropTypes.string,
};

Button.defaultProps = {
  size: BUTTON_SIZE.MEDIUM,
  css: {
    backgroundColor: COLOR.DARK_BLUE_800,
    color: COLOR.WHITE,
  },
};

export default Button;
