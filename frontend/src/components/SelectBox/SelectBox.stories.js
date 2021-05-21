import SelectBox from './SelectBox';

export default {
  title: 'Component/SelectBox',
  component: SelectBox,
};

const Template = (args) => <SelectBox {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  options: ['디토', '티케', '엘라', '서니', '브라운', '포코', '포모', '피케이', '소롱', '웨지'],
};
