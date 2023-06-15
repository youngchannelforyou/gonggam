import React, { useEffect, useState } from 'react';
import { format, startOfMonth, endOfMonth, startOfWeek, endOfWeek, addDays, subMonths, addMonths } from 'date-fns';
import { css, cx } from '@emotion/css';
import Loading from '../../Loading/Loading';

const Calendar = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [currentMonth, setCurrentMonth] = useState(new Date());
    const [selectedDate, setSelectedDate] = useState(new Date());


    useEffect(() => {
        if (currentMonth === null)
            return;

        setTimeout(() => setIsLoading(false), 1000);

    }, [currentMonth]);

    function MakeCalcHeader() {
        return (
            <div className={calcTheadWrapper}>
                <div className={calcTheadTitle}>일</div>
                <div className={calcTheadTitle}>월</div>
                <div className={calcTheadTitle}>화</div>
                <div className={calcTheadTitle}>수</div>
                <div className={calcTheadTitle}>목</div>
                <div className={calcTheadTitle}>금</div>
                <div className={calcTheadTitle}>토</div>
            </div >
        );
    }

    const MakeCalcBody = ({ currentMonth, selectedDate, onDateClick }) => {
        const monthStart = startOfMonth(currentMonth);
        const monthEnd = endOfMonth(monthStart);
        const startDate = startOfWeek(monthStart);
        const endDate = endOfWeek(monthEnd);

        const rows = [];
        let days = [];
        let day = startDate;
        let formattedDate = '';

        while (day <= endDate) {
            for (let i = 0; i < 7; i++) {
                formattedDate = format(day, 'd');
                console.log(formattedDate);
                const cloneDay = day;
                days.push(
                    <div className={cx(dateCommon, format(currentMonth, 'M') !== format(day, 'M') ? notThisMonth : thisMonth)} key={day} onClick={() => onDateClick(cloneDay)}>
                        <div className={paddingLeft}>
                            {Number(formattedDate) < 10 ? `0${formattedDate}` : `${formattedDate}`}
                        </div>
                        <div className={amountTextBox}>
                            <div className={cx(displayFlex, css`color: #318D60;`)}>
                                <span className={amountWrapper}>+0</span>
                                <span className={dollarIcon}>₩</span>
                            </div>
                            <div className={cx(displayFlex, css`color: #AD2F2F;`)}>
                                <span className={amountWrapper}>-100,235,320</span>
                                <span className={dollarIcon}>₩</span>
                            </div>
                        </div>
                    </div>
                );
                day = addDays(day, 1);
            }
            rows.push(
                <div className={calcBodyRow} key={day}>
                    {days}
                </div>,
            );
            days = [];
        }
        return rows;
    };

    return (
        <div className={container}>
            {!isLoading ?
                (
                    <>
                        <div className={calcTitleWrapper}>
                            <div className={calcTitleLeft}>
                                <button className={calcTitleItems} onClick={() => { setCurrentMonth(subMonths(currentMonth, 1)) }}> &lt; </button>
                                <div className={calcTitleItems}>
                                    {format(currentMonth, 'M')}월
                                    {/* {format(currentMonth, 'yyyy')} */}
                                </div>
                                <button className={calcTitleItems} onClick={() => { setCurrentMonth(addMonths(currentMonth, 1)) }}> &gt; </button>
                            </div>
                            <div className={calcTitleRight}>
                                <button className={calcRightSideButton}>
                                    <p>T</p>
                                </button>
                                <button className={cx(calcRightSideButton, addButton)}>
                                    <p>
                                        +
                                    </p>
                                </button>
                            </div>
                        </div>
                        <div className={clacTableWrapper}>
                            <MakeCalcHeader />
                            <div className={calcTableBody}>
                                <MakeCalcBody currentMonth={currentMonth} selectedDate={selectedDate} onDateClick={setSelectedDate} />
                            </div>
                        </div>
                    </>
                ) : (
                    <div className={positionRelative}>
                        <Loading />
                    </div>
                )}
        </div>
    );
}

export default Calendar;

const container = css`
    width: 787px;

    margin: 0 auto;
    height: 100%;
    padding: 16px 20px 22px 24px;
    color: #fff;
`;

const calcTitleWrapper = css`
    display: flex;
    justify-content: space-between;
    align-items: center;

    font-size: 18px;
`;

const calcTitleLeft = css`
    display: flex;
    text-align: center;
`

const calcTitleRight = css`
    display: flex;
    gap: 11px;

`

const calcRightSideButton = css`
    width: 32px;
    height: 32px;
    border: 1px solid #252729;
    border-radius: 30px;

    p {
        width: 100%;
        height: 100%;
        line-height: 30px;
    }
`;

const addButton = css`
    p {
        position: relative;
        top: -2px;
    }
`;

const calcTitleItems = css`
    height: 18px;
    width: 35px;
    line-height: 18px;
`;

const clacTableWrapper = css`
    width: 100%;
    height: 100%;
    margin-top: 18px;
`;

const calcTheadWrapper = css`
    width: 100%;
    display: flex;
    gap: 9px;

    color: #c6c6c6;
    font-size: 13px;
`;

const calcTheadTitle = css`
    width: 100px;
    height: 28px;
    line-height: 28px;
    border: 1px solid #252729;
    border-radius: 15px;
    background-color: #191B1E;

    flex-grow: 1;
    flex-basis: 0;
    
    text-align: center;
`;

const calcTableBody = css`
    display: flex;
    flex-direction: column;
    gap: 6px;

    margin-top: 15px;
`;

const calcBodyRow = css`
    display: flex;
    gap: 6px;

    font-size: 14px;
`;

const dateCommon = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 101px;
    height: 101px;
    padding: 10px 8px 21px 0px;
    border: 1px solid #252729;
    border-radius: 20px;

    flex: 1 0 0px;
    color: #87888A;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

const paddingLeft = css`
    padding-left: 14px;
`;

const thisMonth = css`
    background-color: #191B1E;
`;

const notThisMonth = css`
    background-color: #000;
`;

const amountTextBox = css`
    width: 100%;
    padding-left: 5px;
    font-size: 12px;
    line-height: 15px;
    text-align: right;
`;

const displayFlex = css`
    display: flex;
    gap: 2px;
`;

const amountWrapper = css`
    width: 75px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space:nowrap;
`;

const dollarIcon = css`
    width: 11px;
`;

const positionRelative = css`
    width: 100%;
    height: 100%;
    position: relative;
`;