package com.bms.data;

/**
 * UserInfo: a class used to store basic user information that both Admin and User use
 */
public class UserInfo {
    private final int id;
    private final String username;

    /**
     * Default constructor
     * @param id the id
     * @param username the username
     */
    public UserInfo(int id, String username){
        this.id=id;
        this.username=username;
    }

    /**
     * Id getter
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Username getter
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
