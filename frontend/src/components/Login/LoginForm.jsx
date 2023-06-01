import { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';

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

  async function requestLogin() {
    await fetch('localhost:8080/login', {
      method: 'POST',
      body: JSON.stringify({
        Id: id,
        Password: pw,
      }),
    });
  }

  async function requestJoin() {
    await fetch('localhost:8080/login', {
      method: 'POST',
      body: JSON.stringify({
        Id: id,
        Password: pw,
      }),
    });
  }

  return (
    <div className={container}>
      <form>
        <div className={mainTitle}>
          <p>ID 로그인</p>
        </div>
        <div className={inputWrapper}>
          <NormalTypeInput labelText='아이디' onChangeFuc={onChange} />
          <NormalTypeInput labelText='비밀번호' onChangeFuc={onChange} />
        </div>
        <div className={buttonWrapper}>
          <NormalTypeButton title='로그인' onChangeFuc={requestLogin} styles={loginButton} />
          <NormalTypeButton title='회원가입' onChangeFuc={requestJoin} styles={joinButton} />
        </div>
      </form >
    </div >
  );
}

export default LoginForm;

const container = css`
  width: 300px;
  height: 362px;
 
  margin: 0 auto;
  background-color: white;
  border-radius: 8px;
  padding: 22px 23px;
`;

const mainTitle = css`
  font-size: 15px;
`;

const inputWrapper = css`
    margin-top: 32px;
    font-size: 12px;
`;

const buttonWrapper = css`
  margin-top: 20px;
`;

const loginButton = css`
background-color: #fff;
  &:hover {
    background-color: #eee;
  }
  transition: 0.5s;
`;

const joinButton = css`
  background-color: #232526;
  color: white;
  &:hover {
    background-color: #000000;
  }
  transition: 1s;
`;