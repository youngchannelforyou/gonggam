package App.Gonggam.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Notice {
    private long num;
    private String member;
    private String date;
    private String title;
    private String text;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.date = dateFormat.format(date2);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
