import React from 'react';
import { css } from '@emotion/css';


function RecentLogListItem({ item }) {
    const sign = item.Type === true ? '+' : '-';

    return (
        <div className={recentLogList}>
            <div className={spendItemBox}>
                <div className={itemDate}>{item.Date}</div>
                <div className={itemHead}>
                    <div className={itemTitle}>{item.Title}</div>
                    <div className={itemMoney(item.Type)}>
                        <span className={positionAdjust}>{sign}</span>
                        <span className={budgetOverflow}>{item.Used_Budget}</span>
                        <span className={dollarIcon}>₩</span>
                    </div>
                </div>
                <div className={itemContents}>{item.Text}</div>
            </div>
        </div>
    );
}

export default RecentLogListItem;



const recentLogList = css`
    width: 100%;
    height: 110px;
    background-color: #232526;
    border: 1px solid #232526;
    border-radius: 20px;
`;

const spendItemBox = css`
    padding: 12px 12px 14px;
`;

const itemDate = css`
    font-size: 12px;
    color: #9C9C9C;
`;

const itemHead = css`
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 20px;
    margin-top: 12px;
`;

const itemTitle = css`
    width: 140px;
    line-height: 20px;
    color: #D6D6D6;
    font-size: 13px;


    white-space:nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
`;

const itemMoney = (color) => css`
    display: flex;
    width: fit-content;
    max-width: 86px;
    height: 20px;
    padding: 0px 7px 0 5px;

    font-size: 12px;
    line-height: 20px;
    border-radius: 5px;
    text-align: right;
    ${color === true ? 'color: #318D60;' : 'color: #AD2F2F;'};
    ${color === true ? 'background-color: #172521;' : 'background-color: #2F1818;'};

`;

const positionAdjust = css`
    position: relative;
    top: -1px;
`;

const budgetOverflow = css`
    width: fit-content;
    max-width: 78px;

    white-space:nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
`;

const dollarIcon = css`
    width: 12px;
`;

const itemContents = css`
    display: -webkit-box;
    width: 100%;
    height: 30px;
    margin-top: 10px;

    color: #9c9c9c;
    font-size: 12px;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    line-height: 15px;
    word-wrap: break-word;
    text-overflow: ellipsis;
    overflow: hidden;
`;