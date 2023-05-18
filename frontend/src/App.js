import './App.css';
import { css } from '@emotion/css';
import { useEffect, useState } from 'react';
function App() {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');

  function onChange(e) {
    //const {name, value} = event.target 과 같음
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

  console.log(`${id} : ${pw}`);

  return (
    <div className='App'>
      <div className=''>
        <div>
          <p>모두가 공감할 수 있는 가계부</p>
          <p>공금 관리를 위한 투명한 장부를 만들어보세요</p>
        </div>

        <form>
          <div>로그인</div>
          <label>아이디</label>
          <input name='id' onChange={onChange} />
          <label>비밀번호</label>
          <input name='pw' onChange={onChange} />
          <button onClick={() => requestLogin()}>로그인</button>
          <button onClick={() => requestLogin()}>회원가입</button>
        </form>
      </div>
    </div>
  );
}

export default App;
