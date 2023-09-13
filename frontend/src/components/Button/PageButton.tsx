import Button from './Button';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';
import { ComponentPropsWithoutRef, PropsWithChildren } from 'react';

interface PageButtonProps extends ComponentPropsWithoutRef<'button'> {
  size: string;
  css: ReturnType<typeof css>;
  selected?: boolean;
}

const PageButton = ({ children, ...props }: PropsWithChildren<PageButtonProps>) => {
  return (
    <Button {...props} css={props.selected ? selectedStyle : unselectedStyle}>
      {children}
    </Button>
  );
};

const selectedStyle = css`
  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};
`;

const unselectedStyle = css`
  background-color: transparent;
  color: ${COLOR.BLACK_800};
`;

export default PageButton;
