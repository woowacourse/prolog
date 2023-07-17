import Article from './Article';

export default {
  title: 'Component/Article',
  component: Article,
};

const Template = (args) => <Article {...args} />;

export const Basic = Template.bind({});

const mockArticle = {
  id: 1,
  userName: 'string',
  title: 'JS Prototype',
  url: 'https://poiemaweb.com/js-prototype',
  createdAt: '2023-07-10',
};

Basic.args = { ...mockArticle };
