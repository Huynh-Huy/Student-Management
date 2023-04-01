/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package qlsv;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Admin
 */
public class DBConnect {

    public static Connection getConnection (){
        Connection cons = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String Url = "jdbc:sqlserver://DESKTOP-CHQ1FG5\\SQLEXPRESS:1433;" +
                "databaseName=Demo;user=sa;password=123456789";
            
            cons = DriverManager.getConnection(Url);
        } catch (Exception e){
            e.printStackTrace();
        }
        return cons;
    }
    public static void main(String[] args) {
        System.out.println(getConnection());
    }
    
}
