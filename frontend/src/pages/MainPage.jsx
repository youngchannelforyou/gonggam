import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';
import imgLogo from '../assets/imgLogo.png';
import textLogo from '../assets/textLogo.png';
import AccountSlider from '../components/AccountList/AccountSlider.jsx'
import MakeNewCalcPopup from '../components/Main/MakeNewCalcPopup';
import Loading from '../components/Loading/Loading';

function MainPage() {
    const [value, setValue] = useState('');
    const [isPopup, setIsPopup] = useState(false);
    const [memberInfo, setMemberInfo] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    useEffect(() => {
        getMemberInfo();
    }, []);

    useEffect(() => {
        if (!memberInfo)
            return;
        if (!memberInfo.memberNickName)
            return;
        setIsLoading(false);
    }, [memberInfo]);

    async function getMemberInfo() {
        await fetch('http://localhost:8080/Member/getmemberinfo', {
            method: 'POST',
            body: JSON.stringify({}),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // 쿠키 전송을 위해 credentials 옵션을 include로 설정
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                setTimeout(() => { setMemberInfo(data); }, 3000);
            });
    }

    function onChange(e) {
        if (e.target.name === 'search')
            setValue(e.target.value);
    }

    function handleOnKeyPress(e) {
        if (e.key === 'Enter') {
            requestSearch(); // Enter 입력이 되면 클릭 이벤트 실행
        }
    };

    async function logout() {
        await fetch('http://localhost:8080/Member/logout', {
            method: 'GET',
            // body: JSON.stringify({}),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
            });
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
            },
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
            });
    }

    return (
        <div className={container}>
            <Loading isLoading={isLoading}>
                {isPopup ? <MakeNewCalcPopup setIsPopup={setIsPopup} userName={memberInfo.memberId} /> : <></>}
                <div className={header}>
                    <p className={userNameTag}>
                        {memberInfo?.memberNickName}님</p>
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
            </Loading>
        </div>
    );
}

export default MainPage;

const container = css`
    position: relative;
    width: 100%;
    height: 100%;
    padding-top: 26px;
`;

const header = css`
    display: flex;
    float: right;
    font-size: 15px;
`;

const userNameTag = css`
    color: #FFFFFF;
`;

const logoutButton = css`
    margin-left: 16.5px;
    margin-right: 31.5px;
    color: #A5A6A7;
`;

const main = css`
    width: 523.5px;
    margin: 0 auto;
`;

const titleLogoWrapper = css`
    width: 150px;
    margin: 108.75px auto 0;

    clear: both;

    img {
        width: 100%;
    }
`;

const searchInputWrapper = css`
    position: relative;
    margin-top: 15px;
`;

const searchInput = css`
    width: 100%;
    height: 43.5px;
    border-radius: 50px;
    padding: 14.25px;
    padding-right: 46.5px;
    font-size: 12px;
    line-height: 15px;
`;


const submitButton = css`
    position: absolute;
    top: 10.5px;
    right: 14.25px;
    width: 22.5px;
    height: 22.5px;
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
    width: 108px;
    height: 36px;
    margin: 27px auto 0;
`;

const makeNewCalcButton = css`
    width: 100%;
    height: 100%;
    border: 1px solid #000;
    border-radius: 50px;
    background-color: #1E1E20;

    font-size: 11.25px;
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
    gap: 37.5px;

    width: 607.5px;
    margin: 0 auto 75px;
`;