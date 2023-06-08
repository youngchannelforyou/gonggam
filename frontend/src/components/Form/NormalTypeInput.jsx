import React from 'react';
import { css, cx } from '@emotion/css';

function NormalTypeInput({ labelText, onChangeFuc, value, styles, type, children }) {
  return (
    <div className={cx(inputForm, styles)}>
      {labelText && <label className={inputLabel}>{labelText}</label>}
      <div className={inputWrapper}>
        <input name={labelText} className={idInput} value={value} onChange={onChangeFuc} type={type} />
        {children}
      </div>
    </div>
  );
}

export default NormalTypeInput;

NormalTypeInput.defaultProps = {
  onChangeFuc: () => { },
  labelText: "loading...",
  styles: css``,
  value: '',
  type: 'text'
}


const inputForm = css`
  width: 100%;
  color: #4D4E4F;
`;

const inputLabel = css`
  display: block;
  margin-bottom: 5.25px;
  font-size: 12px;
  font-weight: 400;
`;


const idInput = css`
  width: 100%;
  height: 36px;
  border: 1px solid black;
  padding: 10.5px;
  font-size: 10.5px;
  color: white;
  background-color: #242B29;
  filter: drop-shadow(0 0 15rem white);
  border-radius: 5px;
  margin-bottom: 12.75px;
`;

const inputWrapper = css`
  display: flex;
  gap: 15px;
`;