package com.ulist.ulist;

import android.content.Context;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matt on 4/18/2018.
 * This class creates the MessageListActivity, which allows the seller to message the buyer.
 */

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private GroupChannel openChannel;
    private Context context;
    private Button sendButton;
    private EditText chatbox;

    /**
     * Create the Activity with the SendBird API calls to begin a message conversation.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        context = this;

        SendBird.init("7E03DAD0-21E6-40D6-98A9-D42A911C3E65", this);

        Bundle b = this.getIntent().getExtras();
        final ArrayList<String> USER_IDS = (ArrayList<String>) b.get("user_ids");
        String USER_ID = USER_IDS.get(0);
        String THEIR_ID = USER_IDS.get(1);
        ArrayList<String> THEIR_IDS = new ArrayList<>();
        THEIR_IDS.add(THEIR_ID);
        final boolean IS_DISTINCT = true;
        final int UNIQUE_HANDLER_ID = 1;

        SendBird.connect(USER_ID, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error.
                    return;
                }

                GroupChannel.createChannelWithUserIds(USER_IDS, IS_DISTINCT, new GroupChannel.GroupChannelCreateHandler() {
                    @Override
                    public void onResult(GroupChannel groupChannel, SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                        openChannel = groupChannel;
                        PreviousMessageListQuery prevMessageListQuery = openChannel.createPreviousMessageListQuery();
                        prevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
                            @Override
                            public void onResult(List<BaseMessage> messages, SendBirdException e) {
                                if (e != null) {
                                    // Error.
                                    return;
                                }
                                Collections.reverse(messages);
                                mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
                                mMessageAdapter = new MessageListAdapter(context, messages);
                                mMessageRecycler.setLayoutManager(new LinearLayoutManager(context));
                                mMessageRecycler.setAdapter(mMessageAdapter);
                                if (messages.size() != 0)
                                    mMessageRecycler.scrollToPosition(messages.size() - 1);
                            }
                        });

                        sendButton = (Button) findViewById(R.id.button_chatbox_send);
                        chatbox = (EditText) findViewById(R.id.edittext_chatbox);

                        sendButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!sendButton.getText().toString().isEmpty()) {
                                    String toSend = chatbox.getText().toString();

                                    openChannel.sendUserMessage(toSend, new BaseChannel.SendUserMessageHandler() {
                                        @Override
                                        public void onSent(UserMessage userMessage, SendBirdException e) {
                                            if (e != null) {
                                                // Error.
                                                return;
                                            }
                                            chatbox.setText("");
                                            PreviousMessageListQuery prevMessageListQuery = openChannel.createPreviousMessageListQuery();
                                            prevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
                                                @Override
                                                public void onResult(List<BaseMessage> messages, SendBirdException e) {
                                                    if (e != null) {
                                                        // Error.
                                                        return;
                                                    }
                                                    Collections.reverse(messages);
                                                    mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
                                                    mMessageAdapter = new MessageListAdapter(context, messages);
                                                    mMessageRecycler.setLayoutManager(new LinearLayoutManager(context));
                                                    mMessageRecycler.setAdapter(mMessageAdapter);
                                                    if (messages.size() != 0)
                                                        mMessageRecycler.scrollToPosition(messages.size() - 1);
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });

                        SendBird.addChannelHandler(String.valueOf(UNIQUE_HANDLER_ID), new SendBird.ChannelHandler() {
                            @Override
                            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                                PreviousMessageListQuery prevMessageListQuery = openChannel.createPreviousMessageListQuery();
                                prevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
                                    @Override
                                    public void onResult(List<BaseMessage> messages, SendBirdException e) {
                                        if (e != null) {
                                            // Error.
                                            return;
                                        }
                                        Collections.reverse(messages);
                                        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
                                        mMessageAdapter = new MessageListAdapter(context, messages);
                                        mMessageRecycler.setLayoutManager(new LinearLayoutManager(context));
                                        mMessageRecycler.setAdapter(mMessageAdapter);
                                        if (messages.size() != 0)
                                            mMessageRecycler.scrollToPosition(messages.size() - 1);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}