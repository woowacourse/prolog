import ProfileChip from './ProfileChip';

export default {
  title: 'Component/ProfileChip',
  component: ProfileChip,
  argTypes: { children: { control: 'text' } },
};

const Template = (args) => <ProfileChip {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  imageSrc: 'https://img6.yna.co.kr/photo/cms/2019/05/02/02/PCM20190502000402370_P4.jpg',
  children: '엘라',
};

export const Long = Template.bind({});

Long.args = {
  imageSrc: 'https://img6.yna.co.kr/photo/cms/2019/05/02/02/PCM20190502000402370_P4.jpg',
  children: 'Sunnnnnnnnnnnnny',
};

export const NoProfile = Template.bind({});

NoProfile.args = {
  children: 'Sunnnnnnnnnnnnny',
};
