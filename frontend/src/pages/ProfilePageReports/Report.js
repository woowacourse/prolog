import React from 'react';

import { Wrapper } from './Report.styles';
import ReportStudyLogTable from './ReportStudyLogTable';

const Report = ({ report = {} }) => {
  return (
    <Wrapper>
      <h2>{report?.title}</h2>
      <p>{report?.description}</p>

      <section
        style={{
          height: '25rem',
          border: '1px solid black',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          marginBottom: '4rem',
        }}
      >
        준비중인 기능입니다.
      </section>

      <ReportStudyLogTable studyLogs={report?.studylogs ?? []} />
    </Wrapper>
  );
};

export default Report;
