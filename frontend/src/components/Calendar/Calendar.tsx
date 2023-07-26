import React, { useEffect, useState } from 'react';
import {
    CalendarWrapper,
    Year,
    Month,
    Week,
    Day,
    Container,
    Header,
    WeekWrapper,
    DayWrapper,
    ListItem,
    TitleList,
    MoreTitle,
} from './Calendar.styles';
import { ReactComponent as ArrowIcon } from '../../assets/images/right-arrow-angle.svg';
import { requestGetCalendar } from '../../service/requests';
import { useParams } from 'react-router';

const getStartDayOfMonth = (year, month) => {
    return new Date(year, month, 1).getDay();
};

const isLeapYear = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
};

const DAYS = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const DAYS_LEAP = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const WEEK = ['일', '월', '화', '수', '목', '금', '토'];
const MONTHS = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

const Calendar = ({ newDate, onClick = () => {}, selectedDay = -1, setSelectedDay = () => {} }) => {
    const today = newDate ? new Date(newDate.year, newDate.month - 1, newDate.day) : new Date();

    const [date, setDate] = useState(today);
    const [currentMonth, setCurrentMonth] = useState(date.getMonth());
    const [currentYear, setCurrentYear] = useState(date.getFullYear());
    const [startDay, setStartDay] = useState(getStartDayOfMonth(currentYear, currentMonth));
    const [titleList, setTitleList] = useState([]);
    const [titleListIndex, setTitleListIndex] = useState(null);

    const { username } = useParams();

    const days = isLeapYear(currentYear) ? DAYS_LEAP : DAYS;

    useEffect(() => {
        const getCalendar = async () => {
            try {
                const response = await requestGetCalendar(currentYear, currentMonth + 1, username);

                const { data: titleData } = await response;

                const data = [];

                [...Array(days[currentMonth])].forEach((_, index) => {
                    const day = index + 1;

                    const firstIndex = titleData.findIndex(
                        ({ createdAt }) => day === Number(createdAt.slice(8, 10))
                    );

                    const lastIndex = titleData.findIndex(
                        ({ createdAt }) => day < Number(createdAt.slice(8, 10))
                    );

                    data.push(
                        firstIndex !== -1
                            ? titleData.slice(
                                  firstIndex,
                                  lastIndex === -1 ? titleData.length : lastIndex
                              )
                            : []
                    );
                });

                setTitleList(data);
            } catch (error) {
                console.error(error);
            }
        };

        getCalendar();
    }, [currentYear, currentMonth, username, days]);

    useEffect(() => {
        const year = date.getFullYear();
        const month = date.getMonth();

        setCurrentMonth(month);
        setCurrentYear(year);
        setStartDay(getStartDayOfMonth(year, month));
    }, [date]);

    return (
        <Container>
            <Header>
                <button
                    type="button"
                    onClick={() => {
                        setDate(new Date(currentYear, currentMonth - 1, 1));
                        setSelectedDay(-1);
                    }}
                >
                    <ArrowIcon width="2rem" height="2rem" />
                </button>
                <div>
                    <Year>{currentYear}</Year>
                    <Month>{MONTHS[currentMonth]}</Month>
                </div>
                <button
                    type="button"
                    onClick={() => {
                        setDate(new Date(currentYear, currentMonth + 1, 1));
                        setSelectedDay(-1);
                    }}
                >
                    <ArrowIcon width="2rem" height="2rem" />
                </button>
            </Header>
            <CalendarWrapper>
                <WeekWrapper>
                    {WEEK.map((week) => (
                        <ListItem key={week}>
                            <Week isSunday={week === WEEK[0]} isSaturday={week === WEEK[6]}>
                                {week}
                            </Week>
                        </ListItem>
                    ))}
                </WeekWrapper>
                <DayWrapper>
                    {[...Array(days[currentMonth] + startDay)].map((_, index) => {
                        const day = index - (startDay - 1);
                        const isSunday = index % 7 === 0;
                        const isThursday = index % 7 === 4;
                        const isFriday = index % 7 === 5;
                        const isSaturday = index % 7 === 6;

                        const targetTitleList = titleList[day - 1] ?? [];

                        return (
                            <ListItem key={day}>
                                {index === titleListIndex && targetTitleList.length > 0 && (
                                    <TitleList isRight={isThursday || isFriday || isSaturday}>
                                        {targetTitleList.map(({ title, id }, index) => {
                                            if (index >= 3) return null;

                                            return <div key={id}>{title}</div>;
                                        })}
                                        {targetTitleList.length > 3 && (
                                            <MoreTitle>외 {targetTitleList.length - 3}개</MoreTitle>
                                        )}
                                    </TitleList>
                                )}
                                {day > 0 && (
                                    <Day
                                        isSunday={isSunday}
                                        isSaturday={isSaturday}
                                        isHover={index === titleListIndex}
                                        isSelected={selectedDay === day}
                                        onMouseEnter={() => setTitleListIndex(index)}
                                        onMouseLeave={() => setTitleListIndex(null)}
                                        onClick={() => {
                                            onClick(currentYear, currentMonth + 1, day);
                                            setSelectedDay(day);
                                        }}
                                        count={targetTitleList.length}
                                    >
                                        {day}
                                    </Day>
                                )}
                            </ListItem>
                        );
                    })}
                </DayWrapper>
            </CalendarWrapper>
        </Container>
    );
};

export default Calendar;
