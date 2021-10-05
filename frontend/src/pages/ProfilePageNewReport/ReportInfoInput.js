import React from 'react';
import { Desc, Label, Title } from './ReportInfoInput.styles';

const ReportInfoInput = ({ nickname, title, setTitle, desc, setDescription }) => {
  const onWriteTitle = ({ target: { value } }) => setTitle(value);
  const onWriteDesc = ({ target: { value } }) => setDescription(value);

  return (
    <section>
      <Label htmlFor="report_title">
        ✏️ Title
        <span>{title.length}/50</span>
        <Title
          id="report_title"
          placeholder={`${new Date().toLocaleDateString()} ${nickname}의 리포트`}
          value={title}
          onChange={onWriteTitle}
          maxLength={50}
        />
      </Label>

      <Label htmlFor="report_desc">
        ✏️ Description
        <span>{desc.length}/150</span>
        <Desc
          id="report_desc"
          placeholder="리포트에 대해서 간단히 소개해주세요."
          value={desc}
          onChange={onWriteDesc}
          maxLength={150}
        />
      </Label>
    </section>
  );
};

export default ReportInfoInput;
