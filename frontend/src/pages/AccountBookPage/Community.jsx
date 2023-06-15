import React, { useEffect, useState } from 'react';

import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import Board from '../../components/PersonalAcountBook/Board/Board';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';
import Comments from '../../components/Comment/Comments';

function Community() {
    const [accountNumber, setAccountNumber] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [page, setPage] = useState(1);
    const [postData, setPostData] = useState([
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' },
        { Num: 0, Title: '안녕하세요 이것은 랜덤글입니다.', Member: '홍길동', Date: '2023-05-25' }
    ]);
    const nowUrlParam = useParams();

    useEffect(() => {
        if (!nowUrlParam)
            return;
        setAccountNumber(nowUrlParam.accountName.replace('account', ''));
    }, [nowUrlParam]);

    useEffect(() => {
        if (!accountNumber)
            return;
        communityPostsRequest();
    }, [accountNumber]);


    async function communityPostsRequest() {
        await fetch(`http://localhost:8080/gonggam/${accountNumber}/community/${page}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                setPostData(data.community);
                setIsLoading(false);
            })
    };

    return (
        <Loading isLoading={isLoading}>
            <AccountBookWrapper accountNumber={accountNumber} >
                <Board title='커뮤니티' postList={postData} accountNumber={accountNumber} />
                <Comments />
            </AccountBookWrapper>
        </Loading>
    );
};

export default Community;

