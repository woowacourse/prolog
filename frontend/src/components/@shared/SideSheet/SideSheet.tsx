import { PropsWithChildren } from 'react';
import ReactDOM from 'react-dom';
import { Backdrop, SideSheetContent } from './SideSheet.style';

export type SideSheetProps = {
  width?: string;
  onClickBackdrop(): void;
};

export const SideSheet = ({
  children,
  width,
  onClickBackdrop,
}: PropsWithChildren<SideSheetProps>) => {
  return (
    <>
      {ReactDOM.createPortal(
        <Backdrop onClick={onClickBackdrop} />,
        document.getElementById('backdrop-root') as HTMLElement
      )}
      {ReactDOM.createPortal(
        <SideSheetContent width={width}>{children}</SideSheetContent>,
        document.getElementById('overlay-root') as HTMLElement
      )}
    </>
  );
};
