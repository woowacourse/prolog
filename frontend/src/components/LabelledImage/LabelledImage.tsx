import { css } from '@emotion/react';
import styled from '@emotion/styled';
import React, { ImgHTMLAttributes } from 'react';
import { COLOR } from '../../enumerations/color';

interface LabelledImageProps extends ImgHTMLAttributes<HTMLImageElement> {
  text: string;
  isSelected?: boolean;
}

const LabelledImage = ({ src, alt, text, isSelected }: LabelledImageProps) => {
  return (
    <StyledRoot isSelected={isSelected}>
      <img src={src} alt={alt} width="100px" height="100px" />
      <h3>{text}</h3>
    </StyledRoot>
  );
};

export default LabelledImage;

const StyledRoot = styled.div<Pick<LabelledImageProps, 'isSelected'>>`
  text-align: center;
  opacity: ${({ isSelected }) => (isSelected ? 1 : 0.6)};

  & > img {
    border-radius: 50%;
    border: ${({ isSelected }) => isSelected && `4px solid ${COLOR.LIGHT_BLUE_700}`};
  }
`;
