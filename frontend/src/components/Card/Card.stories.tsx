import Card from './Card';
import { CARD_SIZE } from './Card.styles';

interface Props {
  children: JSX.Element;
  size: string;
}

export default {
  title: 'Component/Card',
  component: Card,
  args: {
    size: 'SMALL',
  },
  argTypes: {
    size: { options: Object.values(CARD_SIZE), control: { type: 'select' } },
  },
};

export const Default = (args: Props) => {
  return (
    <Card {...args}>
      <div>hello</div>
    </Card>
  );
};
