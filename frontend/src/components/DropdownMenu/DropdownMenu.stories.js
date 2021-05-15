import DropdwonMenu from './DropdownMenu';

export default {
  title: 'Component/DropdwonMenu',
  component: DropdwonMenu,
  argTypes: { children: { control: 'text' } },
};

const Template = (args) => <DropdwonMenu {...args} />;

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
  onLogoClick: () => {},
};
