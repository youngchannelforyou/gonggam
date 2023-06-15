import React, { useEffect, useState } from 'react';
import { css } from '@emotion/css';

import RecentLogListItem from './RecentLogListItem';

function RecentLogList({ accountNumber, width, title, needTotal, dataSet }) {
    const [spendList, setSpendList] = useState([
        { date: '2023.05.02', title: '간식사업', type: 0, money: '100,235,320₩', contents: '간식 사업을 위한 식자재 예산을 사용하였습니다. 싸이버거 200개, 뭐뭐사용 100개 맙소사 그렇게 엄청난 사실이?' },
        { date: '2023.05.02', title: '간식사업', type: 1, money: '100,235,320₩', contents: '간식 사업을 위한 식자재 예산을 사용하였습니다. 싸이버거 200개, 뭐뭐사용 100개 맙소사 그렇게 엄청난 사실이?' },
        { date: '2023.05.02', title: '간식사업', type: 0, money: '100,235,320₩', contents: '간식 사업을 위한 식자재 예산을 사용하였습니다. 싸이버거 200개, 뭐뭐사용 100개 맙소사 그렇게 엄청난 사실이?' },
        { date: '2023.05.02', title: '간식사업', type: 1, money: '100,235,320₩', contents: '간식 사업을 위한 식자재 예산을 사용하였습니다. 싸이버거 200개, 뭐뭐사용 100개 맙소사 그렇게 엄청난 사실이?' },
        { date: '2023.05.02', title: '간식사업', type: 0, money: '100,235,320₩', contents: '간식 사업을 위한 식자재 예산을 사용하였습니다. 싸이버거 200개, 뭐뭐사용 100개 맙소사 그렇게 엄청난 사실이?' },
    ]);

    useEffect(() => {
        getHomeExpense()
    }, [])


    async function getHomeExpense() {
        await fetch(`http://localhost:8080/gonggam/${accountNumber}/homeexpense`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            credentials: 'include', // get cookie
        })
            .then((responseData) => responseData.json())
            .then((data) => {
                console.log(data);
                setSpendList(data.posts);
            })
    };

    return (
        <div className={container(width)}>
            <div className={recentBoxTitle}>{title}</div>
            <div className={recentItemWrapper}>
                {
                    spendList.map((item, index) => {
                        const tmpKey = index + 1;
                        return <RecentLogListItem key={tmpKey} item={item} />
                    })
                }
            </div>
            {needTotal && <div></div>}
        </div>
    );
}

export default RecentLogList;

const container = (width) => css`
    width: ${width ?? '100%'};
    height: 306px;
    position: relative;

    padding: 0px 12px;
    border-radius: 20px;
    background-color: #1C1E1F;
    border: 1px solid #252729;
    overflow: scroll;
`;
const recentBoxTitle = css`
    position: sticky;
    top: 0;
    width: 100%;
    height: 30px;
    padding: 8px 0;
    background-color: #1C1E1F;

    color: #D6D6D6;
    font-size: 13px;
    line-height: 14px;
    z-index: 100;
`;
const recentItemWrapper = css`
    display: flex;
    flex-direction: column;
    gap: 10px;

    margin-top: 15px;
    margin-bottom: 15px;
`;