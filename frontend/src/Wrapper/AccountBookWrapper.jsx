import React from 'react';
import { css } from '@emotion/css';
import Header from '../components/PersonalAcountBook/Header';
import SideBar from '../components/PersonalAcountBook/SideBar';

function AccountBookWrapper({ bookTitle, memberCount, children, userInfo }) {

    return (
        <div className={container}>
            <div className={alignCenter}>
                <Header />
                <div className={sectionWrapper}>
                    <div>
                        <SideBar bookTitle={bookTitle} memberCount={memberCount} userInfo={userInfo} />
                    </div>
                    <main className={mainContentsWrapper}>
                        {children}
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