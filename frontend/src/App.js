import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { css } from '@emotion/css';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import JoinPage from './pages/JoinPage';
import AccountBookPage from './pages/AccountBookPage/AccountBookPage';
import Home from './pages/AccountBookPage/Home';
import Budget from './pages/AccountBookPage/Budget';
import Asset from './pages/AccountBookPage/Asset';
import Notice from './pages/AccountBookPage/Notice';
import Community from './pages/AccountBookPage/Community';
import Settings from './pages/AccountBookPage/Settings';
import AccountBook from './pages/AccountBookPage/AccountBook';

function App() {
  return (
    <div className={container}>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<LoginPage />} />
          <Route path='/main' element={<MainPage />} />
          <Route path='/join' element={<JoinPage />} />
          <Route path='/:accountName' element={<AccountBookPage />} />
          <Route path='/:accountName/home' element={<Home />} />
          <Route path='/:accountName/budget' element={<Budget />} />
          <Route path='/:accountName/asset' element={<Asset />} />
          <Route path='/:accountName/accountbook' element={<AccountBook />} />
          <Route path='/:accountName/notice' element={<Notice />} />
          <Route path='/:accountName/community' element={<Community />} />
          <Route path='/:accountName/settings' element={<Settings />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

const container = css`
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
`;
