import React from 'react';
import { Desc, Section, Title } from './ReportInfoInput.styles';

const ReportInfoInput = ({ nickname = '', title, onWriteTitle, desc, onWriteDesc }) => {
  return (
    <Section>
      <label htmlFor="report_title">✏️ Title</label>
      <Title
        id="report_title"
        placeholder={`${new Date().toLocaleDateString()} ${nickname}의 리포트`}
        value={title}
        onChange={onWriteTitle}
      />

      <label htmlFor="report_desc">✏️ Description</label>
      <Desc
        id="report_desc"
        placeholder="리포트에 대해서 간단히 소개해주세요."
        value={desc}
        onChange={onWriteDesc}
      />
    </Section>
  );
};

export default ReportInfoInput;
