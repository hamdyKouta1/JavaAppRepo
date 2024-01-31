
package com.mycompany.guiproject;

import java.util.ArrayList;
import java.util.List;

public class Action {

    public static void SMSUser(String user,String to ,String from ,String msg) {
        List<String> myListUser1 = new ArrayList();
        JDBC user1 = new JDBC();
        MyTwilio T1 = new MyTwilio();

        myListUser1.addAll(user1.ConnectAndGet(user));
        System.out.println("-------------------");

        System.out.println("Sid : " + myListUser1.get(0));
        System.out.println("auth : " + myListUser1.get(1));

        T1.setAuth(myListUser1.get(0), myListUser1.get(1));
        T1.SendSmS(to, from, msg);

    }

    public static void CallUser(String user,String to ,String from ,String msg) {
        
        List<String> myListUser2 = new ArrayList();
        JDBC user2 = new JDBC();
        MyTwilio T2 = new MyTwilio();
        
        myListUser2.addAll(user2.ConnectAndGet(user));
        System.out.println("-------------------");

        System.out.println("Sid : " + myListUser2.get(0));
        System.out.println("auth : " + myListUser2.get(1));

        T2.setAuth(myListUser2.get(0), myListUser2.get(1));
        T2.sendCall(to, from, msg);
        System.out.println("This is logs");
        T2.getLogs();

    }

  /*  public static void main(String[] args) {
        SMSUser();
        CallUser();
        
    }*/

}
