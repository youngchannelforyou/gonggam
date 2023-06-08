import React, { useState } from 'react';
import { css } from '@emotion/css';
import imgLogo from '../assets/imgLogo.png';
import textLogo from '../assets/textLogo.png';
import AccountSlider from '../components/AccountList/AccountSlider.jsx'
import MakeNewCalcPopup from '../components/Main/MakeNewCalcPopup';

function MainPage(props) {
    const [value, setValue] = useState('');
    const [isPopup, setIsPopup] = useState(false);

    const userName = '홍길동';

    console.log(isPopup);

    async function getMemberInfo() {
        await fetch('http://localhost:8080/Member/getmemberinfo', {
            method: 'POST',
            body: JSON.stringify({
                "book": value
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include' // 서버로 쿠키를 자동으로 전송합니다.

        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
            });
    }

    getMemberInfo();

    function onChange(e) {
        if (e.target.name === 'search')
            setValue(e.target.value);
    }

    function handleOnKeyPress(e) {
        if (e.key === 'Enter') {
            requestSearch(); // Enter 입력이 되면 클릭 이벤트 실행
        }
    };

    function logout() {

    }

    async function requestSearch(e) {
        await fetch('http://localhost:8080/AccountBook/findBook', {
            method: 'POST',
            body: JSON.stringify({
                "book": value
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            }
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
            });
    }

    return (
        <div className={container}>
            {isPopup ? <MakeNewCalcPopup setIsPopup={setIsPopup} /> : <></>}
            <div className={header}>
                <p className={userNameTag}>{userName}님</p>
                <button className={logoutButton} onClick={logout}>로그아웃</button>
            </div>
            <div className={main}>
                <div className={titleLogoWrapper}>
                    <img src={textLogo} alt='text_ogo' />
                </div>
                <div className={searchInputWrapper}>
                    <input className={searchInput} name='search' type='search' value={value} onChange={onChange} onKeyPress={handleOnKeyPress} />
                    <button className={submitButton} name='submit' type='submit' onClick={requestSearch}>
                        <img src={imgLogo} alt='search' />
                    </button>
                </div>

                <div className={makeNewCalcButtonWrapper}>
                    <button className={makeNewCalcButton} type='button' onClick={() => setIsPopup(!isPopup)}>새 가계부 만들기</button>
                </div>
            </div>
            <div className={accountBookList}>
                <AccountSlider />
                <AccountSlider />
            </div>

        </div>
    );
}

export default MainPage;

const container = css`
    position: relative;
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

const main = css`
    width: 698px;
    margin: 0 auto;
`;

const titleLogoWrapper = css`
    width: 200px;
    margin: 145px auto 0;

    clear: both;

    img {
        width: 100%;
    }
`;

const searchInputWrapper = css`
    position: relative;
    margin-top: 20px;
`;

const searchInput = css`
    width: 100%;
    height: 58px;
    border-radius: 50px;
    padding: 19px;
    padding-right: 62px;
    font-size: 16px;
    line-height: 20px;
`;


const submitButton = css`
    position: absolute;
    top: 14px;
    right: 19px;
    width: 30px;
    height: 30px;
    scale: 1;
    img {
        width: 100%;
    filter: invert(100%) drop-shadow(0 0 0 black);
    transition: 1s;
    }

    img:hover {
        scale: 1.5;
    transform: rotate(3600deg);
    }
`;

const makeNewCalcButtonWrapper = css`
    width: 144px;
    height: 48px;
    margin: 36px auto 0;
`;

const makeNewCalcButton = css`
    width: 100%;
    height: 100%;
    border: 1px solid #000;
    border-radius: 50px;
    background-color: #1E1E20;

    font-size: 15px;
    color: #A5A6A7;

    &:hover {
        color: white;
    background-color: #505056;
    transition: 0.3s;
    }
`;

const accountBookList = css`
    display: flex;
    flex-direction: column;
    gap: 50px;

    width: 810px;
    margin: 0 auto 100px;
`;