import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';
import DashboardGraph from './DashboardGraph';
import WidthBar from './WidthBar';

function Dashboard({ amount }) {
    const [clickedScope, setClickedScope] = useState('');
    const [expenseBarItemsInfo, setExpenseBarItemsWidth] = useState([
        { title: '축제준비', percent: 0 },
        { title: '축제준비', percent: 0 },
        { title: '축제준비', percent: 0 },
        { title: '축제준비', percent: 0 },
        { title: '축제준비', percent: 0 },
        { title: '축제준비', percent: 0 },
    ]);
    const colorSet = ['#3D83F6', '#91D8FC', '#84D0CB', '#AE6BF2', '#5F5FDE', '#EA4C64'];

    useEffect(() => {
        setExpenseBarItemsWidth([
            { title: '축제준비', percent: 40 },
            { title: '축제준비', percent: 25 },
            { title: '축제준비', percent: 20 },
            { title: '축제준비', percent: 10 },
            { title: '축제준비', percent: 3 },
            { title: '축제준비', percent: 2 },
        ])
    }, [])

    return (
        <div className={container}>
            <div className={leftContents}>
                <div>
                    <div className={budgetText}>예산 잔액</div>
                    <div className={balanceWrapper}>
                        <p className={dollorIcon} >₩</p>
                        <p className={amountText}>{new Intl.NumberFormat().format(amount)}</p>
                    </div>
                </div>
                <div>
                    <div className={scopeButtonWrapper}>
                        <button className={scopeButton('day', clickedScope)} onClick={() => setClickedScope('day')}>1일</button>
                        <button className={scopeButton('week', clickedScope)} onClick={() => setClickedScope('week')}>1주</button>
                        <button className={scopeButton('month', clickedScope)} onClick={() => setClickedScope('month')}>1달</button>
                        <button className={scopeButton('year', clickedScope)} onClick={() => setClickedScope('year')}>1년</button>
                    </div>
                    <div>
                        <DashboardGraph />
                    </div>
                </div>
            </div>
            <div className={rightContents}>
                <div>
                    <div className={expenseText}>지출항목</div>
                    <div className={expenseBarWrapper}>
                        {
                            expenseBarItemsInfo.map((itemInfo, idx) =>
                                <div className={expenseBarItem(idx, colorSet[idx], itemInfo.percent)} />
                            )
                        }
                    </div>
                    <div className={expenseListWrapper}>
                        {
                            expenseBarItemsInfo.map((itemInfo, idx) =>
                                <div className={expenseListItemInfo}>
                                    <div className={colorPoint(colorSet[idx])}></div>
                                    <div className={expenseInfoWrapper}>
                                        <div className={expenseListTitle}>{itemInfo.title}</div>
                                        <div className={expenseListPercent}>{itemInfo.percent}%</div>
                                    </div>
                                </div>
                            )
                        }
                    </div>
                </div>
                <div className={divLine} />
                <div className={widthBarWrapper}>
                    <div className={revenueBar}>
                        <WidthBar title='수입액' totalAmount='50000000' amount='100235320' type={0} />
                    </div>
                    <div>
                        <WidthBar title='지출액' totalAmount='50000000' amount='200235320' type={1} />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;

const container = css`
    display: flex;
    width: 1071px;
    height: 100%;
    border-radius: 20px;

    background-color: #1C1E1F;
    overflow: hidden;
`;

const budgetText = css`
    font-size:18px;
    font-weight: 500;
    color: #9C9C9C;
`;

const balanceWrapper = css`
    display: flex;
    margin-top:2px;
`;

const dollorIcon = css`
    
    margin-top: 10px;
    margin-right: 5px;

    vertical-align: top;
    font-size: 15px;
    color: #9C9C9C;
`;

const scopeButtonWrapper = css`
    display:flex;
    gap: 10px;
    margin-top: 20px;
`;

const scopeButton = (idx, clicked) => css`
    width: 45px;
    height: 25px;
    background-color: #363636;
    border-radius: 30px;

    font-size: 11px;
    font-weight: 600;
    text-align: center;
    ${idx === clicked ? 'color: white;' : 'color: #BDBDBD;'}
    ${idx === clicked ? 'border: 1px solid white' : ''};
    transition: 0.1s;
`;

const amountText = css`
    height: 48px;
    vertical-align: top;
    font-size: 40px;
    line-height: 48px;
    font-weight: 600;
    color: white;
`;

const leftContents = css`
    width: 652px;
    height: 100%;
    padding: 19px 30px 58px 30px;
`;

const rightContents = css`
    width: 419px;
    height: 100%;
    padding: 19px 37px 0px 19px;
    background-color: #232526;
`;

const expenseText = css`
    font-size: 15px;
    color: #fff;
    font-weight: bold;
`;

const expenseBarWrapper = css`
    display: flex;
    gap: 3px;

    width: 361px;
    height: 18px;
    margin-top: 16px;
    border: 1px solid #D2D2D2;
    border-radius: 4px;

    overflow: hidden;
`;

const expenseBarItem = (idx, color, percent) => css`
    width: ${percent}%;
    height: 100%;
    background-color: ${color};
    transition: ${idx + 1}s;
`;

const expenseListWrapper = css`
    display: flex;
    justify-content: space-between;
    margin-bottom: 36px;
    flex-wrap: wrap;
    row-gap: 26px;
    margin-top: 26px;
`;

const expenseListItemInfo = css`
    display: flex;
    width: 31%;
    height: 31px;
`;



const colorPoint = (color) => css`
    width: 6px;
    height: 6px;
    border-radius: 50%;
    background-color: ${color};
`;

const expenseInfoWrapper = css`
    margin-left: 7px;
`;

const expenseListTitle = css`
    color: white;
    font-size: 12px;
`;

const expenseListPercent = css`
    margin-top: 8px;
    color: #87898a;
    font-size: 12px;
    font-weight: bold;
`;

const divLine = css`
    width: 100%;
    height: 1px;
    border: 1px solid rgba(64, 64, 64, 0.51);
`;

const widthBarWrapper = css`
    padding: 14px 9px 0 0%;
`;

const revenueBar = css`
    margin-bottom: 48px;
`;