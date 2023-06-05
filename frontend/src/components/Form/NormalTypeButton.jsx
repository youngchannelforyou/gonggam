import React from 'react';
import { cx, css } from '@emotion/css';

function NormalTypeButton({ onClickFuc, title, styles }) {
  return (
    <button className={cx(basicButton, styles)} type='button' onClick={onClickFuc}>{title}</button>
  );
}

export default NormalTypeButton;

const basicButton = css`
  display: block;
  width: 190px;
  height: 48px;
  margin: 0 auto 20px;
  padding: 14px;
  border: 1px #1C1E1F solid;
  border-radius: 10px;

  font-size: 16px;
`;
