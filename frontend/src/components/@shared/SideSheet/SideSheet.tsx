import { PropsWithChildren } from 'react';
import ReactDOM from 'react-dom';
import { Backdrop, SideSheetContent } from './SideSheet.style';

export type SideSheetProps = {
  width?: string;
  handleCloseSideSheet(): void;
};

export const SideSheet = ({
  children,
  width,
  handleCloseSideSheet,
}: PropsWithChildren<SideSheetProps>) => {
  return (
    <>
      {ReactDOM.createPortal(
        <Backdrop onClick={handleCloseSideSheet} />,
        document.getElementById('backdrop-root') as HTMLElement
      )}
      {ReactDOM.createPortal(
        <SideSheetContent width={width}>{children}</SideSheetContent>,
        document.getElementById('overlay-root') as HTMLElement
      )}
    </>
  );
};
