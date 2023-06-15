import React from 'react';
import { css, cx } from '@emotion/css';

import Dashboard from '../../components/PersonalAcountBook/Home/Dashboard';
import RecentLogList from '../../components/PersonalAcountBook/Home/RecentLogList';


import noticeIcon from '../../assets/noticeIcon.svg'
import communityIcon from '../../assets/communityIcon.svg'
import addImg from '../../assets/addImg.png';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';


function Home() {
    const amount = '5381000058';
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

    return (
        <AccountBookWrapper>
            <div className={mainTopBox}>
                <Dashboard amount={amount} />
            </div>
            <div className={mainBottomBox}>
                <div className={leftContent}>
                    <div className={cx(notieArea, writingBox)}>
                        <div className={titleWrapper}>
                            <img src={noticeIcon} alt='noticeIcon' />
                            <p>공지사항</p>
                        </div>
                        <table className={writingListWrapper}>
                            {
                                noticeList.map((item) => {
                                    return (
                                        <tr className={writingItemWrapper}>
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
                        </table>
                    </div>
                    <div className={cx(communityArea, writingBox)}>
                        <div className={titleWrapper}>
                            <img src={communityIcon} alt='communityIcon' />
                            <p>커뮤니티</p>
                        </div>
                        <table className={writingListWrapper}>
                            {
                                communityList.map((item) => {
                                    return (
                                        <tr className={writingItemWrapper}>
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