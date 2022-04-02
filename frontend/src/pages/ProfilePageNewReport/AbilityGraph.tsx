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

const AbilityGraph = ({ abilities, setAbilities }) => {
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

  const onUpdateWeight = (event, targetId) => {
    setAbilities((prev) =>
      prev.map((ability) => {
        if (ability.id === targetId) {
          return { ...ability, weight: event.target.value };
        }

        return ability;
      })
    );
  };

  if (abilities.length < 3)
    return (
      <div style={{ margin: '3rem auto', fontSize: '1.4rem', color: `${COLOR.RED_700}` }}>
        그래프를 그리기 위해서는 2개이상의 역량을 등록해야합니다.
      </div>
    );

  return (
    <Styled.AbilityGraphContainer>
      <Styled.Title>📊 역량그래프 설정</Styled.Title>

      <div>
        <div id="ability-graph-wrapper">
          <Radar data={graphData} />
        </div>

        <table>
          <Styled.Thead>
            <Styled.TableRow>
              <th scope="col">역량</th>
              <th scope="col">가중치</th>
            </Styled.TableRow>
          </Styled.Thead>

          <Styled.Tbody>
            {abilities?.map((ability) => (
              <Styled.TableRow key={ability.id}>
                <Styled.AbilityName abilityColor={ability.color}>
                  <div>
                    <span>{ability.name}</span>
                  </div>
                </Styled.AbilityName>

                <Styled.AbilityWeight>
                  <input
                    type="number"
                    required
                    min={0}
                    max={20}
                    defaultValue={ability.weight}
                    onChange={(event) => onUpdateWeight(event, ability.id)}
                  />
                </Styled.AbilityWeight>
              </Styled.TableRow>
            ))}
          </Styled.Tbody>
        </table>
      </div>
    </Styled.AbilityGraphContainer>
  );
};

export default AbilityGraph;
