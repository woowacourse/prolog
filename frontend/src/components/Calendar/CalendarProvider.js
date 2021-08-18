import React, { createContext, useState } from 'react';

export const CalendarContext = createContext(null);

const CalendarProvider = ({ children }) => {
  const [selectedDate, setSelectedDate] = useState('');

  const setDate = (year, month, day) =>
    setSelectedDate(`${year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}`);

  return (
    <CalendarContext.Provider value={{ selectedDate, setSelectedDate: setDate }}>
      {children}
    </CalendarContext.Provider>
  );
};

export default CalendarProvider;
