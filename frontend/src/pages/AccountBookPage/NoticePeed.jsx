import React, { useEffect, useState } from 'react';
import Loading from '../../components/Loading/Loading';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import { useParams } from 'react-router-dom';

function NoticePeed(props) {
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
            </AccountBookWrapper>
        </Loading>
    );
}

export default NoticePeed;