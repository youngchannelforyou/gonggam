import React, { useEffect, useState } from 'react';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import Board from '../../components/PersonalAcountBook/Board/Board';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';

function Notice() {
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


    const postList = [
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' }
    ];

    return (
        <Loading isLoading={isLoading}>
            <AccountBookWrapper accountNumber={accountNumber} >
                <Board title='공지사항' postList={postList} />
            </AccountBookWrapper>
        </Loading>
    );
};

export default Notice;
