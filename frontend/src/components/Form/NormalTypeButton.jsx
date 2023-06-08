import React from 'react';
import { cx, css } from '@emotion/css';

function NormalTypeButton({ onClickFuc, title, styles }) {
  return (
    <button className={cx(basicButton, styles)} type='button' onClick={onClickFuc}>{title}</button>
  );
}

export default NormalTypeButton;

NormalTypeButton.defaultPorps = {
  onClickFuc: () => { },
  title: 'loading...',
  styles: css``
}

const basicButton = css`
  display: block;
  width: 142.5px;
  height: 36px;
  margin: 0 auto 15px;
  padding: 10.5px;
  border: 1px #1C1E1F solid;
  border-radius: 10px;

  font-size: 12px;
`;
