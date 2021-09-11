import React, { useEffect, useRef } from 'react';
import { ModalSection, ModalInner } from './Modal.styles';

const Modal = ({ children, onModalClose, width, height }) => {
  const modalRef = useRef(null);

  useEffect(() => {
    const closeStudyLogList = (event) => {
      event.stopPropagation();

      if (!modalRef.current?.contains(event.target)) {
        onModalClose();
      }
    };
    document.addEventListener('click', closeStudyLogList);

    return () => document.removeEventListener('click', closeStudyLogList);
  }, [onModalClose, modalRef]);

  return (
    <ModalSection>
      <ModalInner ref={modalRef} role="dialog" width={width} height={height}>
        {children}
      </ModalInner>
    </ModalSection>
  );
};

export default Modal;
