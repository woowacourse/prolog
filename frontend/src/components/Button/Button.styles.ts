import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const BUTTON_SIZE = {
  XX_SMALL: 'XX_SMALL',
  X_SMALL: 'X_SMALL',
  SMALL: 'SMALL',
  MEDIUM: 'MEDIUM',
  LARGE: 'LARGE',
};

const containerSizeStyle = {
  XX_SMALL: {
    width: '3rem',
    height: '3rem',
    fontSize: '1.4rem',
    lineHeight: '1rem',
    borderRadius: '8px',
  },
  X_SMALL: {
    width: '9.2rem',
    height: '3.6rem',
    lineHeight: '1.5rem',
    borderRadius: '16px',
  },
  SMALL: {
    width: '12.2rem',
    height: '4.8rem',
    fontSize: '2rem',
    lineHeight: '3rem',
    borderRadius: '16px',
  },
  MEDIUM: {
    width: '18rem',
    height: '6.4rem',
    fontSize: '3rem',
    lineHeight: '4.5rem',
    borderRadius: '20px',
  },
  LARGE: {
    width: '63rem',
    height: '6rem',
    fontSize: '2.4rem',
    lineHeight: '3.6rem',
    borderRadius: '22px',
  },
};

const imageSizeStyle = {
  X_SMALL: {
    width: '2rem',
    height: '2rem',
  },
  SMALL: {
    width: '2.5rem',
    height: '2.5rem',
  },
  MEDIUM: {
    width: '3.5rem',
    height: '3.5rem',
  },
  LARGE: {
    width: '3rem',
    height: '3rem',
  },
};

const Container = styled.button<{ size: string; css: {} }>`
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;

  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};

  ${({ size }) => containerSizeStyle[size] || containerSizeStyle.MEDIUM};
  ${({ css }) => css};
`;

const Icon = styled.img<{ hasText: boolean; size: string }>`
  margin-right: ${({ hasText }) => (hasText ? '0.5em' : '0')};

  ${({ size }) => imageSizeStyle[size] || imageSizeStyle.MEDIUM};
`;

const Image = styled.div<{ backgroundImage: string }>`
  background-image: url(${({ backgroundImage }) => backgroundImage});
  background-size: contain;
  width: inherit;
  height: inherit;
  border-radius: inherit;
`;

export { BUTTON_SIZE, Container, Icon, Image };
