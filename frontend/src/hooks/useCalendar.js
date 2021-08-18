import { useContext } from 'react';
import { CalendarContext } from '../components';

const useCalendar = () => {
  const data = useContext(CalendarContext);

  if (!data) {
    throw new Error('Calendar가 컨텍스트 범위에 존재하지 않습니다.');
  }

  return {
    selectedDate: data.selectedDate,
    setSelectedDate: data.setSelectedDate,
  };
};

export default useCalendar;
