import { Link, useParams } from 'react-router-dom';
import { AbilityHistory, List } from './AbilityHistoryList.styles';

type ListType = {
  id: number;
  title: string;
};

interface Props {
  list: ListType[];
}

const AbilityHistoryList = ({ list }: Props) => {
  const { username } = useParams();

  return (
    <List>
      {list.map((abilityHistory, index) => (
        <AbilityHistory key={abilityHistory.id}>
          <Link to={`/${username}/ability-history/${abilityHistory.id}`}>
            {abilityHistory.title} {index === 0 ? '(most recent)' : ''}
          </Link>
        </AbilityHistory>
      ))}
    </List>
  );
};

export default AbilityHistoryList;
