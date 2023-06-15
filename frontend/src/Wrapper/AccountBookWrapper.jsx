import React from 'react';
import { css } from '@emotion/css';
import Header from '../components/AcountBook/Header';
import SideBar from '../components/AcountBook/SideBar';

function AccountBookWrapper(props) {
    return (

        <div className={container}>
            <div className={alignCenter}>
                <Header />
                <div className={sectionWrapper}>
                    <div>
                        <SideBar />
                    </div>
                    <main className={mainContentsWrapper}>
                        {props.children}
                    </main>
                </div>
            </div>
        </div >
    );
}

export default AccountBookWrapper;

const container = css`
    width: 100%;
    min-width: 1423px;
    min-height: 980px;
    height: 100%;
    padding: 25px 30px;
`;


const alignCenter = css`
    width: 1363px;
    margin: 0 auto;
`;


const sectionWrapper = css`
    display: flex;
    width: 100%;
    flex-shrink: 0;
`;


const mainContentsWrapper = css`
    flex-grow: 1;
    margin-left: 30px;
`;