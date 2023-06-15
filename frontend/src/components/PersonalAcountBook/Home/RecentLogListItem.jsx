import React from 'react';
import { css } from '@emotion/css';


function RecentLogListItem({ item }) {
    const sign = item.type === 0 ? '+' : '-';
    return (
        <div className={recentLogList}>
            <div className={spendItemBox}>
                <div className={itemDate}>{item.date}</div>
                <div className={itemHead}>
                    <div className={itemTitle}>{item.title}</div>
                    <div className={itemMoney(item.type)}>
                        <span className={positionAdjust}>{sign}</span>
                        <span>{item.money}</span>
                    </div>
                </div>
                <div className={itemContents}>{item.contents}</div>
            </div>
        </div>
    );
}

export default RecentLogListItem;



const recentLogList = css`
    width: 100%;
    height: 98px;
    background-color: #232526;
    border: 1px solid #232526;
    border-radius: 20px;
`;

const spendItemBox = css`
    padding: 8px 12px;
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
    line-height: 20px;
    color: #D6D6D6;
    font-size: 13px;
`;

const itemMoney = (color) => css`
    height: 20px;
    padding: 0px 10px 0 5px;

    font-size: 12px;
    line-height: 20px;
    border-radius: 5px;
    ${color === 0 ? 'color: #318D60;' : 'color: #AD2F2F;'};
    ${color === 0 ? 'background-color: #172521;' : 'background-color: #2F1818;'};

`;

const positionAdjust = css`
    position: relative;
    top: -1px;
`;

const itemContents = css`
    display: -webkit-box;
    width: 100%;
    height: 30px;
    margin-top: 10px;

    color: #9c9c9c;
    font-size: 12px;
    line-height: 15px;
    word-wrap: break-word;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
    overflow: hidden;
`;