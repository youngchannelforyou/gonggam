import { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';
import imgLogo from '../../assets/imgLogo.png'
import textLogo from '../../assets/textLogo.png'
import { useNavigate } from 'react-router-dom';

function LoginForm() {
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

  async function requestLogin(e) {
    e.preventDefault();

    console.log(`id: ${id}`);
    console.log(`pw: ${pw}`);
    const responseData = await fetch('http://localhost:8080/Member/login', {
      method: 'POST',
      body: JSON.stringify({
        Id: id,
        Password: pw,
      }),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      }
    })
    try {
      await responseData.json()
        .then((data) => {
          console.log(data);
          if (data.status === "404") {
            console.log('frontend_404');
          } else if (data.status === "403") {
            console.log('frontend_403');
          } else {
            movePage('/main');
          }
        })
    } catch (e) {
      console.log(e);
    }
  }

  async function requestJoin() {
    movePage('/join')
  }

  return (
    <div className={container}>
      <form>
        <div className={mainTitle}>
          <img src={imgLogo} alt='draw' />
          <img src={textLogo} alt='img' />
        </div>
        <div className={inputWrapper}>
          <NormalTypeInput labelText='아이디' value={id} onChangeFuc={onChange} />
          <NormalTypeInput labelText='비밀번호' value={pw} onChangeFuc={onChange} />
        </div>
        <div className={buttonWrapper}>
          <NormalTypeButton title='로그인' onClickFuc={requestLogin} styles={loginButton} />
          <NormalTypeButton title='회원가입' onClickFuc={requestJoin} styles={joinButton} />
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