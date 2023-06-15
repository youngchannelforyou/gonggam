import { css } from '@emotion/css';
import React, { useEffect } from 'react';

function SummaryBoard({ iconImg, title, datas }) {
    if (!datas)
        return;
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
                                        <a href={item.url}>{item.Title}</a>
                                    </td>
                                    <td className={writingItemAuth}>
                                        <a href={item.url}>{item.Member}</a>
                                    </td>
                                    <td className={writingItemDate}>
                                        <a href={item.url}>{convertedDate}</a>
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