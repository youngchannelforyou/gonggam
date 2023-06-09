import React from 'react';
import { css } from '@emotion/css';

function Dashboard({ amount }) {

    return (
        <div className={container}>
            <div className={leftContents}>
                <div>
                    <div>예산 잔액</div>
                    <div>
                        <span>₩</span>
                        <span>{amount}</span>
                    </div>
                </div>
                <div>
                    <div>버튼</div>
                    <div>그래프</div>
                </div>
            </div>
            <div className={rightContents}>
                <div>
                    <div>지출항목</div>
                    <div>표</div>
                    <div>항목</div>
                </div>
                <div>
                    <div>수익액</div>
                    <div>지출액</div>
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

const leftContents = css`
    width: 652px;
    height: 100%;
    padding: 19px 30px 58px 28px;
`;

const rightContents = css`
    width: 419px;
    height: 100%;
    padding: 19px 37px 0px 19px;
    background-color: #232526;
`;