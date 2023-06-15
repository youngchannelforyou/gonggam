import React from 'react';
import { css } from '@emotion/css';

function AccountBookNameList({ title, list }) {
    console.log('list');
    console.log(list);
    return (
        <div>
            <div>
                <div className={titleWrapper}>{title}</div>
            </div>
            <div className={accountBookListWrapper}>
                {list.length === 0 ? <a className={accountBookElement} href='title'>현재 참여 중인 가계부가 없습니다.</a> : list.map((element, index) => {
                    const tmpKey = index + 1;
                    return <a className={accountBookElement} href='title' key={tmpKey}>{element}</a>
                })}
            </div>
        </div>
    );
}

export default AccountBookNameList;

const titleWrapper = css`
    color: white;
    font-size: 13px;
    font-weight: bold;
`;

const accountBookListWrapper = css`
    display: flex;
    flex-direction: column;
    gap: 7px;
    margin-top: 16px;
    color: #CACACA;
    font-size: 13px;
    padding-left: 11px;
`;

const accountBookElement = css`
    text-decoration: none;
`;