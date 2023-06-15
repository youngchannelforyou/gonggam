import React, { useEffect, useState } from 'react';
import { css, cx } from '@emotion/css';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import Dashboard from '../../components/PersonalAcountBook/Home/Dashboard';
import RecentLogList from '../../components/PersonalAcountBook/Home/RecentLogList';
import Calendar from '../../components/PersonalAcountBook/AccountBook/Calendar';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';
import CalendarPopUp from '../../components/PersonalAcountBook/AccountBook/CalendarPopUp';

function AccountBook() {
    const [accountNumber, setAccountNumber] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isPopup, setIsPopup] = useState(false);

    const nowUrlParam = useParams();

    useEffect(() => {
        if (!nowUrlParam)
            return;
        setAccountNumber(nowUrlParam.accountName.replace('account', ''));
    }, [nowUrlParam]);

    useEffect(() => {
        if (!accountNumber)
            return;
        setIsLoading(false);
    }, [accountNumber]);

    return (
        <Loading isLoading={isLoading} >
            {isPopup ? <CalendarPopUp setIsPopup={setIsPopup} /> : <></>}
            <AccountBookWrapper accountNumber={accountNumber}>
                <div className={mainTopBox}>
                    <Dashboard accountNumber={accountNumber} />
                </div>

                <div className={mainMiddleBox}>
                    <div className={calcBox}>
                        <Calendar accountNumber={accountNumber} setIsPopup={setIsPopup} isPopup={isPopup} />
                    </div>
                    <div className={mainBottomBox}>
                        <div className={cx(listCommon, revenueBox)}>
                            <RecentLogList title='수입' accountNumber={accountNumber} />
                        </div>
                        <div>
                            <RecentLogList title='지출' accountNumber={accountNumber} />
                        </div>

                    </div>
                </div>
            </AccountBookWrapper>
        </Loading>
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