import React from 'react';
import { css } from '@emotion/css';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import BudgeBox from '../../components/PersonalAcountBook/Budget/BudgetBox';


function Budget() {
    const spendTagList = ['축제준비', 'MT준비', '신입생환영회', '성인의날행사', '임원진회식', '기념일행사'];

    return (
        <AccountBookWrapper>
            <div>
                <BudgeBox spendTagList={spendTagList} title='지출 항목' type={0} />
            </div>
            <div className={spendListWrapper}>
                <BudgeBox spendTagList={spendTagList} title='수입 항목' type={1} />
            </div>
        </AccountBookWrapper>
    );
};

export default Budget;

const spendListWrapper = css`
    margin-top: 30px;
`;