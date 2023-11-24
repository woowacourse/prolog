import Lottie from 'react-lottie';
import { NotFoundAnimation } from '../../assets/lotties';
import { Container } from './NotFound.styles';

const NotFound = () => {
  const defaultOptions = {
    height: '100%',
    loop: true,
    autoplay: true,
    animationData: NotFoundAnimation,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  };
  return (
    <Container>
      <Lottie options={defaultOptions} />
    </Container>
  );
};

export default NotFound;
