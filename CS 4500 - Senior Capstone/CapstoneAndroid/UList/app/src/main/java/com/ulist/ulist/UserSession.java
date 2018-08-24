package com.ulist.ulist;

/**
 * This class creates and instance of the user session, when the user logs in.
 */
public class UserSession {
    private static UserSession mInstance = null;

    public String user;

    protected UserSession(){}

    public static synchronized UserSession getInstance(){
        if(null == mInstance){
            mInstance = new UserSession();
        }
        return mInstance;
    }
}