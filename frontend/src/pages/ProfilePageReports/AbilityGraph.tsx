import DonutChart from '../../components/Charts/DonutChart';
import DonutChartForm from '../../components/Charts/DonutChartForm';
import { COLOR } from '../../constants';
import { Content, Section } from './AbilityGraph.styles';

export type Ability = {
  id: number;
  name: string;
  weight: number;
  percentage: number;
  color: string;
  isPresent: boolean;
};

const MODE = {
  VIEW: 'VIEW',
  EDIT: 'EDIT',
  NEW: 'NEW',
};

const AbilityGraph = ({
  abilities,
  setAbilities,
  mode,
}: {
  abilities: Ability[];
  setAbilities?: (
    data: {
      id: number;
      name: string;
      weight: number;
      percentage: number;
      color: string;
      isPresent: boolean;
    }[]
  ) => void;
  mode: string;
}) => {
  return (
    <Section>
      <h3>📊 역량 그래프</h3>
      <Content>
        {mode === MODE.VIEW && (
          <DonutChart
            chartData={{
              title: '역량 그래프',
              categoryTitle: '역량',
              data: abilities.filter((item) => item.isPresent),
            }}
            config={{ backgroundColor: COLOR.WHITE, width: 400, height: 220 }}
          />
        )}
        {(mode === MODE.NEW || mode === MODE.EDIT) && (
          <DonutChartForm
            chartData={{
              title: '역량 그래프',
              categoryTitle: '역량',
              data: abilities,
            }}
            config={{ backgroundColor: COLOR.WHITE, width: 400, height: 220 }}
            onChangeData={
              setAbilities as (
                data: {
                  id: number;
                  name: string;
                  weight: number;
                  percentage: number;
                  color: string;
                  isPresent: boolean;
                }[]
              ) => void
            }
          />
        )}
      </Content>
    </Section>
  );
};

export default AbilityGraph;
