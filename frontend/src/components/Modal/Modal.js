import React from 'react';
import { ModalSection, ModalInner } from './Modal.styles';

const Modal = ({ children, width, height }) => {
  return (
    <ModalSection>
      <ModalInner role="dialog" width={width} height={height}>
        {children}
      </ModalInner>
    </ModalSection>
  );
};

export default Modal;
