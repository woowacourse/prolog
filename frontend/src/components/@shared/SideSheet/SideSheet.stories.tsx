import { SideSheet, SideSheetProps } from './SideSheet';

const backdropRoot = document.createElement('div');
backdropRoot.setAttribute('id', 'backdrop-root');
document.body.append(backdropRoot);

const modalRoot = document.createElement('div');
modalRoot.setAttribute('id', 'overlay-root');
document.body.append(modalRoot);

export default {
  title: 'Component/SideSheet',
  component: SideSheet,
};

export const DefaultSideSheet = (args: SideSheetProps) => <SideSheet {...args} />;

DefaultSideSheet.args = {
  children: '테스트',
};
