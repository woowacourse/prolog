import TopKeywordList from './TopKeywordList';
import SomeImage from '../../assets/images/background-image.png';

export default {
  title: 'Component/TopKeywordList',
  component: TopKeywordList,
  argTypes: {},
};

const Template = (args) => <TopKeywordList {...args} />;

export const Selected = Template.bind({});

Selected.args = {
  src: SomeImage,
  alt: '이미지',
  text: 'JavaScript',
  isSelected: true,
};
