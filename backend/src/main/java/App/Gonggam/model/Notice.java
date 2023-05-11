package App.Gonggam.model;

import java.util.ArrayList;
import java.util.Date;
import java.awt.Image;
import java.util.List;

public class Notice {
    private Date Notice_day;
    private Member write_member;
    private ArrayList<Image> Notice_image = new ArrayList<Image>();

    private String Notice_writing;

    public Date getNotice_day() {
        return Notice_day;
    }

    public void setNotice_day(Date day) {
        this.Notice_day = day;
    }

    public Member getWrite_member() {
        return write_member;
    }

    public void setWrite_member(Member write_member) {
        this.write_member = write_member;
    }

    public List<Image> getNotice_image() {
        return Notice_image;
    }

    public void setNotice_image(ArrayList<Image> Notice_image) {
        this.Notice_image = Notice_image;
    }

    public void addNotice_image(Image Notice_image) {
        this.Notice_image.add(Notice_image);
    }

    public String getNotice_writing() {
        return Notice_writing;
    }

    public void setNotice_writing(String writing) {
        this.Notice_writing = writing;
    }
}
