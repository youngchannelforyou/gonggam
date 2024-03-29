import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

/*
    type: 0 = 수입
    type: 1 = 지출
*/

function WidthBar({ title, totalAmount, amount, type }) {
    const colorSet = ['#30D979', '#F1C91D'];
    const frameColorSet = ['#2E584B', '#6F581A'];
    const selectedItemColorSet = ['#0CC177', '#EDB11B'];
    const [selectedArr, setSelectedArr] = useState([
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0
    ]);

    useEffect(() => {
        setSelectedArr(arrLoop());
    }, [amount]);

    function arrLoop() {
        const arr = [];
        const percent = amount / (totalAmount);
        const barPercent = Math.round(percent * 17);
        for (let i = 0; i < barPercent; i++) {
            arr.push(1);
        }
        for (let i = barPercent; i < 17; i++) {
            arr.push(0);
        }

        return arr;
    };

    return (
        <div>
            <div className={displayFlexBetw}>
                <div className={titleWrapper}>{title}</div>
                <div className={amountWrapper(colorSet[type])}>{!type ? '+' : '-'}{new Intl.NumberFormat().format(amount)}</div>
            </div>
            <div className={widthBarWrapper}>
                {selectedArr.map((isSelected, index) => {
                    const tmpKey = index + 1;
                    if (isSelected)
                        return (<div className={widthBarItem(selectedItemColorSet[type])} key={tmpKey}></div>)
                    return (<div className={widthBarItem(frameColorSet[type])} key={tmpKey}></div>)
                })}
            </div>
        </div>
    );
}

export default WidthBar;

const displayFlexBetw = css`
    display: flex;
    justify-content: space-between;
`;

const titleWrapper = css`
    color: #fff;
    font-size: 13px;
    font-weight: 700;
    margin-bottom: 1px;
`;

const amountWrapper = (typeColor) => css`
    color: ${typeColor};
    font-size: 12px;
`;

const widthBarWrapper = css`
    display: flex;
    gap: 3px;

    margin-top: 8px;
    border-radius: 13px;
    overflow: hidden;
`;

const widthBarItem = (typeColor) => css`
    width: 18px;
    height: 13px;  
    background-color: ${typeColor};
    transition: 1s;
`;