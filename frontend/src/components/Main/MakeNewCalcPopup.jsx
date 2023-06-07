import React from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../Form/NormalTypeInput';
import NormalTypeButton from '../Form/NormalTypeButton';


function MakeNewCalcPopup({ setIsPopup }) {

    async function makeNewCalc() {
        // await fetch('http://localhost:8080/AccountBook/addBook', {
        //     method: 'POST',
        //     body: JSON.stringify({
        //         "Name": "test",
        //         "Public": "12341234",
        //         "Manager": "aaa",
        //         "memberToken": '5d8b4d50-a80b-45fd-87f0-4d9c6731fed2test1',
        //     }),
        //     headers: {
        //         'WWW-Authenticate': '5d8b4d50-a80b-45fd-87f0-4d9c6731fed2test1',
        //         'Content-Type': 'application/json',
        //         'Accept': 'application/json',
        //     }
        // })
        //     .then((responseData) => responseData.json())
        //     .then((data) => {
        //         console.log(data);
        //     });
    }

    return (
        <div className={popupWrapper}>
            <div className={popUp}>
                <div className={header}>
                    <p className={mainTitle}>새 가계부 만들기</p>
                    <button className={closeButton} onClick={() => setIsPopup(false)}>X</button>
                </div>
                <div>
                    <NormalTypeInput labelText="가계부 이름" onChangeFuc={() => { }} value={''} />
                    <NormalTypeInput labelText="관리자" onChangeFuc={() => { }} value={''} />
                    <NormalTypeInput labelText="공개 유무" onChangeFuc={() => { }} value={''} />
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
    height: 470px;
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