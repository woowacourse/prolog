/** @jsxImportSource @emotion/react */

import DropdownMenu, { DropdownMenuProps } from './DropdownMenu';
import { Story, Meta } from '@storybook/react';
import { css } from '@emotion/react';

export default {
  title: 'Component/DropdownMenu',
  component: DropdownMenu,
  argTypes: { children: { control: 'text' } },
} as Meta<typeof DropdownMenu>;

const Template: Story<React.PropsWithChildren<DropdownMenuProps>> = (args) => <DropdownMenu {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: (
    <ul>
      <li>
        <button type="button">검색</button>
      </li>
      <li>
        <button type="button">글쓰기</button>
      </li>
      <li>
        <button type="button">마이페이지</button>
      </li>
    </ul>
  ),
  css: css``,
};
