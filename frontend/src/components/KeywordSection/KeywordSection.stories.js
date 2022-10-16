import KeywordSection from './KeywordSection';
import SomeImage from '../../assets/images/background-image.png';

export default {
  title: 'Component/KeywordSection',
  component: KeywordSection,
  argTypes: {},
};

const Template = (args) => <KeywordSection {...args} />;

export const Selected = Template.bind({});

Selected.args = {
  src: SomeImage,
  alt: '이미지',
  text: 'JavaScript',
  isSelected: true,
};
