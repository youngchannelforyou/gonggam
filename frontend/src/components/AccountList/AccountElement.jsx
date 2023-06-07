import React, { useState } from 'react';
import { css } from '@emotion/css';
import sample from '../../assets/sampleImg.jpg'
import userNumberIcon from '../../assets/userNumberIcon.svg'
function AccountElement({ title, imgUrl, memberCount }) {
    const [mouseOverState, setMouseMoverState] = useState(false);

    return (
        <div className={container}>

            <div className={accountInfoWrapper}>
                <div className={accountImgWrapper(mouseOverState)} onMouseEnter={() => setMouseMoverState(true)} onMouseLeave={() => setMouseMoverState(false)}>
                    <img className={accountImg} src={sample} alt='accountImg' />
                </div>
                <div className={accountInfo(mouseOverState)} onMouseEnter={() => setMouseMoverState(true)} onMouseLeave={() => setMouseMoverState(false)}>
                    <div className={titleWrapper}>
                        {title}
                    </div>
                    <div className={memberCountWrapper}>
                        <img className={userNumberImg} src={userNumberIcon} alt='userNumberIcon' />
                        <span className={memberCountSpan}>{memberCount}</span>
                    </div>
                </div>
            </div>
        </div >
    );
}



const container = css``;

const accountInfoWrapper = css`
    width: 250px;
    height: 250px;
`;


const accountImgWrapper = (mouseOverState) => css`
    width: 100%;
    height: 100%;
    overflow: hidden;
    margin-bottom: 10px;
    border-radius: 10px;
    cursor: grab;
    
    img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: 0.5s;


        ${mouseOverState && 'scale: 110%;'}
    }
`;

const accountImg = css``;



const accountInfo = (mouseMoverState) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    width: 100%;
    height: 30px;
    line-height: 30px;
    padding: 0px 5px;
    bottom: 0px;
    cursor: grab;

    ${mouseMoverState && 'filter: drop-shadow(0 0 0 yellow);'}
`;

const titleWrapper = css`
    color: white;
    font-size: 18px;
`;

const memberCountWrapper = css`
    position: relative;
    top: 3px;
    color: white;
`;

const userNumberImg = css`
    width: 20px;
    margin-right: 10px;
`;

const memberCountSpan = css`
    position: relative;
    top: -5px;
`;

export default AccountElement;