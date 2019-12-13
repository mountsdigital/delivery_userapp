package com.mounts.lenovo.delivery3.api;

import androidx.transition.Slide;

import com.mounts.lenovo.delivery3.response.AddOrderInfo;
import com.mounts.lenovo.delivery3.response.DetailResponse;
import com.mounts.lenovo.delivery3.response.GetServiceDetails;
import com.mounts.lenovo.delivery3.response.GetServiceList;
import com.mounts.lenovo.delivery3.response.GetTown;
import com.mounts.lenovo.delivery3.response.LoginResponse;
import com.mounts.lenovo.delivery3.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<LoginResponse> login(@Field("phone_number") String phoneNumber,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/user/register")
    Call<RegisterResponse> register(@Field("name") String name,
                                    @Field("phone_number") String phoneNumber,
                                    @Field("password") String password,
                                    @Field("password_confirmation") String confirmPassword);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/user/order/create")
    //@Headers("Content-Type: application/json")
    Call<AddOrderInfo> fullOrderInformation(@Field("user_city") String usercity,
                                            @Field("name") String ordername,
                                            @Field("weight") int estimatedweight,
                                            @Field("receiver_name") String edtname,
                                            @Field("receiver_phone_number") String edtphone,
                                            @Field("receiver_address") String edtaddress,
                                            @Field("receiver_city") String city,
                                            @Field("order_prepaid") boolean orderprepaidcheckbox,
                                            @Field("order_postpaid") boolean orderpostpaidcheckbox,
                                          //  @Field("sessionId") String sessionId,
                                            @Field("same_city") int same_city,
                                            @Field("user_lat") double user_lat,
                                            @Field("user_lng") double user_lng,
                                            @Field("receiver_lat") double receiver_lat,
                                            @Field("receiver_lng") double receiver_lng);

    @FormUrlEncoded
    @POST("/api/user/order/create")
    Call<AddOrderInfo> orderInformation(@Field("name") String ordername,
                                        @Field("weight") String estimatedweight,
                                        @Field("receiver_name") String edtname,
                                        @Field("receiver_phone_number") String edtphone,
                                        @Field("receiver_address") String edtaddress,
                                        @Field("receiver_city") String spinnercity,
                                        @Field("order_prepaid") boolean orderprepaidcheckbox,
                                        @Field("order_postpaid") boolean orderpostpaidcheckbox);

    @FormUrlEncoded
    @POST("/api/user/order/create")
    Call<AddOrderInfo> orderInformation(@Field("name") String ordername,
                                        @Field("weight") String estimatedweight,
                                        @Field("receiver_name") String edtname,
                                        @Field("receiver_phone_number") String edtphone,
                                        @Field("receiver_address") String edtaddress,
                                        @Field("receiver_city") String spinnercity);

    @GET("/api/user/offices/get_towns") // /api/user/offices/get_towns
    Call<GetTown> getOfficeTown();

    @FormUrlEncoded
    @POST("/api/user/detail")
    Call<DetailResponse> detail(@Field("sessionId") String sessionId);

    @GET("/api/user/adds_on_service/list?")
    Call<GetServiceList> getServiceList();

    @GET("/user/adds_on_service/detail/{{id}}")
    Call<GetServiceDetails> getServiceDetail();
}
