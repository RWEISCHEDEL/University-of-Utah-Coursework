package com.ulist.ulist;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class serves as an abstraction for FAQ objects that the seller and buyer and interactive with. This is used to store the FAQ information for a single
 * question and answer.
 */
public class FAQEntry {

    private String question;
    private String answer;

    private String owner_username;
    private String asker_username;

    private int product_id;
    private int faq_id;

    private boolean answered;

    public FAQEntry(JSONObject jsonObject) throws JSONException {
        question = jsonObject.getString("question");
        answer = jsonObject.getString("answer");
        asker_username = jsonObject.getString("asker_username");
        owner_username = jsonObject.getString("owner_username");
        faq_id = jsonObject.getInt("faq_id");
        product_id = jsonObject.getInt("product_id");
    }

    public String getAnswer() {
        return answer;
    }

    public String getAsker_username() {
        return asker_username;
    }

    public String getQuestion() {
        return question;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public boolean isAnswered() {
        return answered;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getFaq_id() {
        return faq_id;
    }
}
