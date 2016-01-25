package ru.diaproject.vkplus.news.model.attachments;


import ru.diaproject.vkplus.news.model.DataObject;

public class PollAnswer extends DataObject {
    private String text;
    private Integer votes;
    private Integer rate;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
