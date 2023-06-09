import React, { useEffect, useState } from 'react';
import { css, cx } from '@emotion/css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

function Navigation() {
    const movePage = useNavigate();
    const { accountName } = useParams();
    const location = useLocation();
    const pathname = location.pathname;
    const nowUrl = pathname.substring(pathname.lastIndexOf('/') + 1);


    function navigatePage(url) {
        console.log(url);
        console.log(nowUrl);
        movePage(`/${accountName}/${url}`);
    }

    return (
        <div className={container}>
            <button onClick={() => navigatePage('home')} className={buttonStyle('home', nowUrl)}>홈</button>
            <button onClick={() => navigatePage('budget')} className={buttonStyle('budget', nowUrl)}>예산안</button>
            <button onClick={() => navigatePage('asset')} className={buttonStyle('asset', nowUrl)}>자산</button>
            <button onClick={() => navigatePage('accountbook')} className={buttonStyle('accountbook', nowUrl)}>가계부</button>
            <button onClick={() => navigatePage('notice')} className={buttonStyle('notice', nowUrl)}>공지사항</button>
            <button onClick={() => navigatePage('community')} className={buttonStyle('community', nowUrl)}>커뮤니티</button>
            <button onClick={() => navigatePage('settings')} className={buttonStyle('settings', nowUrl)}>설정</button>
        </div >
    );
}

export default Navigation;

const container = css`
    display: flex;
    flex-direction: column;
    gap: 3px;
    
    width: 100%;
    background-color: #232526;
    
`;

const buttonStyle = (url, nowUrl) => css`
    width: 100%;
    height: 28px;
    padding: 6px 6px 6px 7px;
    border-radius: 5px;

    font-size: 13px;
    font-weight: bold;
    text-align: start;
    ${url === nowUrl ? 'color: #fff;' : 'color: #CACACA;'}
    ${url === nowUrl && 'background-color: black;'}
`;