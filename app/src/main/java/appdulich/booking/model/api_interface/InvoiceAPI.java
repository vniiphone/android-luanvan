package appdulich.booking.model.api_interface;

import java.util.List;

import appdulich.booking.model.Invoice;
import appdulich.booking.payload.InvoiceRequest;
import appdulich.booking.payload.PaymentRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InvoiceAPI {

    @POST("/api/hoa-don/create")
    Call<Invoice> createInvoice(@Body InvoiceRequest invoiceRequest);

    @GET("/api/hoa-don/getAllInvoicesByUser/{userId}")
    Call<List<Invoice>> getAllInvoiceByUserId(@Path("userId") long userId);

    @GET("/api/hoa-don/getHoaDon/{userId}/{invoiceId}")
    Call<Invoice> getHoaDonByUserIdAndInvoiceId(
            @Path("userId") long userId,
            @Path("invoiceId") long invoiceId);

    @PUT("/api/payment/setPaySuccess/{invoiceId}")
    Call<Invoice> setPaySuccess(
            @Path("invoiceId") long invoiceId,
            @Body PaymentRequest paymentRequest);
}
