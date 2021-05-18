import Button, { SIZE } from './Button';
import backImage from '../../assets/images/background-image.png';
import search from '../../assets/images/search.svg';
import pen from '../../assets/images/pen.svg';
import person from '../../assets/images/person.svg';

export default {
  title: 'Component/Button',
  component: Button,
  argTypes: {
    size: { options: Object.values(SIZE), control: { type: 'select' } },
  },
};

const Template = (args) => <Button {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  size: 'X_SMALL',
  icon: search,
  css: {
    backgroundColor: '#ffffff',
  },
};

export const ProfileButton = Template.bind({});

ProfileButton.args = {
  size: 'X_SMALL',
  backgroundImageUrl: backImage,
};

export const SMALL = Template.bind({});

SMALL.args = {
  size: 'SMALL',
  icon: person,
  children: '로그인',
  css: {
    backgroundColor: '#ffffff',
  },
};

export const MEDIUM = Template.bind({});

MEDIUM.args = {
  size: 'MEDIUM',
  icon: pen,
  children: '글쓰기',
};

export const LARGE = Template.bind({});

LARGE.args = {
  size: 'LARGE',
  children: '작성완료',
  css: {
    backgroundColor: '#153147',
    color: '#ffffff',
  },
};
