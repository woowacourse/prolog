import moment from 'moment';
import { DatePicker } from 'antd';

import * as Styled from './ReportInfo.styles';

const ReportInfo = ({
  nickname, //
  title,
  setTitle,
  desc,
  setDescription,
  setStartDate,
  setEndDate,
  edit,
}) => {
  const onWriteTitle = ({ target: { value } }) => setTitle(value);
  const onWriteDesc = ({ target: { value } }) => setDescription(value);
  const onSelectDate = (_, dateStrings) => {
    const [startDate, endDate] = dateStrings;
    setStartDate(startDate);
    setEndDate(endDate);
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
            defaultValue={[moment('2019-09-03', dateFormat), moment('2019-11-22', dateFormat)]}
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
        <span>{desc.length}/2000</span>
        <Styled.Desc
          id="report_desc"
          placeholder="리포트에 대해서 소개해주세요."
          value={desc}
          onChange={onWriteDesc}
          maxLength={2000}
        />
      </Styled.Label>
    </section>
  );
};

export default ReportInfo;
