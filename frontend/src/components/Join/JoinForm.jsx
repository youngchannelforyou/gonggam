import { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';
import { useNavigate } from 'react-router-dom';

function JoinForm() {
    const [inputValue, setInputValue] = useState({});
    const [emailRequestInputText, setEmailRequestInputText] = useState(null);
    const [isUserEmailReceived, serIsUserEmailReceived] = useState(false);
    const movePage = useNavigate();


    function onChange(e) {
        const {
            target: { name, value },
        } = e;
        let currentValue = { ...inputValue };
        if (name === '이메일') {
            currentValue.email = value;
        } else if (name === '비밀번호') {
            currentValue.password = value;
        } else if (name === '이메일 코드 확인') {
            currentValue.emailCode = value;
        } else if (name === '비밀번호') {
            currentValue.password = value;
        } else if (name === '비밀번호 확인') {
            currentValue.pwCheck = value;
        } else if (name === '닉네임') {
            currentValue.nickName = value;
        }
        console.log(currentValue);
        setInputValue(currentValue);
    }

    const isEmail = (email) => {
        const emailRegex =
            /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

        return emailRegex.test(email);
    };

    async function sendAuthMail() {
        if (!isEmail(inputValue.email)) {
            alert('이메일 형식이 올바르지 않습니다!');
            return;
        }
        setEmailRequestInputText('전송 중');

        try {
            await fetch('http://localhost:8080/Member/createcode', {
                method: 'POST',
                body: JSON.stringify({
                    "Email": inputValue.email,
                }),
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                }

            })
                .then((responseData) => responseData.json())
                .then((data) => {
                    console.log(data);
                    if (data.status === "200") {
                        setEmailRequestInputText('전송 완료');
                        serIsUserEmailReceived(true);
                    }
                })
        } catch (error) {
            setEmailRequestInputText(null);
        }
    }

    async function checkcEmailCode() {
        await fetch('http://localhost:8080/Member/checkcode', {
            method: 'POST',
            body: JSON.stringify({
                "Email": inputValue.email,
                "Code": inputValue.emailCode,
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            }
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
                if (data.code === 200) {
                    console.log('succes');
                }
            })
    }

    async function requestJoin() {
        await fetch('http://localhost:8080/Member/signupmember', {
            method: 'POST',
            body: JSON.stringify({
                "Id": inputValue.email,
                "Password": inputValue.password,
                "NickName": inputValue.nickName
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            }
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
                movePage('/');
                if (data.code === 200) {
                }
            })

    }

    return (
        <div className={container}>
            <form>
                <div className={mainTitle}>
                    회원가입
                </div>
                <div className={inputWrapper}>
                    <div className={emailInputWrapper}>
                        <NormalTypeInput labelText='이메일' value={inputValue.email} onChangeFuc={onChange} type='email' >
                            <button className={authMailSendButton(emailRequestInputText)} type='button' onClick={sendAuthMail}>
                                {emailRequestInputText ?? '인증번호 받기'}
                            </button>
                        </NormalTypeInput >
                        {isUserEmailReceived &&
                            <NormalTypeInput labelText='이메일 코드 확인' value={inputValue.emailCode} onChangeFuc={onChange}>
                                <button className={authMailSendButton(emailRequestInputText)} type='button' onClick={checkcEmailCode}>
                                    인증번호 확인
                                </button>
                            </ NormalTypeInput>}
                    </div>
                    <div className={othersInputWrapper}>
                        <NormalTypeInput labelText='비밀번호' value={inputValue.password} onChangeFuc={onChange} />
                        <NormalTypeInput labelText='비밀번호 확인' value={inputValue.pwCheck} onChangeFuc={onChange} />
                        <NormalTypeInput labelText='닉네임' value={inputValue.nickName} onChangeFuc={onChange} />
                    </div>
                </div>
                <div className={buttonWrapper}>
                    <NormalTypeButton title='회원가입' onClickFuc={requestJoin} styles={joinButton} />
                </div>
            </form >
        </div >
    );
}

export default JoinForm;



const container = css`
    width: 310.5px;
    height: 525px;
    
    margin: 0 auto;
    background-color: black;
    color: white;
    border-radius: 20px;
    padding: 22.5px 24px;
`;

const mainTitle = css`
    height: 41.25px;
    font-size: 15px;
    font-weight: bold;
    color: #cecece;
    img {
        height: 100%;
    }
`;

const inputWrapper = css`
    margin-top: 22.5px;
    font-size: 10.5px;
    margin-bottom: 42,75px;
`;

const emailInputWrapper = css`
    display: flex;
    align-items: center;
    margin-bottom: 42.75px;
`;



const authMailSendButton = (emailRequestInputText) => css`
    width: 112.5px;
    font-size: 10.5px;
    font-weight: bold;
    height: 36px;
    background-color: #cecece;
    border: 1px solid black;
    border-radius: 5px;
    transition: 0.3s;

    ${emailRequestInputText !== null && 'background-color: black;'}
`;

const othersInputWrapper = css`
    display: flex;
    flex-direction: column;
    gap: 16.5px;
`;

const buttonWrapper = css`
    margin-top: 20.7px;
`;

const joinButton = css`
    background-color: #232526;
    color: white;
    &:hover {
        background-color: #101010;
    }
    transition: 0.3s;
`;