import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

import Navigation from './Navigation';
import AccountBookNameList from './AccountBookNameList';

function SideBar({ accountNumber }) {
    const [datas, setDatas] = useState(null);
    const userGrade = '반갑습니다';

    useEffect(() => {
        if (!accountNumber)
            return;
        homeGetRequest();
    }, [accountNumber]);

    async function homeGetRequest() {
        await fetch(`http://localhost:8080/gonggam/${accountNumber}/homeside`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                setDatas(data);
            })
    };

    return (
        <aside className={container}>
            <div className={userInfoWrapper}>
                <p className={userNameWrapper}>{datas?.user.name}님</p>
                <p className={userGradeWrapper}>{userGrade}</p>
            </div>
            <div className={accountBooskWrapper}>
                <div className={accountBookWrapper}>
                    <div className={accountBookInfoWrapper}>
                        <div className={accountBookNameWrapper}>{datas?.book.name}</div>
                        <div className={accountBookUserNumber}>{datas?.book.count}명이 참가 중</div>
                    </div>
                    <div>
                        <Navigation />
                    </div>
                </div>
                <div className={accountBookNameListWrapper}>
                    <div className={marginBottom48}>
                        <AccountBookNameList title='참여중인 가계부' list={datas?.user.PAccountBook} />
                    </div>
                    <div>
                        <AccountBookNameList title='관리중인 가계부' list={datas?.user.MAccountBook} />
                    </div>
                </div>
            </div>
        </aside>
    );
}

export default SideBar;

const container = css`
    height: 100%;
    border-radius: 20px;
    background-color: transparent ;
`;

const userInfoWrapper = css`
    width: 262px;
    height: 51px;

    background-color: #1E1E20;
    border: 1px solid #252729;
    border-radius: 10px;
    padding: 16px;

    color: white;
`;

const userNameWrapper = css`
    float:left;
    font-size: 15px;
    font-weight: bold;
`;

const userGradeWrapper = css`
    float: right;
    margin-top:2px;
    font-size: 12px;
    color: #9c9c9c;
`;

const accountBooskWrapper = css`
    width: 262px;
    min-height: 770px;
    
    margin-top: 18px;
    background-color: #1E1E20;
    border: 1px solid #252729;
    padding: 16px;
    border-radius: 10px;
`;

const accountBookWrapper = css`
    width: 230px;
    min-height: 200px;
    background-color: #232526;
    border-radius: 10px;
    padding: 19px 11px;
`;

const accountBookInfoWrapper = css`
    width: 208px;
    height: 57px;

    margin-bottom: 16px;
`;

const accountBookNameWrapper = css`
    height: 18px;
    font-size: 18px;
    font-weight: bold;
    color:white;
`;

const accountBookUserNumber = css`
    width: 208px;
    height: 29px;
    padding-bottom: 14px;
    margin-top: 7px;
    border-bottom: 1px solid #404040;

    font-size: 14px;
    color: #9C9C9C;
`;

const accountBookNameListWrapper = css`
    margin-top: 72px;
    margin-left: 10px;
`;

const marginBottom48 = css`
    margin-bottom: 48px;
`;