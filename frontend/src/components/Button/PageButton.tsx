import Button from './Button';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

interface PageButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
  children: number;
  selected?: boolean;
}

const PageButton = ({ children, ...props }: PageButtonProps) => {
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
