package ru.diaproject.vkplus.json.attachments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.attachments.PollAnswer;
import ru.diaproject.vkplus.model.attachments.PollInfo;

public class PollInfoJsonHandler {
    public PollInfo parse(JSONObject jsonObject) throws JSONException {
        PollInfo info = new PollInfo();
        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setCreated(jsonObject.getInt("created"));
        info.setAnswerId(jsonObject.getInt("answer_id"));
        info.setVotes(jsonObject.getInt("votes"));
        info.setQuestion(jsonObject.getString("question"));

        JSONArray array = jsonObject.getJSONArray("answers");
        List<PollAnswer> answers = new ArrayList<>();

        for (int index= 0; index < array.length(); index++){
            JSONObject ans = array.getJSONObject(index);
            PollAnswer tempAnswer = new PollAnswer();
            tempAnswer.setId(ans.getInt("id"));
            tempAnswer.setVotes(ans.getInt("votes"));
            tempAnswer.setText(ans.getString("text"));
            tempAnswer.setRate(ans.optInt("rate",0));
            answers.add(tempAnswer);
        }

        info.setAnswers(answers);

        return info;
    }
}
