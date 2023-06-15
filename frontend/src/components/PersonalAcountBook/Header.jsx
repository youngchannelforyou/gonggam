import React from 'react';
import { css } from '@emotion/css';

import imgLogo from '../../assets/imgLogo.png'
import textLogo from '../../assets/textLogo.png'
import { useNavigate } from 'react-router-dom';
function Header() {

  const movePage = useNavigate();

  return (
    <button onClick={() => movePage('/main')} className={mainTitle}>
      <img src={imgLogo} alt='draw' />
      <img src={textLogo} alt='img' />
    </button>
  );
}

export default Header;

const mainTitle = css`
  height: 41.25px;
  font-size: 11.25px;

  margin-bottom: 18px;
  
  img {
    height: 100%;
  }
`;