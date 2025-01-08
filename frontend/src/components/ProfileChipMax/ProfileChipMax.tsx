import { SerializedStyles } from '@emotion/react';
import PropTypes from 'prop-types';
import { PropsWithChildren } from 'react';

import NoProfileImage from '../../assets/images/no-profile-image.png';
import { Container, Image, Nickname } from './ProfilChipMax.styles';

interface Props {
  imageSrc: string;
  css?: SerializedStyles;
  // (event?: MouseEvent) => void와 MouseEventHandler가 동시에 들어올 수 있는 구조라 방법을 찾지 못해 any로 하였습니다.
  onClick: any;
  cssProps: SerializedStyles;
}

const ProfileChipMax = ({ imageSrc, children, css, cssProps, onClick }: PropsWithChildren<Props>) => {
  return (
    <Container css={cssProps || css} onClick={onClick}>
      <Image src={imageSrc} alt={`${children} 프로필 이미지`} />
      <Nickname>{children}</Nickname>
    </Container>
  );
};

ProfileChipMax.propTypes = {
  imageSrc: PropTypes.string,
  children: PropTypes.string,
  css: PropTypes.object,
  cssProps: PropTypes.object,
  onClick: PropTypes.func,
};

ProfileChipMax.defaultProps = {
  children: 'nickname',
  imageSrc: NoProfileImage,
};

export default ProfileChipMax;
