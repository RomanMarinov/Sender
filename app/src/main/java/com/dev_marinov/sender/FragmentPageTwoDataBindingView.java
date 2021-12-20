package com.dev_marinov.sender;

public class FragmentPageTwoDataBindingView {

    String tv_to, edt_email_address, tv_theme, edt_theme, tv_message, edt_message, bt_send_email;

    public FragmentPageTwoDataBindingView(String tv_to, String edt_email_address, String tv_theme,
                                          String edt_theme, String tv_message, String edt_message, String bt_send_email) {
        this.tv_to = tv_to;
        this.edt_email_address = edt_email_address;
        this.tv_theme = tv_theme;
        this.edt_theme = edt_theme;
        this.tv_message = tv_message;
        this.edt_message = edt_message;
        this.bt_send_email = bt_send_email;
    }

    public String getTv_to() {
        return tv_to;
    }

    public void setTv_to(String tv_to) {
        this.tv_to = tv_to;
    }

    public String getBt_send_email() {
        return bt_send_email;
    }

    public void setBt_send_email(String bt_send_email) {
        this.bt_send_email = bt_send_email;
    }

    public String getEdt_email_address() {
        return edt_email_address;
    }

    public void setEdt_email_address(String edt_email_address) {
        this.edt_email_address = edt_email_address;
    }

    public String getTv_theme() {
        return tv_theme;
    }

    public void setTv_theme(String tv_theme) {
        this.tv_theme = tv_theme;
    }

    public String getEdt_theme() {
        return edt_theme;
    }

    public void setEdt_theme(String edt_theme) {
        this.edt_theme = edt_theme;
    }

    public String getTv_message() {
        return tv_message;
    }

    public void setTv_message(String tv_message) {
        this.tv_message = tv_message;
    }

    public String getEdt_message() {
        return edt_message;
    }

    public void setEdt_message(String edt_message) {
        this.edt_message = edt_message;
    }
}
