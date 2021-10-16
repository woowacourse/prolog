import { Wrapper } from './Report.styles';
import AbilityGraph from './AbilityGraph';
import ReportStudyLogTable from './ReportStudyLogTable';

const Report = ({ report = {} }) => {
  return (
    <Wrapper>
      <h2>{report?.title}</h2>
      <p>{report?.description}</p>

      {!!report?.abilityGraph?.abilities && (
        <AbilityGraph abilities={report.abilityGraph.abilities} mode="VIEW" />
      )}

      <ReportStudyLogTable studyLogs={report?.studylogs ?? []} />
    </Wrapper>
  );
};

export default Report;
