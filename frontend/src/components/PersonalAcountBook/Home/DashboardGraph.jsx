import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';
import NumberDisplay from '../Etc/NumberDisplay'

function DashboardGraph({ tableInfo }) {
    const [values, setValues] = useState([
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    ]);

    const [yAxisDegree, setYAxisDegree] = useState({
        maxNum: 800,
        minNum: 400,
        range: 100,
    });

    useEffect(() => {
        if (!tableInfo)
            return;
        setValues(tableInfo.costList);
        calcYAxisDegree();
    }, [tableInfo]);

    function calcYAxisDegree() {
        const maxNum = tableInfo.maxCost * 1.1
        const minNum = tableInfo.minCost * 0.9
        const range = (maxNum - minNum) / 5;

        setYAxisDegree({
            maxNum: maxNum,
            minNum: minNum,
            range: range,
        })
    }

    function makePercentToValue(value) {
        return (
            ((value / (yAxisDegree.range * 5)) - (yAxisDegree.minNum / (yAxisDegree.range * 5))) * 100
        );
    }

    return (
        <div className={container}>
            <div className={barsWrapper}>
                {values.map((value, idx) => {
                    return (
                        <div key={idx} className={barWrapper}>
                            <div className={barFrame}>
                                <div className={fillBar(makePercentToValue(value))}></div>
                            </div>
                            {idx % 3 === 0 &&
                                <div className={indexOfBar}>
                                    {idx}
                                </div>
                            }
                        </div>
                    );
                })}
            </div>
            <div className={unitsWrapper}>
                <div><NumberDisplay number={parseInt(yAxisDegree.maxNum)} /></div>
                <div><NumberDisplay number={parseInt(yAxisDegree.maxNum - yAxisDegree.range)} /></div>
                <div><NumberDisplay number={parseInt(yAxisDegree.maxNum - yAxisDegree.range * 2)} /></div>
                <div><NumberDisplay number={parseInt(yAxisDegree.maxNum - yAxisDegree.range * 3)} /></div>
                <div><NumberDisplay number={parseInt(yAxisDegree.minNum)} /></div>
            </div>
        </div >
    );
}

const container = css`
    display: flex;
    padding-top: 32px;
`;

const barsWrapper = css`
    display: flex;
    gap: 8px;  
`;

const barWrapper = css`
    position: relative;
`;

const barFrame = css`
    position: relative;
    width: 10px;
    height: 158px;
    background-color: #242B29;
    border-radius: 10px;
`;

const fillBar = (percent) => css`
    position: absolute;
    bottom: 0px;
    width: 10px;
    height: ${percent}%;
    border-radius: 10px;
    background-color: #30D979;
    transition: 1s;
`;

const indexOfBar = css`
    position: absolute;
    top: 170px;
    left: 50%;

    transform: translate(-50%, -50%);

    font-size: 14px;
    color: #828486;
`;

const unitsWrapper = css`
    position: relative;
    top: -6px;
    display: flex;
    flex-direction: column;
    height: 170px;
    gap: 27px;
    margin-left: 18px;
    font-size: 12px;
    color: #828486;
`;

export default DashboardGraph;