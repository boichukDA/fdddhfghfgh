package ru.diaproject.vkplus.model.attachments;

import java.util.List;

import ru.diaproject.vkplus.model.baseitems.DataItem;

public class PollInfo extends DataItem{
    private Integer created;
    private String question;

    private Integer votes;
    private Integer answerId;
    private List<PollAnswer> answers;

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public List<PollAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<PollAnswer> answers) {
        this.answers = answers;
    }
}
