import moment from 'moment';
import { DatePicker } from 'antd';

import * as Styled from './ReportInfo.styles';
import ReportDescEditor from './ReportDescEditor';
import { Editor } from '@toast-ui/react-editor';
import { Dispatch, SetStateAction } from 'react';

interface ReportInfoPros {
  nickname: string;
  title: string;
  desc: Editor | string;
  editorRef: Dispatch<SetStateAction<Editor | string>>;
  startDate?: string;
  setStartDate?: Dispatch<SetStateAction<string>>;
  setTitle: Dispatch<SetStateAction<string>>;
  endDate?: string;
  setEndDate?: Dispatch<SetStateAction<string>>;
  edit;
}

const ReportInfo = ({
  nickname, //
  title,
  setTitle,
  desc,
  editorRef,
  startDate,
  setStartDate,
  endDate,
  setEndDate,
  edit,
}: ReportInfoPros) => {
  const onWriteTitle = ({ target: { value } }) => setTitle(value);
  const onSelectDate = (_, dateStrings) => {
    const [startDate, endDate] = dateStrings;
    if (setStartDate) setStartDate(startDate);
    if (setEndDate) setEndDate(endDate);
  };

  const dateFormat = 'YYYY-MM-DD';

  return (
    <section>
      <Styled.DateContainer>
        <span>✏️ 기간 선택</span>
        {edit ? (
          <DatePicker.RangePicker
            size="large"
            ranges={{
              Today: [moment(), moment()],
              'This Month': [moment().startOf('month'), moment().endOf('month')],
            }}
            format={dateFormat}
            onChange={onSelectDate}
            defaultValue={[
              moment(startDate ?? '2022-02-01', dateFormat),
              moment(endDate ?? '2022-02-01', dateFormat),
            ]}
            disabled
          />
        ) : (
          <DatePicker.RangePicker
            size="large"
            ranges={{
              Today: [moment(), moment()],
              'This Month': [moment().startOf('month'), moment().endOf('month')],
            }}
            format={dateFormat}
            onChange={onSelectDate}
          />
        )}
      </Styled.DateContainer>

      <Styled.Label htmlFor="report_title">
        ✏️ 리포트 제목
        <span>{title.length}/30</span>
        <Styled.Title
          id="report_title"
          placeholder={`${nickname}의 리포트`}
          value={title}
          onChange={onWriteTitle}
          maxLength={30}
          required
        />
      </Styled.Label>

      <Styled.Label htmlFor="report_desc">
        ✏️ 리포트 설명
        <span>{desc instanceof String ? desc.length : null}/300</span>
        <ReportDescEditor initialContent={desc ?? ''} editorRef={editorRef} />
      </Styled.Label>
    </section>
  );
};

export default ReportInfo;
