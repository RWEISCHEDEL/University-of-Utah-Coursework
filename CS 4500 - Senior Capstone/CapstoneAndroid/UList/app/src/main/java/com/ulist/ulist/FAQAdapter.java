package com.ulist.ulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class severs as the List Adapter for the FAQ Section in the Product Page. It allows the list view to display more than just text, it allows for rating bars
 * a question and answer and the seller username.
 */
public class FAQAdapter extends ArrayAdapter<FAQEntry> {

    private ArrayList<FAQEntry> faqs;
    private Context bContext;

    public FAQAdapter(Context context, ArrayList<FAQEntry> faqs) {
        super(context, R.layout.activity_faq_listview, faqs);
        bContext = context;
        this.faqs = faqs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(bContext).inflate(R.layout.activity_faq_listview,parent,false);

        FAQEntry currentFAQ = faqs.get(position);

        TextView questioner = (TextView) listItem.findViewById(R.id.textViewQuestionTitle);
        questioner.setText(currentFAQ.getAsker_username() + " asks:");

        TextView question = (TextView) listItem.findViewById(R.id.questionTextView);
        question.setText(currentFAQ.getQuestion());

        if (currentFAQ.getAnswer() != null && !currentFAQ.getAnswer().equals("null")) {
            TextView answerer = (TextView) listItem.findViewById(R.id.textViewAnswerTitle);
            answerer.setText(currentFAQ.getOwner_username() + " answered:");

            TextView answer = (TextView) listItem.findViewById(R.id.answerTextView);
            answer.setText(currentFAQ.getAnswer());
        }
        else {
            TextView answerer = (TextView) listItem.findViewById(R.id.textViewAnswerTitle);
            answerer.setText(currentFAQ.getOwner_username() + "has not answered:");

            TextView answer = (TextView) listItem.findViewById(R.id.answerTextView);
            answer.setText("");
        }

        return listItem;
    }
}
