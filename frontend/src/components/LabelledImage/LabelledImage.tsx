import styled from '@emotion/styled';
import { ImgHTMLAttributes } from 'react';
import { COLOR } from '../../enumerations/color';

interface LabelledImageProps extends ImgHTMLAttributes<HTMLImageElement> {
  text: string;
  isSelected?: boolean;
}

const LabelledImage = ({ text, isSelected, ...props }: LabelledImageProps) => {
  return (
    <StyledRoot isSelected={isSelected}>
      <img width="100px" height="100px" {...props} />
      <h3>{text}</h3>
    </StyledRoot>
  );
};

export default LabelledImage;

const StyledRoot = styled.div<Pick<LabelledImageProps, 'isSelected'>>`
  text-align: center;
  opacity: ${({ isSelected }) => (isSelected ? 1 : 0.6)};
  cursor: pointer;

  & > img {
    border-radius: 50%;
    border: ${({ isSelected }) => isSelected && `4px solid ${COLOR.LIGHT_BLUE_700}`};
  }
`;
