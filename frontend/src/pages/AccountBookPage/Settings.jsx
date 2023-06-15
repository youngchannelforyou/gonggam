import React, { useEffect, useState } from 'react';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';

function Settings() {
    const [accountNumber, setAccountNumber] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

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
                <div></div>
            </AccountBookWrapper>
        </Loading>
    );
};

export default Settings;

