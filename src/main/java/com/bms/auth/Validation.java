package com.bms.auth;

public class Validation {
    public static void usernameValidation(String username) throws Exception{
        if(username.isEmpty()) throw new Exception("Username field is empty");
        if(username.length()<8) throw new Exception("Username must be at least 8 characters long");
        if(username.contains(" ")) throw new Exception("Username cannot contain whitespace");
        if(username.length()>50) throw new Exception("Username cannot be more than 50 characters long");
    }
    public static void passwordValidation(String password,String confirm) throws Exception{
        if(password.isEmpty()) throw new Exception("Password field is empty");
        if(password.length()<5) throw new Exception("Password must be at least 5 characters long");
        if(password.contains(" ")) throw new Exception("Password cannot contain whitespace");
        if(password.length()>100) throw new Exception("Password cannot be more than 100 characters long");
        if(confirm.isEmpty()) throw new Exception("Confirm field is empty");
        if(!password.equals(confirm)) throw new Exception("Password and confirm do not match");
    }
    public static void loginValidation(String username,String password) throws Exception{
        if(username.isEmpty() || password.isEmpty()) throw new Exception("All fields are required to log in");
        if(!DBAuth.checkUsernameExists(username)) throw new Exception("Username doesn't exist");
        String dbPassword = DBAuth.getStoredPassword(username);
        if(!Password.validatePassword(password,dbPassword)) throw new Exception("Username or password are incorrect");
    }
}
