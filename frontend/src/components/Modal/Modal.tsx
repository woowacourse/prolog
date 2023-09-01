import { HTMLAttributes } from 'react';
import { ModalSection, ModalInner } from './Modal.styles';

export interface ModalProps extends HTMLAttributes<HTMLElement> {
  width: string;
  height: string;
}

const Modal = ({ children, width, height }: ModalProps) => {
  return (
    <ModalSection>
      <ModalInner role="dialog" width={width} height={height}>
        {children}
      </ModalInner>
    </ModalSection>
  );
};

export default Modal;
