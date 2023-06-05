import { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';
import Logo from '../../assets/gonggamLogoRemove.png'
import Logo2 from '../../assets/logo.png'

function LoginForm() {
  const [id, setId] = useState(null);
  const [pw, setPw] = useState(null);

  function onChange(e) {
    const {
      target: { name, value },
    } = e;

    if (name === 'id') {
      setId(value);
    } else if (name === 'pw') {
      setPw(value);
    }
  }

  async function requestLogin(e) {
    e.preventDefault();
    console.log(e);

    const data = await fetch('http://localhost:8080/Member/login', {
      method: 'POST',
      body: JSON.stringify({
        Id: id,
        Password: pw,
      }),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      }
    });

    console.log(data);
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
  }

  return (
    <div className={container}>
      <form>
        <div className={mainTitle}>
          <img src={Logo} alt='draw' />
          <img src={Logo2} alt='img' />
        </div>
        <div className={inputWrapper}>
          <NormalTypeInput labelText='아이디' onChangeFuc={onChange} />
          <NormalTypeInput labelText='비밀번호' onChangeFuc={onChange} />
        </div>
        <div className={buttonWrapper}>
          <button type='button' onClick={requestLogin}>ㅇㅇㅇㅇㅇ</button>
          <NormalTypeButton title='로그인' onChangeFuc={requestLogin} styles={loginButton} />
          <NormalTypeButton title='회원가입' onChangeFuc={requestJoin} styles={joinButton} />
          <button type='button' onClick={requestJoin}>회원가입</button>
        </div>
      </form >
    </div >
  );
}

export default LoginForm;

const container = css`
  width: 414px;
  height: 500px;
 
  margin: 0 auto;
  background-color: black;
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
`;

const buttonWrapper = css`
  margin-top: 27.6px;
`;

const loginButton = css`
background-color: #fff;
  &:hover {
    background-color: #cdcbcb;
  }
  transition: 0.3s;
`;

const joinButton = css`
  background-color: #232526;
  color: white;
  &:hover {
    background-color: #101010;
  }
  transition: 0.3s;
`;