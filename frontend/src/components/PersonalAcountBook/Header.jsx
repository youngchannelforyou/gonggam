import React from 'react';
import { css } from '@emotion/css';

import imgLogo from '../../assets/imgLogo.png'
import textLogo from '../../assets/textLogo.png'
function Header() {
  return (
    <div className={mainTitle}>
      <img src={imgLogo} alt='draw' />
      <img src={textLogo} alt='img' />
    </div>
  );
}

export default Header;

const mainTitle = css`
  width: 100%;
  height: 41.25px;
  font-size: 11.25px;

  margin-bottom: 18px;
  img {
    height: 100%;
  }
`;