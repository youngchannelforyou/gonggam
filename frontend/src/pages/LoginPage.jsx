import React from 'react';
import LoginForm from '../components/Login/LoginForm';
import { css } from '@emotion/css';

function LoginPage(props) {
  return (
    <div className={container}>
      <div>
        <div className={titleWrapper}>
          <p className={mainTitle}>마음을 함께하는 공동 가계부</p>
          <p className={subTitle}>
            공금 관리를 위한 투명한 장부를 만들어보세요
          </p>
        </div>
        <LoginForm />
      </div>
    </div>
  );
}

export default LoginPage;

const container = css`
  position: absolute;
  top: 100px;
  left: 50%;
  transform: translate(-50%, 0);
`;


const titleWrapper = css`
  width: 405px;
  text-align: center;
`;

const mainTitle = css`
  font-size: 36px;
  color: white;
  margin-bottom: 21.75px;
`;

const subTitle = css`
  font-size: 18.75px;
  color: #7a7a7a;
  margin-bottom: 76.5px;
`;
