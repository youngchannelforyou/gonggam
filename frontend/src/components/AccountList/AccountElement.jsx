import React, { useState } from 'react';
import { css } from '@emotion/css';
import sample from '../../assets/sampleImg.jpg'
import userNumberIcon from '../../assets/userNumberIcon.svg'
import { useNavigate } from 'react-router-dom';
function AccountElement({ title, imgUrl, memberCount }) {
    const [mouseOverState, setMouseMoverState] = useState(false);
    const [isButtonClicked, setIsButtonClicked] = useState(false);

    const movePage = useNavigate();

    function moveAccountPage() {
        movePage('/account8/home');
    }

    return (
        <div className={container}>

            <div className={accountInfoWrapper}>
                <button className={accountImgWrapper(mouseOverState)} onClick={() => moveAccountPage()} onMouseEnter={() => setMouseMoverState(true)} onMouseLeave={() => setMouseMoverState(false)}>
                    <img className={accountImg} src={sample} alt='accountImg' />
                </button>
                <button className={accountInfo(mouseOverState)} onClick={() => moveAccountPage()} onMouseEnter={() => setMouseMoverState(true)} onMouseLeave={() => setMouseMoverState(false)}>
                    <div className={titleWrapper}>
                        {title}
                    </div>
                    <div className={memberCountWrapper}>
                        <img className={userNumberImg} src={userNumberIcon} alt='userNumberIcon' />
                        <span className={memberCountSpan}>{memberCount}</span>
                    </div>
                </button>
            </div>
        </div >
    );
}



const container = css``;

const accountInfoWrapper = css`
    width: 187.5px;
    height: 187.5px;
`;


const accountImgWrapper = (mouseOverState) => css`
    width: 100%;
    height: 100%;
    overflow: hidden;
    margin-bottom: 7.5px;
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
    height: 22.5px;
    line-height: 22.5px;
    padding: 0px 3.75px;
    bottom: 0px;
    cursor: grab;

    ${mouseMoverState && 'filter: drop-shadow(0 0 0 yellow);'}
`;

const titleWrapper = css`
    color: white;
    font-size: 13.5px;
`;

const memberCountWrapper = css`
    position: relative;
    top: 2.25px;
    font-size: 13.5px;
    color: white;
`;

const userNumberImg = css`
    width: 15px;
    margin-right: 7.5px;
`;

const memberCountSpan = css`
    position: relative;
    top: -3.75px;
`;

export default AccountElement;