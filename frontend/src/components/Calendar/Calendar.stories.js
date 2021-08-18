import Calendar from './Calendar';

export default {
  title: 'Component/Calendar',
  component: Calendar,
};

const Template = (args) => <Calendar {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  titleData: [
    {
      id: 0,
      title: '타이틀 0',
      createdAt: '2021-08-01T12:00:00',
      updatedAt: '2021-08-01T12:00:00',
    },
    {
      id: 11,
      title: '타이틀 11',
      createdAt: '2021-08-01T13:00:00',
      updatedAt: '2021-08-01T13:00:00',
    },
    {
      id: 12,
      title: '타이틀 12',
      createdAt: '2021-08-01T14:00:00',
      updatedAt: '2021-08-01T14:00:00',
    },
    {
      id: 1,
      title: '타이틀 1',
      createdAt: '2021-08-02T12:00:00',
      updatedAt: '2021-08-02T12:00:00',
    },
    {
      id: 13,
      title: '타이틀 13',
      createdAt: '2021-08-02T12:00:00',
      updatedAt: '2021-08-02T12:00:00',
    },
    {
      id: 2,
      title: '타이틀 2',
      createdAt: '2021-08-03T12:00:00',
      updatedAt: '2021-08-03T12:00:00',
    },
    {
      id: 3,
      title: '타이틀 3',
      createdAt: '2021-08-04T12:00:00',
      updatedAt: '2021-08-04T12:00:00',
    },
    {
      id: 4,
      title: '타이틀 4',
      createdAt: '2021-08-05T12:00:00',
      updatedAt: '2021-08-05T12:00:00',
    },
    {
      id: 5,
      title: '타이틀 5',
      createdAt: '2021-08-06T12:00:00',
      updatedAt: '2021-08-06T12:00:00',
    },
    {
      id: 6,
      title: '타이틀 6',
      createdAt: '2021-08-07T12:00:00',
      updatedAt: '2021-08-07T12:00:00',
    },
    {
      id: 7,
      title: '타이틀 7',
      createdAt: '2021-08-08T12:00:00',
      updatedAt: '2021-08-08T12:00:00',
    },
    {
      id: 8,
      title: '타이틀 8',
      createdAt: '2021-08-09T12:00:00',
      updatedAt: '2021-08-09T12:00:00',
    },
    {
      id: 9,
      title: '타이틀 9',
      createdAt: '2021-08-10T12:00:00',
      updatedAt: '2021-08-10T12:00:00',
    },
    {
      id: 15,
      title: '타이틀 15',
      createdAt: '2021-08-10T12:00:00',
      updatedAt: '2021-08-10T12:00:00',
    },
    {
      id: 16,
      title: '타이틀 16',
      createdAt: '2021-08-10T12:00:00',
      updatedAt: '2021-08-10T12:00:00',
    },
    {
      id: 17,
      title: '타이틀 17',
      createdAt: '2021-08-10T12:00:00',
      updatedAt: '2021-08-10T12:00:00',
    },
    {
      id: 18,
      title: '타이틀 18',
      createdAt: '2021-08-31T12:00:00',
      updatedAt: '2021-08-31T12:00:00',
    },
  ],
};
