package appdulich.booking.datalocal;

import java.util.Date;

import appdulich.booking.model.CartItem;
import appdulich.booking.model.Tour;

public  class AppUtils {
    public static String nameTour = "";
    public static int price = 0;
    public static Date beginTrip = null;
    public static Date endTrip = null;
    public static int slot = 0;
    public static String imageUrl = "";
    public static int quantity = 0;
    public static long tour_id = 0;

    public Tour tour;
    public CartItem cartItem;

    public AppUtils(Tour tour, CartItem cartItem) {
        this.tour = tour;
        this.cartItem = cartItem;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getBeginTrip() {
        return beginTrip;
    }

    public void setBeginTrip(Date beginTrip) {
        this.beginTrip = beginTrip;
    }

    public Date getEndTrip() {
        return endTrip;
    }

    public void setEndTrip(Date endTrip) {
        this.endTrip = endTrip;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTour_id() {
        return tour_id;
    }

    public void setTour_id(long tour_id) {
        this.tour_id = tour_id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public AppUtils() {
    }


}
