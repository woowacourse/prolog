import React from 'react';
import { Container, Image } from './NotFound.styles';
import notFound from '../../assets/images/not-found.png';

const NotFound = () => (
  <Container>
    <Image src={notFound} />
  </Container>
);

export default NotFound;
