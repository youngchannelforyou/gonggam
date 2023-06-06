import React, { useState } from 'react';
import { css } from '@emotion/css';

function AccountBookPage(props) {
    const [receiptImg, setReceiptImg] = useState(null);
    const receiptImaChange = (e) => {
        setReceiptImg(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (receiptImg) {
            await sendReceipt(receiptImg);
            alert('전송되었습니다.');
        }
    };

    const sendReceipt = async () => {
        const formData = new FormData();
        formData.append("file", receiptImg);
        formData.append("file", receiptImg);
        formData.append("Text", "test");
        formData.append("Title", "1234");
        formData.append("Date", "1234");
        formData.append("Type", true);
        formData.append("Tag", "123");
        formData.append("Budget", 123);
        formData.append("Table", "testbook1");

        console.log(formData)
        const txt = "test";
        await fetch("http://localhost:8080/AccountBook/addpost", {
            method: "POST",
            headers: {
            },
            body: formData,
            headers: {
                'Accept': 'application/json',
            }
        });
    };

    return (
        <div>
            AccountBook페이지입니다.
            <input className={receiptImgInput} accept=".jpg, .png, .jpeg" type="file" onChange={receiptImaChange} />
            <button className={submitImgButton} type="submit" onClick={handleSubmit}>
                사진 보내기
            </button>
        </div>
    );
}

const receiptImgInput = css`
    background-color: blue;
`;

const submitImgButton = css`
    width: 100px;
    height: 100px;
    color: #fff;
`;

export default AccountBookPage;