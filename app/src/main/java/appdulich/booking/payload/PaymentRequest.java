package appdulich.booking.payload;

import org.jetbrains.annotations.NotNull;

public class PaymentRequest {
    @NotNull
    private Long user_id;

    @NotNull
    private String paymentMethod;

    @NotNull
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(@NotNull Long user_id) {
        this.user_id = user_id;
    }

    @NotNull
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotNull String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentRequest(
            @NotNull Long user_id,
            @NotNull String paymentMethod) {
        this.user_id = user_id;
        this.paymentMethod = paymentMethod;
    }
}
