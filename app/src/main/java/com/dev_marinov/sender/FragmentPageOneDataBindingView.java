package com.dev_marinov.sender;

public class FragmentPageOneDataBindingView {

    public String edtPhone, edtMessage, btSend;


    public String getEdtPhone() {
        return edtPhone;
    }

    public void setEdtPhone(String edtPhone) {
        this.edtPhone = edtPhone;
    }

    public String getEdtMessage() {
        return edtMessage;
    }

    public void setEdtMessage(String edtMessage) {
        this.edtMessage = edtMessage;
    }

    public String getBtSend() {
        return btSend;
    }

    public void setBtSend(String btSend) {
        this.btSend = btSend;
    }

    public FragmentPageOneDataBindingView(String edtPhone, String edtMessage, String btSend) {
        this.edtPhone = edtPhone;
        this.edtMessage = edtMessage;
        this.btSend = btSend;
    }
}

