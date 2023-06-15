import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

function DashboardGraph({ tableInfo }) {
    const [values, setValues] = useState([
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    ]);

    useEffect(() => {
        if (!tableInfo)
            return;
        console.log('hello');
        console.log(tableInfo.costList);
        // setValues(tableInfo.costList);
    }, [tableInfo]);

    return (
        <div className={container}>
            <div className={barsWrapper}>
                {values.map((value, idx) => {
                    return (
                        <div key={idx} className={barWrapper}>
                            <div className={barFrame}>
                                <div className={fillBar(value)}></div>
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
                <div>800k</div>
                <div>700k</div>
                <div>600k</div>
                <div>500k</div>
                <div>400k</div>
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