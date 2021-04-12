package com.appterm.mydietician;

import java.io.Serializable;

public class Chat implements Serializable {
    String sender;
    String text;


    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
