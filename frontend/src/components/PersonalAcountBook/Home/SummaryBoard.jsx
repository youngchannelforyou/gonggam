import { css } from '@emotion/css';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function SummaryBoard({ iconImg, title, datas }) {
    const [accountNumber, setAccountNumber] = useState(null);
    const nowUrlParam = useParams();
    const titlePath = title === '커뮤니티' ? 'communitypeed' : 'noticepeed';
    const movePage = useNavigate();

    useEffect(() => {
        if (!nowUrlParam)
            return;
        setAccountNumber(nowUrlParam.accountName.replace('account', ''));
    }, [nowUrlParam]);

    console.log(datas);

    return (
        <>
            <div className={titleWrapper}>
                <img src={iconImg} alt='noticeIcon' />
                <p>{title}</p>
            </div>
            <table className={writingListWrapper}>
                <tbody>
                    {
                        datas?.map((item, index) => {
                            const tmpKey = index + 1;
                            const convertedDate = item.Date.replace(/-/g, '.');
                            return (
                                <tr key={tmpKey} className={writingItemWrapper}>
                                    <td className={writingItemTitle}>
                                        <button onClick={() => movePage(`/account${accountNumber}/${titlePath}/${item.Num}`)}>{item.Title}</button>
                                    </td>
                                    <td className={writingItemAuth}>
                                        <button onClick={() => movePage(`/account${accountNumber}/${titlePath}/${item.Num}`)}>{item.Member}</button>
                                    </td>
                                    <td className={writingItemDate}>
                                        <button onClick={() => movePage(`/account${accountNumber}/${titlePath}/${item.Num}`)}>{convertedDate}</button>
                                    </td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
        </>
    );
}

export default SummaryBoard;


const titleWrapper = css`
    display: flex;
    color: #fff;
`;


const writingListWrapper = css`
    width: 100%;
    margin-top: 20px;
    margin-bottom: 10px;
    border-collapse: collapse;
    color: #fff;
    font-size: 13px;
    font-weight: 400;
`;

const writingItemWrapper = css`
    width: 100%;

    td {
        height: 16px;
        line-height: 16px;
        padding-bottom: 12px;
        text-overflow : ellipsis;
    }
`;

const writingItemTitle = css`
    text-align: left;
`;

const writingItemAuth = css`
    text-align: right;
`;
const writingItemDate = css`
    width: 71px;
    padding-left: 46px;
    text-align: right;
`;