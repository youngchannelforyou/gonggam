import React from 'react';
import { css } from '@emotion/css';

function MainPage(props) {
    const userName = '홍길동';

    return (
        <div className={container}>
            <div className={header}>
                <p className={userNameTag}>{userName}님</p>
                <button className={logoutButton}>로그아웃</button>
            </div>
            <div className={search}></div>
            <div className={makeNewCalcButtonWrapper}></div>
            <div className={main}></div>
        </div>
    );
}

export default MainPage;

const container = css`
    width: 100%;
    padding-top: 26px;
`;

const header = css`
    display: flex;
    float: right;
    font-size: 20px;

`;

const userNameTag = css`
    color: #FFFFFF;

`;

const logoutButton = css`
    margin-left: 22px;
    margin-right: 42px;
    color: #A5A6A7;
`;

const search = css``;
const makeNewCalcButtonWrapper = css``;
const main = css``;
