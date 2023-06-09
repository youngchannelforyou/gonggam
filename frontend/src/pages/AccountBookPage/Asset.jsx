import React from 'react';
import { css } from '@emotion/css';

import SideBar from '../../components/AcountBook/SideBar';

function Asset() {

    return (
        <div className={container}>
            <SideBar />
            <main>

            </main>
        </div>
    );
};

export default Asset;

const container = css`
    width: 100%;
    background-color: #121416;
    display: flex;
    ;
`;