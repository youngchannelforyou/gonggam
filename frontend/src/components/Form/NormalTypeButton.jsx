import React from 'react';
import { cx, css } from '@emotion/css';

function NormalTypeButton({ onClickFuc, title, styles }) {
    return (
        <button className={cx(basicButton, styles)} onClick={() => onClickFuc()}>{title}</button>
    );
}

export default NormalTypeButton;

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
