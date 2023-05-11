package App.Gonggam.model;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsageHistory {
    private Date UsageHistory_day;
    private Member write_member;
    private ArrayList<Image> UsageHistory_image = new ArrayList<Image>();

    private String UsageHistory_writing;

    BigInteger bigNumber1;

    //아래는 함수
    public Date getUsageHistory_day() {
        return UsageHistory_day;
    }

    public void setUsageHistory_day(Date day) {
        this.UsageHistory_day = day;
    }

    public Member getWrite_member() {
        return write_member;
    }

    public void setWrite_member(Member write_member) {
        this.write_member = write_member;
    }

    public List<Image> getUsageHistory_image() {
        return UsageHistory_image;
    }

    public void setUsageHistory_image(ArrayList<Image> image) {
        this.UsageHistory_image = image;
    }

    public void addUsageHistory_image(Image image) {
        this.UsageHistory_image.add(image);
    }

    public String getUsageHistory_writing() {
        return UsageHistory_writing;
    }

    public void setUsageHistory_writing(String writing) {
        this.UsageHistory_writing = writing;
    }
}
