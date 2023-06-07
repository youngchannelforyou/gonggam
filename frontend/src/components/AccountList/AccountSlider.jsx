import React from 'react';
import { css } from '@emotion/css';
import AccountElement from './AccountElement';

function AccountSlider(props) {
    const imgUrl = '../../assets/sampleImg.jpg';
    return (
        <div className={container}>
            <div className={title}>

            </div>
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
`

const title = css`    
`;

const listSlider = css`
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    margin-top: 50px;
`;