package com.example.roadmanagement.kaltara.api;

import com.example.roadmanagement.kaltara.DownloadBaru.ListDownload;
import com.example.roadmanagement.kaltara.DownloadBaru.ListDrainase;
import com.example.roadmanagement.kaltara.Interface.CekSinkronku;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.Koordinatku;
import com.example.roadmanagement.kaltara.Interface.ListJumlahSegment;
import com.example.roadmanagement.kaltara.Interface.Loginku;
import com.example.roadmanagement.kaltara.Interface.Ruasku;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface InterfaceDrainase {


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/downloadDetailExt.php")
    Call<ListDownload> downAssetExt(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg);

    //testing
    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/downloadDetail.php")
    Call<ListDownload> downloadfirst(@Field("provinsi") String provinsi,
                                     @Field("ruas") String ruas);



    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/inputSinkrondetail.php")
    Call<String> setSinkrondetail(@Field("provinsi") String provinsi,
                                  @Field("ruas") String ruas,
                                  @Field("segment") int segment,
                                  @Field("subseg") int subseg,
                                  @Field("segmentAkhir") int segmentAkhir,
                                  @Field("subsegAkhir") int subsegAkhir,
                                  @Field("detail") String detail,
                                  @Field("posisi") String posisi,
                                  @Field("lajur") String lajur,
                                  @Field("jenis") String jenis,
                                  @Field("user") String user,
                                  @Field("tanggal") String tanggal,
                                  @Field("sinkronid") int sinkronid);


    //testing
    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/inputSinkronid.php")
    Call<String> setSinkronid(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("id") String id,
                              @Field("user") String user,
                              @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/cekSinkronid.php")
    Call<CekSinkronku> cekSinkronid(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/cekSinkronUrut.php")
    Call<Integer> cekSinkronUrut(@Field("provinsi") String provinsi,
                                 @Field("ruas") String ruas);






    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/postSaluran.php")
    Call<String> setSaluran(@Field("provinsi") String provinsi,
                            @Field("ruas") String ruas,
                            @Field("segment") int segment,
                            @Field("subseg") int subseg,
                            @Field("segmentAkhir") int segmentAkhir,
                            @Field("subsegAkhir") int subsegAkhir,
                            @Field("posisi") String posisi,
                            @Field("tipe") String tipe,
                            @Field("samping") String samping,
                            @Field("penampang") String penampang,
                            @Field("dalam") float dalam,
                            @Field("lebar") float lebar,
                            @Field("tinggi") float tinggi,
                            @Field("sedimen") float sedimen,
                            @Field("konstruksi") String konstruksi,
                            @Field("kondisi") String kondisi);


    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/postBahu.php")
    Call<String> setBahu(@Field("provinsi") String provinsi,
                         @Field("ruas") String ruas,
                         @Field("segment") int segment,
                         @Field("subseg") int subseg,
                         @Field("segmentAkhir") int segmentAkhir,
                         @Field("subsegAkhir") int subsegAkhir,
                         @Field("posisi") String posisi,
                         @Field("tipe") String tipe,
                         @Field("material") String material,
                         @Field("lebar") float lebar,
                         @Field("derajat") float derajat,
                         @Field("arah") String arah,
                         @Field("kondisi") String kondisi,
                         @Field("tipeinner") String tipeinner,
                         @Field("materialinner") String materialinner,
                         @Field("lebarinner") float lebarinner);


    //testing

    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/postLane.php")
    Call<String> setLane(@Field("provinsi") String provinsi,
                         @Field("ruas") String ruas,
                         @Field("segment") int segment,
                         @Field("subseg") int subseg,
                         @Field("segmentAkhir") int segmentAkhir,
                         @Field("subsegAkhir") int subsegAkhir,
                         @Field("posisi") String posisi,
                         @Field("lajur") int lajur,
                         @Field("surface") String tipeLane,
                         @Field("lebar") float lebar,
                         @Field("derajat") float derajat,
                         @Field("arah") String arah,
                         @Field("kondisi") String kondisi);



    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/postImage.php")
    Call<String> setImage(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("segment") int segment,
                          @Field("subseg") int subsegment,
                          @Field("tipe") String tipe,
                          @Field("posisi") String posisi,
                          @Field("nama_file") String nama_file,
                          @Field("folder") String folder,
                          @Field("latitude") String latitude,
                          @Field("longtitude") String longtitude,
                          @Field("urut") int urut);


    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/getBahu.php")
    Call<DataBahu> updateBahu(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi);


    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/getSaluran.php")
    Call<DataSaluran> updateSaluran(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg,
                                    @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandro2021subseg/apidrainase/getLane.php")
    Call<DataLane> updateLane(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi,
                              @Field("lajur") String lajur);


}
