import PropTypes from 'prop-types';
import NoProfileImage from '../../assets/images/no-profile-image.png';
import { Container, Image, Nickname } from './ProfilChip.styles';

const ProfileChip = ({ imageSrc, children, css, onClick }) => {
  return (
    <Container css={css} onClick={onClick}>
      <Image src={imageSrc} alt={`${children} 프로필 이미지`} />
      <Nickname>{children}</Nickname>
    </Container>
  );
};

ProfileChip.propTypes = {
  imageSrc: PropTypes.string,
  children: PropTypes.string.isRequired,
};

ProfileChip.defaultProps = {
  imageSrc: NoProfileImage,
  children: '',
};

export default ProfileChip;
