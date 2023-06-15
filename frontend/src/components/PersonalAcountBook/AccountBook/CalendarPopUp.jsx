import React, { useState } from 'react';
import { css } from '@emotion/css';
import NormalTypeInput from '../../Form/NormalTypeInput';
import NormalTypeButton from '../../Form/NormalTypeButton';
import { el } from 'date-fns/locale';


function CalendarPopUp({ setIsPopup }) {
    const [newCalcValue, setValue] = useState({});
    const [receiptImg, setReceiptImg] = useState(null);
    const receiptImaChange = (e) => {
        setReceiptImg(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (receiptImg) {
            await sendReceipt(receiptImg);
            alert('전송되었습니다.');
            setIsPopup(false);
        }
    };

    function onChange(e) {
        const {
            target: { name, value, checked },
        } = e;

        console.log(name);
        console.log(checked);
        if (name === '날짜') {
            setValue({ ...newCalcValue, 'date': value });
        } else if (name === '수익 / 지출') {
            setValue({ ...newCalcValue, 'range': value });
        } else if (name === '금액') {
            setValue({ ...newCalcValue, 'amount': value });
        } else if (name === '제목') {
            setValue({ ...newCalcValue, 'title': value });
        } else if (name === '설명') {
            setValue({ ...newCalcValue, 'describe': value });
        } else if (name === '분류') {
            setValue({ ...newCalcValue, 'tag': value });
        }
    };

    const sendReceipt = async (receiptImg) => {
        const formData = new FormData();
        formData.append("file", receiptImg);
        formData.append("Text", newCalcValue.describe);
        formData.append("Title", newCalcValue.title);
        formData.append("Date", newCalcValue.date);
        formData.append("Type", newCalcValue.range);
        formData.append("Tag", newCalcValue.tag);
        formData.append("Budget", newCalcValue.amount);
        formData.append("Table", "8");

        console.log(formData);

        await fetch("http://localhost:8080/AccountBook/addpost", {
            method: "POST",
            body: formData,
            headers: {
                'Accept': 'application/json',
            }
        });
    };

    console.log(newCalcValue);

    return (
        <div className={popupWrapper}>
            <div className={popUp}>
                <div className={header}>
                    <p className={mainTitle}>사용 내역 추가</p>
                    <button className={closeButton} onClick={() => setIsPopup(false)}>X</button>
                </div>
                <div>
                    <NormalTypeInput labelText="날짜" onChangeFuc={onChange} type='date' value={newCalcValue.date} />
                    <NormalTypeInput labelText="수익 / 지출" onChangeFuc={onChange} value={newCalcValue.range} />
                    <NormalTypeInput labelText="분류" onChangeFuc={onChange} value={newCalcValue.tag} />
                    <NormalTypeInput labelText="금액" onChangeFuc={onChange} value={newCalcValue.amount} />
                    <div className={inputForm}>
                        <label className={inputLabel}>영수증 사진</label>
                        <div className={imgInputWapper}>
                            <input className={receiptImgInput} accept=".jpg, .png, .jpeg" type="file" onChange={receiptImaChange} />
                        </div>
                    </div>
                    <NormalTypeInput labelText="제목" onChangeFuc={onChange} value={newCalcValue.title} />
                    <NormalTypeInput labelText="설명" onChangeFuc={onChange} value={newCalcValue.describe} />
                    <div className={buttonWrapper}>
                        <NormalTypeButton title='추가하기' type='submit' onClickFuc={handleSubmit} styles={confirmButton} />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CalendarPopUp;

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
    left: 50%;
    transform: translate(-50%, 7%);

    width: 350px;
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
`;

const imgInputWapper = css`
    display: flex;
    align-items: center;
`;

const inputLabel = css`
  display: block;
  margin-bottom: 5.25px;
  font-size: 12px;
  font-weight: 400;
  color: #4D4E4F;
`;

const receiptImgInput = css`
    width: 100%;
    
    border: 1px solid black;
    padding: 10.5px;
    font-size: 10.5px;
    color: white;
    background-color: #242B29;
    filter: drop-shadow(0 0 15rem white);
    border-radius: 5px;
    margin-bottom: 12.75px;
`;