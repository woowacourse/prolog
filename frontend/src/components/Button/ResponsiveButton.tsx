import * as Styled from './ResponsiveButton.styles';
import { ButtonHTMLAttributes } from 'react';

export interface ResponsiveButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text?: string;
  fontSize?: string;
  color?: string;
  backgroundColor?: string;
  height?: string;
}

const ResponsiveButton = ({
  text,
  fontSize,
  color,
  backgroundColor,
  height,
  ...props
}: ResponsiveButtonProps) => {
  return (
    <Styled.Root
      fontSize={fontSize}
      color={color}
      backgroundColor={backgroundColor}
      height={height}
      {...props}
    >
      {text}
    </Styled.Root>
  );
};

export default ResponsiveButton;
