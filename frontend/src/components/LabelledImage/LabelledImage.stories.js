import LabelledImage from './LabelledImage';
import SomeImage from '../../assets/images/background-image.png';

export default {
  title: 'Component/LabelledImage',
  component: LabelledImage,
  argTypes: {},
};

const Template = (args) => <LabelledImage {...args} />;

export const Selected = Template.bind({});

Selected.args = {
  src: SomeImage,
  alt: '이미지',
  text: 'JavaScript',
  isSelected: true,
};

export const UnSelected = Template.bind({});

UnSelected.args = {
  src: SomeImage,
  alt: '이미지',
  text: 'JavaScript',
  isSelected: false,
};
