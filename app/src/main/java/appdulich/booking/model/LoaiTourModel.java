package appdulich.booking.model;

import java.util.List;

public class LoaiTourModel {
    boolean success;
    String message;
    List<LoaiTour> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LoaiTour> getResult() {
        return result;
    }

    public void setResult(List<LoaiTour> result) {
        this.result = result;
    }
}
