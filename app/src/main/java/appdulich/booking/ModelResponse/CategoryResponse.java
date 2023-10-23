package appdulich.booking.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appdulich.booking.model.Category;

public class CategoryResponse {
    String error;
    @SerializedName("message")
    String msg;
    List<Category> result;


    public List<Category> getResult() {
        return result;
    }

    public void setResult(List<Category> result) {
        this.result = result;
    }

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

    public CategoryResponse(String error, String msg, List<Category> result) {
        this.error = error;
        this.msg = msg;
        this.result = result;
    }
}
