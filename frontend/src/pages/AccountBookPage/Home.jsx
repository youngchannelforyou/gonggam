import React, { useEffect, useState } from 'react';
import { css, cx } from '@emotion/css';

import Dashboard from '../../components/PersonalAcountBook/Home/Dashboard';
import RecentLogList from '../../components/PersonalAcountBook/Home/RecentLogList';


import noticeIcon from '../../assets/noticeIcon.svg'
import communityIcon from '../../assets/communityIcon.svg'
import addImg from '../../assets/addImg.png';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import { useParams } from 'react-router-dom';


function Home() {
    const [accountNumber, setAccountNumber] = useState(null);
    const nowUrlParam = useParams();

    console.log(nowUrlParam);
    console.log(accountNumber);
    useEffect(() => {
        setAccountNumber(nowUrlParam.accountName.replace('account', ''));

    }, [nowUrlParam])

    const noticeList = [
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25', url: 'aaa' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25', url: 'aaa' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25', url: 'aaa' }
    ];

    const communityList = [
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' }
    ]

    async function homeGetRequest() {
        await fetch(`http://localhost:8080/gonggam/${accountNumber}/home`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.table(data);
            })
    }

    useEffect(() => {
        homeGetRequest();
    }, []);

    return (
        <AccountBookWrapper>
            <div className={mainTopBox}>
                <Dashboard accountNumber={accountNumber} />
            </div>
            <div className={mainBottomBox}>
                <div className={leftContent}>
                    <div className={cx(notieArea, writingBox)}>
                        <div className={titleWrapper}>
                            <img src={noticeIcon} alt='noticeIcon' />
                            <p>공지사항</p>
                        </div>
                        <table className={writingListWrapper}>
                            <tbody>
                                {
                                    noticeList.map((item, index) => {
                                        const tmpKey = index + 1;
                                        return (
                                            <tr key={tmpKey} className={writingItemWrapper}>
                                                <td className={writingItemTitle}>
                                                    <a href={item.url}>{item.title}</a>
                                                </td>
                                                <td className={writingItemAuth}>
                                                    <a href={item.url}>{item.auth}</a>
                                                </td>
                                                <td className={writingItemDate}>
                                                    <a href={item.url}>{item.date}</a>
                                                </td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                    <div className={cx(communityArea, writingBox)}>
                        <div className={titleWrapper}>
                            <img src={communityIcon} alt='communityIcon' />
                            <p>커뮤니티</p>
                        </div>
                        <table className={writingListWrapper}>
                            <tbody>
                                {
                                    communityList.map((item, index) => {
                                        const tmpKey = index + 1;
                                        return (
                                            <tr key={tmpKey} className={writingItemWrapper}>
                                                <td className={writingItemTitle}>
                                                    <a href='#'>{item.title}</a>
                                                </td>
                                                <td className={writingItemAuth}>
                                                    <a href='#'>{item.auth}</a>
                                                </td>
                                                <td className={writingItemDate}>
                                                    <a href='#'>{item.date}</a>
                                                </td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className={rightContent}>
                    <div className={addBox}><img src={addImg} alt='add' /></div>
                    <div className={recentBox}>
                        <RecentLogList />
                    </div>
                </div>
            </div>
        </AccountBookWrapper>
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

const titleWrapper = css`
    display: flex;
    color: #fff;

`;

const writingListWrapper = css`
    width: 100%;
    margin-top: 20px;
    margin-bottom: 10px;
    border-collapse: collapse;
    color: #fff;
    font-size: 13px;
    font-weight: 400;
`;

const writingItemWrapper = css`
    width: 100%;

    td {
        height: 16px;
        line-height: 16px;
        padding-bottom: 12px;
        text-overflow : ellipsis;
    }
`;

const writingItemTitle = css`
    text-align: left;
`;

const writingItemAuth = css`
    text-align: right;
`;
const writingItemDate = css`
    width: 71px;
    padding-left: 46px;
    text-align: right;
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