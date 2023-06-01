import { useState } from 'react';
import { cx, css } from '@emotion/css';

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
          <div className={inputForm}>
            <label className={inputLabel}>아이디</label>
            <input name='id' className={idInput} onChange={onChange} />
          </div>
          <div className={inputForm}>
            <label className={inputLabel}>비밀번호</label>
            <input name='pw' className={idInput} onChange={onChange} />
          </div>
        </div>
        <div className={buttonWrapper}>
          <button className={cx(basicButton, loginButton)} onClick={() => requestLogin()}>로그인</button>
          <button className={cx(basicButton, joinButton)} onClick={() => requestJoin()}>회원가입</button>
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

const inputForm = css`
  width: 100%;
`;

const inputLabel = css`
  display: block;
  margin-bottom: 5px;
  font-weight: 400;
  color: #4D4E4F;
`;


const idInput = css`
  width: 100%;
  height: 35px;
  border: 1px solid black;
  border-radius: 5px;
  margin-bottom: 12px;
`;

const buttonWrapper = css`
  margin-top: 20px;
`;

const basicButton = css`
  display: block;
  width: 144px;
  height: 35px;
  margin: 0 auto 15px;
  padding: 10px;
  border: 1px #1C1E1F solid;
  border-radius: 10px;

  font-size: 12px;
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