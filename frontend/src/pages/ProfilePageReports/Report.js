import { Wrapper } from './Report.styles';
import AbilityGraph from './AbilityGraph';
import ReportStudylogTable from './ReportStudylogTable';

const Report = ({ report = {} }) => {
  return (
    <Wrapper>
      <h2>{report?.title}</h2>
      <p>{report?.description}</p>

      {!!report?.abilityGraph?.abilities && (
        <AbilityGraph abilities={report.abilityGraph.abilities} mode="VIEW" />
      )}

      <ReportStudylogTable Studylogs={report?.studylogs ?? []} />
    </Wrapper>
  );
};

export default Report;
