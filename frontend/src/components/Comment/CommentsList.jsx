import { css } from "@emotion/css";

function CommentsList(props) {
    return (
        <div className={container}>
            <h3 className={contentsListTitle}>방명록</h3>
        </div>
    );
}

const container = css`
    // border: 1px solid #dae1e6;
    width: 100%;
    height: 100px;
    margin-bottom: 10px;
`;

const contentsListTitle = css`
  height: 25px;
  font-size: 14px;
  font-weight: 700;
  line-height: 1.6;
  padding: 0 0 4px 12px;
  border-bottom: 2.5px solid rgba(0, 0, 0, 1);
`;

export default CommentsList;