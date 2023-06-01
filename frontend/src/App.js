import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { css } from '@emotion/css';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import JoinPage from './pages/JoinPage';

function App() {
  return (
    <div className={container}>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<LoginPage />} />
          <Route path='/main' element={<MainPage />} />
          <Route path='/join' element={<JoinPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

const container = css`
  height: 100%;
  background-color: #121416;
`;
