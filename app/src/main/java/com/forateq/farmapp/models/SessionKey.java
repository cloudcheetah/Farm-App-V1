package com.forateq.farmapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Vallejos Family on 3/1/2016.
 */
@Table(name = "SessionKey")
public class SessionKey extends Model {

    @Column(name = "session_key")
    private String session_key;

    @Column(name = "user")
    private String user;

    /**
     * use to get user
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * use to set the user
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * use to get the session_key
     * @return session_key
     */
    public String getSession_key() {
        return session_key;
    }

    /**
     * use to set the session_key
     * @param session_key
     */
    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    /**
     * use to get the active session of the current user
     * @return
     */
    public static SessionKey getSessionKeyValue(){
        List<SessionKey> sessionKeyList = new Select().from(SessionKey.class).execute();
        return sessionKeyList.get(0);
    }
}
