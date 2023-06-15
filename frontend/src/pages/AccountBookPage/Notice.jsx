import React from 'react';
import AccountBookWrapper from '../../Wrapper/AccountBookWrapper';
import { css } from '@emotion/css';
import Board from '../../components/PersonalAcountBook/Board/Board';

function Notice() {
    const postList = [
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' },
        { title: '안녕하세요 이것은 랜덤글입니다.', auth: '홍길동', date: '2023.05.25' }
    ];

    return (
        <AccountBookWrapper>
            <Board title='공지사항' postList={postList} />
        </AccountBookWrapper>
    );
};

export default Notice;
