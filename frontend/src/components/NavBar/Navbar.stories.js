import NavBar from './NavBar';

export default {
  title: 'Component/NavBar',
  component: NavBar,
  argTypes: { children: { control: 'text' } },
};

const Template = (args) => <NavBar {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: (
    <>
      <button type="button">검색</button>
      <button type="button">글쓰기</button>
      <button type="button">마이페이지</button>
    </>
  ),
  onLogoClick: () => {},
};
