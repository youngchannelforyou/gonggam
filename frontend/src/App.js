import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { css } from '@emotion/css';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import JoinPage from './pages/JoinPage';
import AccountBookPage from './pages/AccountBookPage';

function App() {
  return (
    <div className={container}>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<LoginPage />} />
          <Route path='/main' element={<MainPage />} />
          <Route path='/join' element={<JoinPage />} />
          <Route path='/:personalUrl' element={<AccountBookPage />} />
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
