package appdulich.booking.ModelResponse;

import appdulich.booking.model.Tour;

public class TourResponse {
    Tour tour;
    String  errors;
    String  message;

    public TourResponse(Tour tour, String errors, String message) {
        this.tour = tour;
        this.errors = errors;
        this.message = message;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
