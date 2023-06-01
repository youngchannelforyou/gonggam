import React from 'react';
import LoginForm from '../components/Login/LoginForm';
import { css } from '@emotion/css';

function LoginPage(props) {
  return (
    <div className={container}>
      <div>
        <div className={titleWrapper}>
          <p className={mainTitle}>모두가 공감할 수 있는 가계부</p>
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
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;


const titleWrapper = css`
  text-align: center;
`;

const mainTitle = css`
  font-size: 48px;
  color: white;
  margin-bottom: 29px;
`;

const subTitle = css`
  font-size: 25px;
  color: #7a7a7a;
  margin-bottom: 102px;
`;
