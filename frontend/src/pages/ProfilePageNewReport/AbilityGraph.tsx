import Chart from 'react-apexcharts';

import * as Styled from './AbilityGraph.styles';
import { COLOR } from '../../constants';
import { ApexOptions } from 'apexcharts';

interface Props {
  type?:
    | 'line'
    | 'area'
    | 'bar'
    | 'histogram'
    | 'pie'
    | 'donut'
    | 'radialBar'
    | 'scatter'
    | 'bubble'
    | 'heatmap'
    | 'treemap'
    | 'boxPlot'
    | 'candlestick'
    | 'radar'
    | 'polarArea'
    | 'rangeBar';
  series?: ApexOptions['series'];
  width?: string | number;
  options?: ApexOptions;
  [key: string]: any;
}

const AbilityGraph = ({ abilities, setAbilities, edit }) => {
  const options: Props = {
    series: [
      {
        name: '역량 가중치',
        data: abilities.map((ability) => ability.weight),
      },
    ],
    chart: {
      type: 'radar',
    },
    dataLabels: {
      enabled: true,
    },
    plotOptions: {
      radar: {
        size: 120,
        polygons: {
          strokeColors: '#e9e9e9',
          fill: {
            colors: ['#f8f8f8', '#fff'],
          },
        },
      },
    },
    colors: [`${COLOR.DARK_BLUE_500}`],
    markers: {
      size: 4,
      colors: [`${COLOR.WHITE}`],
      strokeColor: `${COLOR.DARK_BLUE_500}`,
      strokeWidth: 2,
    },
    tooltip: {
      y: {
        formatter: function (val) {
          return val;
        },
      },
    },
    xaxis: {
      categories: abilities.map((ability) => ability.name),
      labels: {
        style: {
          colors: Array.from({ length: abilities.length }).fill(`${COLOR.BLACK_800}`),
          fontSize: '12px',
          fontWeight: 400,
        },
      },
    },
    yaxis: {
      tickAmount: 7,
      labels: {
        formatter: function (item, i) {
          if (i % 2 === 0) {
            return Math.floor(item);
          } else {
            return '';
          }
        },
      },
    },
  };

  const onUpdateWeight = (event, targetId) => {
    setAbilities((prev) =>
      prev.map((ability) => {
        if (ability.id === targetId) {
          return { ...ability, weight: Number(event.target.value) };
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
        <Chart options={options} type={options.chart.type} series={options.series} width="750" />

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
                    disabled={edit}
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
