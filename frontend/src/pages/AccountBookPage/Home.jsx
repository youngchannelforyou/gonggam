import React from 'react';
import { css } from '@emotion/css';

import SideBar from '../../components/AcountBook/SideBar';
import Header from '../../components/AcountBook/Header';
import addImg from '../../assets/addImg.png';
import Dashboard from '../../components/AcountBook/Home/Dashboard';

function Home() {

    return (
        <div className={container}>
            <Header />
            <div className={sectionWrapper}>
                <div>
                    <SideBar />
                </div>
                <main className={mainContentsWrapper}>
                    <div className={mainTopBox}>
                        <Dashboard amount='5,381,000,058' />
                    </div>
                    <div className={mainBottomBox}>
                        <div className={leftContent}>
                            <div className={noticeBox}></div>
                            <div className={communityBox}></div>
                        </div>
                        <div className={rightContent}>
                            <div className={addBox}><img src={addImg} alt='add' /></div>
                            <div className={recentLogList}></div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
};

export default Home;

const container = css`
    width: 100%;
    min-width: 1393px;
    min-height: 980px;
    height: 100%;
    margin: 25px 0 25px 30px;
    padding-right: 30px;
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

const mainTopBox = css`
    width: 1071px;
    height: 404px;
`;

const mainBottomBox = css`
    display: flex;
    gap: 17px;
    width: 1071px;
    height: 441px;
    margin-top: 29px;
`;

const leftContent = css`
    width: 771px;
`;

const rightContent = css`
    width: 278px;
`;

const noticeBox = css`
    width: 777px;
    height: 155px;

    border: 1px solid #252729;
    border-radius: 20px;
    margin-bottom: 19px;
`;

const communityBox = css`
    width: 777px;
    height: 267px;

    border: 1px solid #252729;
    border-radius: 20px;
`;

const addBox = css`
    width: 100%;
    height: 116px;
    margin-bottom: 19px; 
`;

const recentLogList = css`
    width: 100%;
    height: 306px;
    background-color: #1C1E1F;
    border: 1px solid #252729;
    border-radius: 20px;
`;