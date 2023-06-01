import React from 'react';
import Logo from '../assets/gonggamLogoRemove.png'
import Logo2 from '../assets/logo.png'
function MainPage(props) {
    return (
        <div>
            <img src={Logo} alt='draw' />
            <img src={Logo2} alt='img' />
        </div>
    );
}

export default MainPage;