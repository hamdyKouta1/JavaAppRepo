/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guiproject;

import com.twilio.rest.api.v2010.account.Call;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hamdy
 *
 */
public class JDBC {

    final private String url = "jdbc:postgresql://localhost:5432/UserData";
    final private String user = "postgres";
    final private String password = "123";

    List<String> ConnectAndGet(String postgres_user) {
        String sid = null;
        String auth = null;
        List<String> myArray = new ArrayList();
        String GetSID = "SELECT * FROM data WHERE user_name = ?";

        try (
                Connection connection = DriverManager.getConnection(url, user, password); PreparedStatement prepStrSID = connection.prepareStatement(GetSID);) {
            prepStrSID.setString(1, postgres_user);

            try (ResultSet resultSet = prepStrSID.executeQuery()) {
                if (resultSet.next()) {
                    sid = resultSet.getString("SID");
                    auth = resultSet.getString("auth_token");
                    System.out.println(sid);

                } else {
                    System.out.println("No SID found for user.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        myArray.add(sid);
        myArray.add(auth);
        return myArray;
    }

    void SaveRecord(Call record) {
        String insertSql = "INSERT INTO twilio_calls "
                + "(sid, date_created, date_updated, parent_call_sid, account_sid, "
                + "to_phone_number, to_formatted_phone_number, from_phone_number, from_formatted_phone_number, "
                + "phone_number_sid, status, start_time, end_time, duration, price, price_unit, "
                + "direction, answered_by, api_version, forwarded_from, group_sid, caller_name, "
                + "queue_time, trunk_sid, uri) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = connection.prepareStatement(insertSql);) {
            preparedStatement.setString(1, String.valueOf(record.getSid()));
            preparedStatement.setString(2, String.valueOf(record.getDateCreated()));
            preparedStatement.setString(3, String.valueOf(record.getDateUpdated()));
            preparedStatement.setString(4, String.valueOf(record.getParentCallSid()));
            preparedStatement.setString(5, String.valueOf(record.getAccountSid()));
            preparedStatement.setString(6, String.valueOf(record.getPhoneNumberSid()));
            preparedStatement.setString(7, String.valueOf(record.getFromFormatted()));
            preparedStatement.setString(8, String.valueOf(record.getParentCallSid()));
            preparedStatement.setString(9, String.valueOf(record.getDirection()));
            preparedStatement.setString(10, String.valueOf(record.getPhoneNumberSid()));
            preparedStatement.setString(11, String.valueOf(record.getStatus()));
            preparedStatement.setString(12, String.valueOf(record.getStartTime()));
            preparedStatement.setString(13, String.valueOf(record.getEndTime()));
            preparedStatement.setString(14, String.valueOf(record.getDuration()));
            preparedStatement.setString(15, String.valueOf(record.getPrice()));
            preparedStatement.setString(16, String.valueOf(record.getPriceUnit()));
            preparedStatement.setString(17, String.valueOf(record.getDirection()));
            preparedStatement.setString(18, String.valueOf(record.getAnsweredBy()));
            preparedStatement.setString(19, String.valueOf(record.getApiVersion()));
            preparedStatement.setString(20, String.valueOf(record.getForwardedFrom()));
            preparedStatement.setString(21, String.valueOf(record.getGroupSid()));
            preparedStatement.setString(22, String.valueOf(record.getCallerName()));
            preparedStatement.setString(23, String.valueOf(record.getQueueTime()));
            preparedStatement.setString(24, String.valueOf(record.getTrunkSid()));
            preparedStatement.setString(25, String.valueOf(record.getUri()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void saveChatMsg(String getFrom, String getTo, String getMsg, Timestamp getTime) {
        try (
                Connection connection = DriverManager.getConnection(url, user, password)) {

            String from = getFrom;
            String to = getTo;
            String msg = getMsg;
            Timestamp timestamp = getTime;

            String insertSql = "INSERT INTO msg_data (fromport, toport , msg, attime) VALUES (?, ?, ?, ?)";

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setString(1, from);
                preparedStatement.setString(2, to);
                preparedStatement.setString(3, msg);
                preparedStatement.setTimestamp(4, timestamp);

                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void saveChatMsgUDP(String getFrom, String getTo, String getMsg, Timestamp getTime) {
        try (
                Connection connection = DriverManager.getConnection(url, user, password)) {

            String from = getFrom;
            String to = getTo;
            String msg = getMsg;
            Timestamp timestamp = getTime;

            String insertSql = "INSERT INTO msg_data_udp (fromport, toport , msg, attime) VALUES (?, ?, ?, ?)";

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setString(1, from);
                preparedStatement.setString(2, to);
                preparedStatement.setString(3, msg);
                preparedStatement.setTimestamp(4, timestamp);

                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addUser(String userName, String SID, String Auth) {
        try (
                Connection connection = DriverManager.getConnection(url, user, password)) {

            String user_name = userName;
            String sid_num = SID;
            String auth_token = Auth;

            String insertSql = "INSERT INTO data (user_name, sid , auth_token) VALUES (?, ?, ?)";

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setString(1, user_name);
                preparedStatement.setString(2, sid_num);
                preparedStatement.setString(3, auth_token);

                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
