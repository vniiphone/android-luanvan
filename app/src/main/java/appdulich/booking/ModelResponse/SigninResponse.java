package appdulich.booking.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class SigninResponse {
    String error;
    @SerializedName("message")
    String msg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SigninResponse(String error, String msg) {
        this.error = error;
        this.msg = msg;
    }
}
