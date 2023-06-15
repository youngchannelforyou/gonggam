import { css } from "@emotion/css";
import { useState } from "react";

function CommentsInput() {
  const [commentValue, setCommentValue] = useState('');
  const [textAreaHeight, setTextAreaHeight] = useState(60);

  function textAreaClickHandler(e) {
    const text = e.target.value
    const lines = text?.split(/\r|\r\n|\n/);
    const count = lines?.length;

    setCommentValue(text);
    if (count > 2)
      setTextAreaHeight(60 + ((count - 2) * 14));
  }

  function submitHandler(e) {
    alert('전송 되었습니다');
    setCommentValue('');
  }

  return (
    <div className={container}>
      <div className={commentsFrame(textAreaHeight)}>
        <textarea className={textArea} onChange={textAreaClickHandler} value={commentValue} placeholder="댓글을 남겨보세요." />
        <button type='submit' className={confirmButton(commentValue)} onClick={submitHandler}>전송하기</button>
      </div>
    </div>
  );
}

const container = css`
  position: relative;
  background-color: #1E1E20;
  border: 1px solid #252729;
  border-radius: 10px;
`;

const commentsFrame = (textAreaHeight) => css`
  width: 100%;
  height: ${textAreaHeight}px;
  background-color: #1E1E20;
  border-radius: 10px;
  padding: 10px 87px 10px 10px;
`;

const textArea = css`
  width: 100%;
  height: 100%;
  font-size: 12px;
  background-color: #1E1E20;
  color: #d6d6d6;
  border: none;
  resize: none
`

const confirmButton = (commentValue) => css`
  position: absolute;
  top: 10px;
  right: 10px;
  width: 70px;
  height: 40px;
  background-color: ${commentValue !== '' ? '#252729' : '#1E1E20'};
  border-radius: 10px;
  text-align: center;
  font-size: 12px;
  color: ${commentValue !== '' ? 'white' : '#bcc0c4'};
  &:hover {
    background-color: #131415;
    color: white;
  }
`;

export default CommentsInput;
