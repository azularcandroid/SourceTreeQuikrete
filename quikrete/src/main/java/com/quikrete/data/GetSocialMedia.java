package com.quikrete.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NabeelRangari on 3/19/18.
 */

public class GetSocialMedia {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "GetSocialMedia{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}