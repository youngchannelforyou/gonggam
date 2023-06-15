import React from 'react';
import { css, cx } from '@emotion/css';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import Dashboard from '../../components/PersonalAcountBook/Home/Dashboard';
import RecentLogList from '../../components/PersonalAcountBook/Home/RecentLogList';
import Calendar from '../../components/PersonalAcountBook/AccountBook/Calendar';

function AccountBook() {
    const amount = '5381000058';

    return (
        <AccountBookWrapper>
            <div className={mainTopBox}>
                <Dashboard amount={amount} />
            </div>

            <div className={mainMiddleBox}>
                <div className={calcBox}>
                    <Calendar />
                </div>
                <div className={mainBottomBox}>
                    <div className={cx(listCommon, revenueBox)}>
                        <RecentLogList />
                    </div>
                    <div>
                        <RecentLogList />
                    </div>

                </div>
            </div>
        </AccountBookWrapper>
    );
};

export default AccountBook;



const mainTopBox = css`
    width: 1071px;
    height: 404px;
`;

const mainMiddleBox = css`
    display: flex;
    gap: 17px;

    width: 100%;
    margin-top: 29px;
`;

const mainBottomBox = css`
    width: 100%;
    height: 441px;
`;

const calcBox = css`
    width: 787x;
    min-height: 642px;
    border: 1px solid #252729;
    border-radius: 20px;
`;

const listCommon = css`
    
`;

const revenueBox = css`
    margin-bottom: 22px;
`; 