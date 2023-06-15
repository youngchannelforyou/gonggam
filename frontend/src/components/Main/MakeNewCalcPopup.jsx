import React, { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';


function MakeNewCalcPopup({ setIsPopup, userName }) {
    const [newCalcValue, setValue] = useState({
        'name': '', 'public': ''
    });

    async function makeNewCalc() {
        await fetch('http://localhost:8080/AccountBook/addBook', {
            method: 'POST',
            body: JSON.stringify({
                "Name": "test",
                "Public": "12341234",
                "Manager": userName,
            }),
            headers: {
                'WWW-Authenticate': '5d8b4d50-a80b-45fd-87f0-4d9c6731fed2test1',
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            }
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
            });
    }

    function onChange(e) {
        const {
            target: { name, value, checked },
        } = e;

        console.log(name);
        console.log(checked);
        if (name === '가계부 이름') {
            setValue({ ...newCalcValue, 'name': value });
        } else if (name === '공개 유무') {

            setValue({ ...newCalcValue, 'public': checked });
        }
    }

    console.log(newCalcValue);

    return (
        <div className={popupWrapper}>
            <div className={popUp}>
                <div className={header}>
                    <p className={mainTitle}>새 가계부 만들기</p>
                    <button className={closeButton} onClick={() => setIsPopup(false)}>X</button>
                </div>
                <div>
                    <NormalTypeInput labelText="가계부 이름" onChangeFuc={onChange} value={newCalcValue.name} />
                    <div className={inputForm}>
                        <label className={inputLabel}>공개 유무</label>
                        <div className={inputWrapper}>
                            <input name='공개 유무' className={idInput} onChange={onChange} value={newCalcValue.public} type='checkbox' />
                        </div>
                    </div>
                    {/* <NormalTypeInput labelText="관리자" onChangeFuc={() => { }} value={''} /> */}
                </div>
                <div className={buttonWrapper}>
                    <NormalTypeButton title='만들기' onClickFuc={makeNewCalc} styles={confirmButton} />
                </div>
            </div>
        </div>
    );
}

export default MakeNewCalcPopup;

const popupWrapper = css`
    position: fixed;
    top: 0px;
    width: 100vw;
    height: 100vh;
    z-index: 2;
    background-color: #38383876;
`;

const popUp = css`
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%, 0%);
    
    width: 500px;
    min-height: 300px;
    border-radius: 5%;

    background-color: black;
    border-radius: 20px;
    padding: 30px 32px;
`;

const header = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 33px;
    color: white;
`;

const mainTitle = css`
    font-size: 20px;
`;

const closeButton = css`
    font-size: 20px;
`;

const buttonWrapper = css`
    margin-top: 27.6px;
`;

const confirmButton = css`
    background-color: #fff;
    &:hover {
        background-color: #cdcbcb;
    }
    transition: 0.3s;
`;


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
  width: 20px;
  height: 20px;
  
  padding: 10.5px;
  font-size: 10.5px;
  color: white;
  background-color: #242B29;
  filter: drop-shadow(0 0 15rem white);
  border-radius: 5px;
  margin-left: 2px;
  margin-top: 5px;
  margin-bottom: 12.75px;
  
  &:checked {
    background-color: #eee;
  }
`;

const inputWrapper = css`
  display: flex;
  gap: 15px;

`;