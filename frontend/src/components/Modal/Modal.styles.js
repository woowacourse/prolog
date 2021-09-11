import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const ModalSection = styled.section`
  position: absolute;
  width: 100%;
  height: 100%;
`;

const ModalInner = styled.article`
  position: absolute;
  width: ${({ width }) => width ?? '100%'};
  height: ${({ height }) => height ?? '100%'};
  right: 0;
  bottom: 0;

  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  background-color: ${COLOR.WHITE};
  border-radius: 0.5rem;
  box-shadow: 0px 2px 4px 0px ${COLOR.BLACK_OPACITY_300};
`;

export { ModalSection, ModalInner };
