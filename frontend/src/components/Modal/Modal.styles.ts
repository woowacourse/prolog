import styled from '@emotion/styled';
import { COLOR } from '../../constants';
import { ModalProps } from './Modal';

const ModalSection = styled.section`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100vh;

  background-color: ${COLOR.BLACK_OPACITY_100};
`;

const ModalInner = styled.article<ModalProps>`
  position: absolute;
  width: ${({ width }) => width ?? '50%'};
  height: ${({ height }) => height ?? '50%'};
  left: 0;
  top: 0;

  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  background-color: ${COLOR.WHITE};
  border-radius: 0.5rem;
  box-shadow: 0px 2px 4px 0px ${COLOR.BLACK_OPACITY_300};
`;

export { ModalSection, ModalInner };
