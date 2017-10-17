package edu.utah.cs4530.networkbattleship;

import android.content.Context;

/**
 * Created by Robert on 10/30/2016.
 */

public class NetworkManager {

    private Context context;
    private int requestCode;
    private String payload;
    private String returnInfo;
    private String GameID;
    public String playerID;

    public NetworkManager(Context context, int requestCode, String payload) {
        this.context = context;
        this.requestCode = requestCode;
        this.payload = payload;
    }


    public String getGameID() {
        return GameID;
    }

    public void setGameID(String gameID) {
        GameID = gameID;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returned) {
        this.returnInfo = returned;
    }

    public Context getContext() {

        return context;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public String getPayload() {
        return payload;
    }
}
