import PropTypes from 'prop-types';

import NoProfileImage from '../../assets/images/no-profile-image.png';
import { Container, Image, Nickname } from './ProfilChip.styles';

const ProfileChip = ({ imageSrc, children, css, cssProps, onClick }) => {
  return (
    <Container css={cssProps || css} onClick={onClick}>
      <Image src={imageSrc} alt={`${children} 프로필 이미지`} />
      <Nickname>{children}</Nickname>
    </Container>
  );
};

ProfileChip.propTypes = {
  imageSrc: PropTypes.string,
  children: PropTypes.string.isRequired,
  css: PropTypes.object,
  cssProps: PropTypes.object,
  onClick: PropTypes.func,
};

ProfileChip.defaultProps = {
  imageSrc: NoProfileImage,
};

export default ProfileChip;
