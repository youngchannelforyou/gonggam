import React from 'react';
import { css } from '@emotion/css';


function Board({ title, postList }) {
    return (
        <div className={container}>
            <div className={writingHeader}>
                <div className={writingTitle}>{title}</div>
                <div className={searchBox}>
                    <input className={searchInput} placeholder='게시글 찾아보기' />
                    <button className={searchButton}>검색</button>
                </div>
            </div>
            <div className={devideBar} />
            <table className={writingListWrapper}>
                {
                    postList.map((item, idx) => {
                        const tmpKey = idx + 1;
                        return (
                            <tr className={writingItemWrapper} key={tmpKey}>
                                <td className={writingItemTitle}>
                                    <a href='#'>{item.title}</a>
                                </td>
                                <td className={writingItemAuth}>
                                    <a href='#'>{item.auth}</a>
                                </td>
                                <td className={writingItemDate}>
                                    <a href='#'>{item.date}</a>
                                </td>
                            </tr>
                        )
                    })
                }
            </table>
        </div>
    );
}

export default Board;

const container = css`
    width: 100%;
`;

const writingHeader = css`
    display: flex;
    justify-content: space-between;
`;

const writingTitle = css`
    height: 56px;
    color: #CACACA;
    font-size: 20px;
    font-weight: bold;
    line-height: 56px;
`;

const searchBox = css`
    display: flex;
    align-items: center;

    height: 56px;
`;

const searchInput = css`
    width: 200px;
    height: 30px;
    padding: 8px 12px;
    color: white;

    background-color: #121416;
    border: 1px solid #404040;
    /* border-radius: 19px; */
`;

const searchButton = css`
    width: 50px;
    height: 30px;
    line-height: 30px;

    text-align: center;
    color: #D6D6D6;
    background-color: #404040;

`;

const devideBar = css`
    width: 100%;
    height: 1px;
    border: 1px solid #CACACA;
`;


const writingListWrapper = css`
    width: 100%;
    margin-top: 5px;
    margin-bottom: 10px;
    border-collapse: collapse;
    color: #9c9c9c;
    font-size: 14px;
    font-weight: 400;
`;

const writingItemWrapper = css`
    width: 100%;
    border-bottom: 1px solid #404040;

    td {
        height: 16px;
        line-height: 1.6;
        padding: 8px 4px 8px 14px;
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
