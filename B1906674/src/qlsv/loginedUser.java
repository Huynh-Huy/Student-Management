/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlsv;

/**
 *
 * @author Tan Huy
 */
public class loginedUser {

    private String username;

    public loginedUser() {
        this.username = new String();
    }
    public loginedUser(loginedUser user){
        this.username = new String(user.username);
    }
    public loginedUser(String username) {
        this.username = new String(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
