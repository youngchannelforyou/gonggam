import React from 'react';
import { css } from '@emotion/css';

function NormalTypeInput({ labelText, onChangeFuc, value, styles }) {
  return (
    <div className={inputForm}>
      <label className={inputLabel}>{labelText}</label>
      <input name={labelText} className={idInput} value={value} onChange={onChangeFuc} />
    </div>
  );
}

export default NormalTypeInput;

NormalTypeInput.defaultProps = {
  labelText: "loading...",
  onChangeFuc: () => { },
  styles: css``
}


const inputForm = css`
  width: 100%;
`;

const inputLabel = css`
  display: block;
  margin-bottom: 7px;
  font-size: 16px;
  font-weight: 400;
  color: #4D4E4F;
`;


const idInput = css`
  width: 100%;
  height: 48px;
  border: 1px solid black;
  padding: 14px;
  font-size: 14px;
  color: white;
  background-color: #242B29;
  filter: drop-shadow(0 0 15rem white);
  border-radius: 5px;
  margin-bottom: 17px;
`;