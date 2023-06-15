import { css } from "@emotion/css";
import CommentsInput from "./CommentsInput";
import CommentsList from "./CommentsList";

function Comments() {
    return (
        <div className={container}>
            {/* <CommentsList /> */}
            <CommentsInput />
        </div>
    );
}

const container = css`
  padding: 10px;
`;

export default Comments;
