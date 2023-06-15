import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import BudgeBox from '../../components/PersonalAcountBook/Budget/BudgetBox';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';


function Budget() {
    const [accountNumber, setAccountNumber] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const spendTagList = ['축제준비', 'MT준비', '신입생환영회', '성인의날행사', '임원진회식', '기념일행사'];
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
        <Loading isLoading={isLoading}>
            <AccountBookWrapper accountNumber={accountNumber} >
                <div>
                    <BudgeBox spendTagList={spendTagList} title='지출 항목' type={0} />
                </div>
                <div className={spendListWrapper}>
                    <BudgeBox spendTagList={spendTagList} title='수입 항목' type={1} />
                </div>
            </AccountBookWrapper>
        </Loading>
    );
};

export default Budget;

const spendListWrapper = css`
    margin-top: 30px;
`;