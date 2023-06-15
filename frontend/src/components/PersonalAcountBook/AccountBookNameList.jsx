import React from 'react';
import { css } from '@emotion/css';

function AccountBookNameList({ title, list }) {
    return (
        <div>
            <div>
                <div className={titleWrapper}>{title}</div>
            </div>
            <div className={accountBookListWrapper}>
                {list.map((element) => <a className={accountBookElement} href='title'>{element}</a>)}
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