import React from 'react';
import { css } from '@emotion/css';

function NormalTypeInput({ labelText, onChangeFuc }) {
    return (
        <div className={inputForm}>
            <label className={inputLabel}>{labelText}</label>
            <input name={labelText} className={idInput} onChange={onChangeFuc} />
        </div>
    );
}

export default NormalTypeInput;


const inputForm = css`
  width: 100%;
`;

const inputLabel = css`
  display: block;
  margin-bottom: 5px;
  font-weight: 400;
  color: #4D4E4F;
`;


const idInput = css`
  width: 100%;
  height: 35px;
  border: 1px solid black;
  border-radius: 5px;
  margin-bottom: 12px;
`;