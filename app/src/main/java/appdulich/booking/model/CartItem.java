package appdulich.booking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartItem  implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("user_id")
    private Long user_id;
    @SerializedName("tour_id")
    private Long tour_id;
    @SerializedName("soLuongVeDat")
    private int quantity;
    @SerializedName("status")
    private int status;

    public CartItem(Long user_id, Long tour_id, int quantity) {
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.quantity = quantity;
    }

   /* public CartItem(long lgUser_id, long tourid, int quantity) {
    }*/

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getTour_id() {
        return tour_id;
    }

    public void setTour_id(Long tour_id) {
        this.tour_id = tour_id;
    }

    public CartItem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
