import { Radar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from 'chart.js';

import * as Styled from './AbilityGraph.styles';
import { COLOR } from '../../constants';

const AbilityGraph = ({ abilities }) => {
  ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);
  const graphData = {
    labels: abilities.map((ability) => ability.name),
    datasets: [
      {
        label: '역량 가중치',
        data: abilities.map((ability) => ability.weight),
        backgroundColor: `${COLOR.LIGHT_BLUE_100}BB`,
        borderColor: `${COLOR.LIGHT_BLUE_400}`,
        borderWidth: 1.5,
      },
    ],
  };

  return (
    <Styled.AbilityGraphContainer>
      <Styled.Title>📊 역량그래프</Styled.Title>

      <div>
        <div id="ability-graph-wrapper">
          <Radar data={graphData} />
        </div>
      </div>
    </Styled.AbilityGraphContainer>
  );
};

export default AbilityGraph;
