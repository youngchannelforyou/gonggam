import { css, keyframes } from '@emotion/css';
import React, { useEffect, useState } from 'react';
import imgLogo from '../../assets/imgLogo.png';

function Loading(props) {
    const [loadingDots, setLoadingDots] = useState('');

    useEffect(() => {

        const interval = setInterval(() => {
            setLoadingDots(loadingDots => {
                if (loadingDots === '...') {
                    return '';
                }
                return loadingDots + '.';
            });
        }, 500);
        return () => {
            clearInterval(interval);
        };
    }, []);

    return (
        <div className={loadingWrapper}>
            <div className={loadingImg}>
                <img src={imgLogo} alt='loading' />
            </div>
            <div className={loadingText}>
                <h1>로딩중{loadingDots}</h1>
            </div>
        </div>
    );
}

export default Loading;

const loadingWrapper = css`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    
    width: 300px;
    margin: auto;

    color: white;
`;

const loadingText = css`
    width: 100%;
    margin-top: 50px;
    text-align: center;
    font-size: 30px;
    font-weight: bolder;
`;

const rotate_image = keyframes`
    100% {
        transform: rotate(360deg);
    }
`;

const loadingImg = css`
    top: 10.5px;
    right: 14.25px;
    width: 300px;
    height: 300px;
    scale: 1;
    img {
        width: 100%;
    }
    animation: ${rotate_image} 2s linear infinite;
`;