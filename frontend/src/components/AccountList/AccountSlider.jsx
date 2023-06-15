import React from 'react';
import { css } from '@emotion/css';
import AccountElement from './AccountElement';

function AccountSlider(props) {
    const imgUrl = '../../assets/sampleImg.jpg';
    return (
        <div className={container}>
            <div className={listSlider}>
                <AccountElement title='한경대 - 컴퓨터공학과' memberCount='30' imgUrl={imgUrl} />
                <AccountElement title='한경대 - 컴퓨터공학과' memberCount='30' imgUrl={imgUrl} />
                <AccountElement title='한경대 - 컴퓨터공학과' memberCount='30' imgUrl={imgUrl} />
            </div>
        </div>
    );
}

export default AccountSlider;

const container = css`
    width: 100%;
`;

const listSlider = css`
    display: flex;
    flex-wrap: wrap;
    gap: 22.5px;
    margin-top: 37.5px;
`;