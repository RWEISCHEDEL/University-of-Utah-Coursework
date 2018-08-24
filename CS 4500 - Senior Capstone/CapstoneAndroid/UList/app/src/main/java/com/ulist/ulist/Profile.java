package com.ulist.ulist;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt on 4/18/2018.
 * This class serves as an abstraction for all the profiles, both the user and all sellers.
 */

class Profile {

    private final String fullname;
    private final String username;
    private final String email;
    private final String umail;
    private final String image_names;

    public Profile(JSONObject jsonObject) throws JSONException {
         username = jsonObject.getString("username");
         fullname = jsonObject.getString("fullname");
         email = jsonObject.getString("email");
         umail = jsonObject.getString("umail");
         image_names = jsonObject.getString("image_names");
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUmail() {
        return umail;
    }

    public String getImage_names() {
        return image_names;
    }
}
