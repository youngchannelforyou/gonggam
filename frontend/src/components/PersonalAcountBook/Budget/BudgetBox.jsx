import React from 'react';
import { css } from '@emotion/css';

function BudgeBox({ spendTagList, title, amount, type }) {
    const colorSet = [
        'rgba(227, 226, 224, 0.5)',
        'rgb(227, 226, 224)',
        'rgb(238, 224, 218)',
        'rgb(250, 222, 201)',
        'rgb(253, 236, 200)',
        'rgb(219, 237, 219)',
        'rgb(232, 222, 238)',
        'rgb(211, 229, 239)',
        'rgb(245, 224, 233)',
        'rgb(255, 226, 221)',
    ];

    return (
        <div className={tagContainer}>
            <div className={titleBox}>
                <div className={titleWrapper}>{title}</div>
                <button className={addButton}>+</button>
            </div>
            <div className={tagBox}>
                {spendTagList.map((tag, index) => {
                    const tmpKey = index + 1;
                    const randomNumber = Math.floor(Math.random() * 10);
                    const color = colorSet[randomNumber];

                    return <div className={tagItem(color)} key={tmpKey}>{tag}</div>
                }
                )}
            </div>
        </div>
    );
}

export default BudgeBox;


const tagContainer = css`
    width: 100%;
    height: fit-content;
    padding: 10px 22px 22px;
    background-color: #1C1E1F;
    border-radius: 20px;
`;

const titleBox = css`
    display: flex;
    justify-content: space-between;
    align-content: center;
`;

const titleWrapper = css`
    line-height: 32px;
    font-size: 17px;
    color: #9c9c9c;
`;

const addButton = css`
    width: 32px;
    height: 32px;

    color: #fff;
    font-size: 16px;
    line-height: 32px;
`;

const tagBox = css`
    display: flex;
    gap: 12px;

    margin-top: 18px;
`

const tagItem = (color) => css`
    width: fit-content;
    height: fit-content;
    padding: 6px;
    border-radius: 3px;
    background-color: ${color};
    
    color: rgb(68, 42, 30);
    font-size: 14px;
`;
