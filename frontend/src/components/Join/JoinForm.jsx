import { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';
import { useNavigate } from 'react-router-dom';

function JoinForm() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const movePage = useNavigate();


    function onChange(e) {
        const {
            target: { name, value },
        } = e;

        if (name === '아이디') {
            setId(value);
        } else if (name === '비밀번호') {
            setPw(value);
        }
    }

    async function requestJoin() {
        const data = await fetch('http://localhost:8080/Member/signupmember', {
            method: 'POST',
            body: JSON.stringify({
                "Id": "test",
                "Password": "12341234",
                "NickName": "가가가"
            }),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            }
        });

        console.log(data);
        movePage('/');
    }

    return (
        <div className={container}>
            <form>
                <div className={mainTitle}>
                    회원가입
                </div>
                <div className={inputWrapper}>
                    <div className={emailInputWrapper}>
                        <NormalTypeInput labelText='이메일' value={id} onChangeFuc={onChange} />
                    </div>
                    <div className={othersInputWrapper}>
                        <NormalTypeInput labelText='비밀번호' value={pw} onChangeFuc={onChange} />
                        <NormalTypeInput labelText='비밀번호 확인' value={pw} onChangeFuc={onChange} />
                        <NormalTypeInput labelText='닉네임' value={pw} onChangeFuc={onChange} />
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
    width: 414px;
    height: 700px;
    
    margin: 0 auto;
    background-color: black;
    color: white;
    border-radius: 20px;
    padding: 30px 32px;
`;

const mainTitle = css`
    height: 55px;
    font-size: 15px;

    img {
        height: 100%;
    }
`;

const inputWrapper = css`
    margin-top: 30px;
    font-size: 14px;
    margin-bottom: 57px;
`;

const emailInputWrapper = css`
    margin-bottom: 57px;
`;

const othersInputWrapper = css`
    display: flex;
    flex-direction: column;
    gap:22px;
`;

const buttonWrapper = css`
    margin-top: 27.6px;
`;

const joinButton = css`
    background-color: #232526;
    color: white;
    &:hover {
        background-color: #101010;
    }
    transition: 0.3s;
`;