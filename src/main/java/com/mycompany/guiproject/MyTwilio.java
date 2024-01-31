package com.mycompany.guiproject;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import java.net.URI;

public class MyTwilio {

    public String ACCOUNT_SID = null;
    public String AUTH_TOKEN = null;

    public void setAuth(String sid, String auth) {
        ACCOUNT_SID = sid;
        AUTH_TOKEN = auth;
    }

    public void SendSmS(String to, String from, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from),
                msg
        ).create();

        System.out.println(message.getSid());
    }

    public void sendCall(String to, String from, String msg) {
        JDBC saveCall = new JDBC();

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Say say = new Say.Builder(msg).build();
        VoiceResponse response = new VoiceResponse.Builder().say(say).build();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(response.toXml().toString());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        GitHubFileUpdater.uploadXML(response.toXml().toString());
        /*   try {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(response.toXml().toString());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            
            GitHubFileUpdater.uploadXML(response.toXml().toString());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }*/

        Call call;
        call = Call.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from),
                URI.create("https://raw.githubusercontent.com/hamdyKouta1/xml/main/callling.xml")
        ).setMethod(HttpMethod.GET).create();

        System.out.println(call.getSid());
        saveCall.SaveRecord(call);

    }

    public void getLogs() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        ResourceSet<Call> calls = Call.reader().limit(20).read();

        for (Call record : calls) {
            System.out.println(record);
        }
    }

}
