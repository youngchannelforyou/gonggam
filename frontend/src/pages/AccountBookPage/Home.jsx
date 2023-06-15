import React, { useEffect, useState } from 'react';
import { css, cx } from '@emotion/css';

import Dashboard from '../../components/PersonalAcountBook/Home/Dashboard';
import RecentLogList from '../../components/PersonalAcountBook/Home/RecentLogList';


import noticeIcon from '../../assets/noticeIcon.svg'
import communityIcon from '../../assets/communityIcon.svg'
import addImg from '../../assets/addImg.png';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import { useParams } from 'react-router-dom';
import Loading from '../../components/Loading/Loading';
import SummaryBoard from '../../components/PersonalAcountBook/Home/SummaryBoard';


function Home() {
    const [accountNumber, setAccountNumber] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [peedDatas, setPeedDatas] = useState(null);
    const nowUrlParam = useParams();

    useEffect(() => {
        if (!nowUrlParam)
            return;
        setAccountNumber(nowUrlParam.accountName.replace('account', ''));
    }, [nowUrlParam]);

    useEffect(() => {
        if (!accountNumber)
            return;
        getHomePeed();

    }, [accountNumber]);

    async function getHomePeed() {
        await fetch(`http://localhost:8080/gonggam/${accountNumber}/homepeed`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                setPeedDatas(data);
                setIsLoading(false);
            })
    };

    return (
        <Loading isLoading={isLoading}>
            <AccountBookWrapper accountNumber={accountNumber} >
                <div className={mainTopBox}>
                    <Dashboard accountNumber={accountNumber} />
                </div>
                <div className={mainBottomBox}>
                    <div className={leftContent}>
                        <div className={cx(notieArea, writingBox)}>
                            <SummaryBoard title='공지사항' iconImg={noticeIcon} datas={peedDatas?.notice} />
                        </div>
                        <div className={cx(communityArea, writingBox)}>
                            <SummaryBoard title='커뮤니티' iconImg={communityIcon} datas={peedDatas?.communities} />
                        </div>
                    </div>
                    <div className={rightContent}>
                        <div className={addBox}><img src={addImg} alt='add' /></div>
                        <div className={recentBox}>
                            <RecentLogList title='최근 변동 내역 ' accountNumber={accountNumber} />
                        </div>
                    </div>
                </div>
            </AccountBookWrapper>
        </Loading>
    );
};

export default Home;


const mainTopBox = css`
    width: 1071px;
    height: 404px;
`;

const mainBottomBox = css`
    display: flex;
    gap: 17px;
    width: 1071px;
    height: 441px;
    margin-top: 29px;
`;

const leftContent = css`
    width: 771px;
`;

const rightContent = css`
    width: 278px;
`;

const notieArea = css`
    width: 777px;
    min-height: 160px;
`;

const writingBox = css`
    padding: 20px 27px 0 17px;
    border: 1px solid #252729;
    border-radius: 20px;
    margin-bottom: 19px;
`;

const communityArea = css`
    width: 777px;
    height: 267px;
`;

const addBox = css`
    width: 100%;
    height: 116px;
    margin-bottom: 19px; 
`;

const recentBox = css`

`