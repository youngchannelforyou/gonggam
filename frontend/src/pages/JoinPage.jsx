import React from 'react';
import { css } from '@emotion/css';
import JoinForm from '../components/Join/JoinForm';

function JoinPage(props) {
    return (
        <div className={container}>
            <div>
                <JoinForm />
            </div>
        </div>
    );
}

export default JoinPage;

const container = css`
  position: absolute;
  top: 100px;
  left: 50%;
  transform: translate(-50%, 0);
`;

