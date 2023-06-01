import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { css } from '@emotion/css';
import LoginPage from './pages/LoginPage';

function App() {
  return (
    <div className={container}>
      <BrowserRouter>
        <Routes>
          <Route path='/*' element={<LoginPage />} />
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
