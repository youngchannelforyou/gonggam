import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

function Board({ title, postList, accountNumber }) {
  const [contentsList, setContentsList] = useState([]);

  useEffect(() => {
    if (!accountNumber) return;
    if (!postList) return;

    const fetchData = async () => {
      const contentsArr = [];
      for (const item of postList) {
        const url = `http://localhost:8080/gonggam/${accountNumber}/${title === '커뮤니티' ? 'communitypeed' : 'noticepeed'}/${item.Num}`;
        const response = await fetch(url, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
          credentials: 'include',
        });
        const data = await response.json();
        const dataTitle = title === '커뮤니티' ? 'community' : 'notice';
        const tmpObj = data[dataTitle];
        contentsArr[item.Num] = tmpObj.text;
      }
      setContentsList(contentsArr);
    };

    fetchData();
  }, [accountNumber, postList, title]);

  return (
    <div className={container}>
      <div className={writingHeader}>
        <div className={writingTitle}>{title}</div>
        <div className={searchBox}>
          <input className={searchInput} placeholder='게시글 찾아보기' />
          <button className={searchButton}>검색</button>
        </div>
      </div>
      <div className={devideBar} />
      <div className={writingListWrapper}>
        {postList.map((item, idx) => {
          const tmpKey = idx + 1;
          return (
            <div className={contentsWrapper} key={tmpKey}>
              <div className={writingItemWrapper}>
                <div className={writingItemTitle}>
                  <a href={`communitypeed/${item.Num}`}>{item.Title}</a>
                </div>
                <div className={authAndDate}>
                  <div className={writingItemAuth}>
                    <a href={`communitypeed/${item.Num}`}>{item.Member}</a>
                  </div>
                  <div className={writingItemDate}>
                    <a href={`communitypeed/${item.Num}`}>{item.Date.replace(/-/g, '.')}</a>
                  </div>
                </div>
              </div>
              <div className={writingItemContent}>
                <a href={`communitypeed/${item.Num}`}>{contentsList[item.Num] || <>불러오는 중...</>}</a>
              </div>
            </div>
          );
        })}
      </div>
      <div>

      </div>
    </div>
  );
}

export default Board;

const container = css`
  width: 100%;
`;

const writingHeader = css`
  display: flex;
  justify-content: space-between;
`;

const writingTitle = css`
  height: 56px;
  color: #cacaca;
  font-size: 20px;
  font-weight: bold;
  line-height: 56px;
`;

const searchBox = css`
  display: flex;
  align-items: center;
  height: 56px;
`;

const searchInput = css`
  width: 200px;
  height: 30px;
  padding: 8px 12px;
  color: white;
  background-color: #121416;
  border: 1px solid #404040;
`;

const searchButton = css`
  width: 50px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #d6d6d6;
  background-color: #404040;
`;

const devideBar = css`
  width: 100%;
  height: 1px;
  border: 1px solid #cacaca;
`;

const writingListWrapper = css`
  width: 100%;
  margin-top: 5px;
  margin-bottom: 10px;
  border-collapse: collapse;
  color: #d6d6d6;
  font-size: 14px;
  font-weight: 400;
`;

const contentsWrapper = css`
  width: 100%;
  border-bottom: 1px solid #404040;
`;

const writingItemWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 40px;
  line-height: 1.6;
  text-overflow: ellipsis;
`;

const writingItemTitle = css`
  width: 709px;
  padding: 8px 4px 8px 14px;
`;

const authAndDate = css`
  display: flex;
  width: 302px;
  text-align: right;
`;

const writingItemAuth = css`
  width: 209px;
  padding: 8px 4px 8px 14px;
`;

const writingItemDate = css`
  width: 92px;
  padding: 8px 4px 8px 14px;
  text-align: right;
`;

const writingItemContent = css`
  width: 100%;
  padding: 8px 4px 20px 14px;
  color: #9c9c9c;
`;
