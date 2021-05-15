import Card, { SIZE } from './Card';

export default {
  title: 'Component/Card',
  component: Card,
  argTypes: {
    size: { options: Object.values(SIZE), control: { type: 'select' } },
  },
};

const Template = (args) => <Card {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <div>hello</div>,
};

export const Large = Template.bind({});

Large.args = {
  children: <div>hello</div>,
  size: 'LARGE',
};
