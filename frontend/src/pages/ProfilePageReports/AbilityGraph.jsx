import Chart from 'react-apexcharts';

import * as Styled from './AbilityGraph.styles';
import { COLOR } from '../../constants';

const AbilityGraph = ({ abilities }) => {
  const options = {
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

  return (
    <Styled.AbilityGraphContainer>
      <Styled.Title>📊 역량그래프</Styled.Title>

      <div>
        <Chart options={options} type={options.chart.type} series={options.series} width="750" />
      </div>
    </Styled.AbilityGraphContainer>
  );
};

export default AbilityGraph;
