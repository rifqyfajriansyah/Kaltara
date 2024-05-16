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
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DetailSegmentku;
import com.example.roadmanagement.kaltara.Interface.Downloadku;
import com.example.roadmanagement.kaltara.Interface.Koordinat;
import com.example.roadmanagement.kaltara.Interface.Koordinatku;
import com.example.roadmanagement.kaltara.Interface.ListJumlahSegment;
import com.example.roadmanagement.kaltara.Interface.Loginku;
import com.example.roadmanagement.kaltara.Interface.Ruasku;
import com.example.roadmanagement.kaltara.Interface.Segment;
import com.example.roadmanagement.kaltara.Interface.Segmentku;
import com.example.roadmanagement.kaltara.Interface.UpdateBahuku;
import com.example.roadmanagement.kaltara.Interface.UpdateLahanku;
import com.example.roadmanagement.kaltara.Interface.UpdateLaneku;
import com.example.roadmanagement.kaltara.Interface.UpdateMedianku;
import com.example.roadmanagement.kaltara.Interface.UpdateSaluranku;
import com.example.roadmanagement.kaltara.Interface.UpdateSegmentku;
import com.example.roadmanagement.kaltara.interfacedownload.DownloadDataku;

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

public interface InterfaceKaltara {

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appLog.php")
    Call<Loginku> getLogin(@Field("username") String provinsi,
                           @Field("password") String ruas);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appLogUp.php")
    Call<String> setLogUp(@Field("username") String provinsi,
                           @Field("flagLog") int value);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appLogExt.php")
    Call<String> getLogExt(@Field("username") String username);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appCekAsset.php")
    Call<String> getAssetExt(@Field("provinsi") String provinsi,
                           @Field("ruas") String ruas,
                           @Field("segment") int segment,
                           @Field("subseg") int subseg);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownAssetExt.php")
    Call<ListDownload> downAssetExt(@Field("provinsi") String provinsi,
                             @Field("ruas") String ruas,
                             @Field("segment") int segment,
                             @Field("subseg") int subseg);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownDrainExt.php")
    Call<ListDrainase> downDrainExt(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg);

    //Call<Loginku> getLogin(@QueryMap HashMap<String,String> params);
    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownRuasFull.php")
    Call<Ruasku> getRuas(@Field("noprov") String params);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownProv.php")
    Call<String> getProv(@Field("noprov") String params);

    //testing
    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownAsset.php")
    Call<ListDownload> downloadfirst(@Field("provinsi") String provinsi,
                                     @Field("ruas") String ruas);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appDownDrain.php")
    Call<ListDrainase> downloadDrainase(@Field("provinsi") String provinsi,
                                     @Field("ruas") String ruas);

    @FormUrlEncoded
    @POST("apiandro2021subseg/getJumlahSegment.php")
    Call<ListJumlahSegment> cekJumlahSegment(@Field("provinsi") String provinsi,
                                             @Field("ruas") String ruas,

                                             @Field("segment") int segment);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apivalrm/syncDetailPost.php")
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
    @POST("apiandrobaru/apiAppRM/syncIdPost.php")
    Call<String> setSinkronid(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("id") String id,
                              @Field("user") String user,
                              @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/syncDetailRuas.php")
    Call<CekSinkronku> cekSinkronid(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/syncIdCek.php")
    Call<Integer> cekSinkronUrut(@Field("provinsi") String provinsi,
                                 @Field("ruas") String ruas,
                                 @Field("flag_reset") int flag);




    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apivalrm/poLahanSet.php")
    Call<String> setLahan(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("segment") int segment,
                          @Field("subseg") int subseg,
                          @Field("segmentAkhir") int segmentAkhir,
                          @Field("subsegAkhir") int subsegAkhir,
                          @Field("posisi") String posisi,
                          @Field("tipe") String tipe,
                          @Field("tagun") String tagun,
                          @Field("panjang") Float panjang);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apivalrm/poSaluranSet.php")
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
    @POST("apiandrobaru/apiAppRM/apivalrm/poPerkerasanSet.php")
    Call<String> setPerkerasan(@Field("provinsi") String provinsi,
                            @Field("ruas") String ruas,
                            @Field("segment") int segment,
                            @Field("subseg") int subseg,
                            @Field("segmentAkhir") int segmentAkhir,
                            @Field("subsegAkhir") int subsegAkhir,
                            @Field("posisi") String posisi,
                            @Field("lebar") float lebar);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSPDSet.php")
    Call<String> setSPD(@Field("provinsi") String provinsi,
                               @Field("ruas") String ruas,
                               @Field("segment") int segment,
                               @Field("subseg") int subseg,
                               @Field("segmentAkhir") int segmentAkhir,
                               @Field("subsegAkhir") int subsegAkhir,
                               @Field("posisi") String posisi,
                               @Field("lebar") float lebar);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSPLSet.php")
    Call<String> setSPL(@Field("provinsi") String provinsi,
                               @Field("ruas") String ruas,
                               @Field("segment") int segment,
                               @Field("subseg") int subseg,
                               @Field("segmentAkhir") int segmentAkhir,
                               @Field("subsegAkhir") int subsegAkhir,
                               @Field("posisi") String posisi,
                               @Field("lebar") float lebar);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apivalrm/poBahuSet.php")
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
    @POST("apiandrobaru/apiAppRM/apivalrm/poLaneSet.php")
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
    @POST("apiandrobaru/apiAppRM/apivalrm/poSegmentSet.php")
    Call<String> setSegment(@Field("provinsi") String provinsi,
                            @Field("ruas") String ruas,
                            @Field("segment") int segment,
                            @Field("subseg") int subseg,
                            @Field("segmentAkhir") int segmentAkhir,
                            @Field("subsegAkhir") int subsegAkhir,
                            @Field("vertical") String vertikal,
                            @Field("horizontal") String horizontal,
                            @Field("tipejalan") String tipejalan,
                            @Field("lebarpvmt") float lebarpvmt,
                            @Field("grade") float grade);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apivalrm/poMedianSet.php")
    Call<String> setMedian(@Field("provinsi") String provinsi,
                           @Field("ruas") String ruas,
                           @Field("segment") int segment,
                           @Field("subseg") int subseg,
                           @Field("segmentAkhir") int segmentAkhir,
                           @Field("subsegAkhir") int subsegAkhir,
                           @Field("lebar") float lebar);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poInletSet.php")
    Call<String> setInlet(@Field("provinsi") String provinsi,
                            @Field("ruas") String ruas,
                            @Field("segment") int segment,
                            @Field("subseg") int subseg,
                            @Field("posisi") String posisi,
                            @Field("keberadaan") String keberadaan,
                            @Field("penampang") String penampang,
                            @Field("panjang") float panjang,
                            @Field("lebar") float lebar,
                            @Field("tinggi") float tinggi,
                            @Field("sedimen") float sedimen,
                            @Field("konstruksi") String konstruksi,
                            @Field("kondisi") String kondisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poMinletSet.php")
    Call<String> setMinlet(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("segment") int segment,
                          @Field("subseg") int subseg,
                          @Field("posisi") String posisi,
                          @Field("keberadaan") String keberadaan,
                          @Field("penampang") String penampang,
                          @Field("panjang") float panjang,
                          @Field("lebar") float lebar,
                          @Field("tinggi") float tinggi,
                          @Field("sedimen") float sedimen,
                          @Field("konstruksi") String konstruksi,
                          @Field("kondisi") String kondisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poOutletSet.php")
    Call<String> setOutlet(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("segment") int segment,
                          @Field("subseg") int subseg,
                          @Field("posisi") String posisi,
                          @Field("keberadaan") String keberadaan,
                          @Field("penampang") String penampang,
                          @Field("diameter") float diameter,
                          @Field("lebar") float lebar,
                          @Field("tinggi") float tinggi,
                          @Field("konstruksi") String konstruksi,
                          @Field("kondisi") String kondisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poGorongSet.php")
    Call<String> setGorong(@Field("provinsi") String provinsi,
                           @Field("ruas") String ruas,
                           @Field("segment") int segment,
                           @Field("subseg") int subseg,
                           @Field("posisi") String posisi,
                           @Field("keberadaan") String keberadaan,
                           @Field("penampang") String penampang,
                           @Field("diameter") float diameter,
                           @Field("lebar") float lebar,
                           @Field("tinggi") float tinggi,
                           @Field("konstruksi") String konstruksi,
                           @Field("kondisi") String kondisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poLerengSet.php")
    Call<String> setLereng(@Field("provinsi") String provinsi,
                           @Field("ruas") String ruas,
                           @Field("segment") int segment,
                           @Field("subseg") int subseg,
                           @Field("posisi") String posisi,
                           @Field("keberadaan") String keberadaan,
                           @Field("penampang") String penampang,
                           @Field("lebar") float lebar,
                           @Field("tinggi") float tinggi,
                           @Field("sedimen") float sedimen,
                           @Field("kondisi") String kondisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/appCoor.php")
    Call<Koordinatku> getLokasiRuas(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/postImage.php")
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

    @Multipart
    @POST("uploadfoto.php")
    Call<String> uploadFile(@Part MultipartBody.Part file, @Part("posisi") RequestBody name);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poBahuGet.php")
    Call<DataBahu> updateBahu(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poLahanGet.php")
    Call<DataLahan> updateLahan(@Field("provinsi") String provinsi,
                                @Field("ruas") String ruas,
                                @Field("segment") int segment,
                                @Field("subseg") int subseg,
                                @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSaluranGet.php")
    Call<DataSaluran> updateSaluran(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg,
                                    @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poPerkerasanGet.php")
    Call<DataInletMedian> updatePerkerasan(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg,
                                    @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSPDGet.php")
    Call<DataSpDalam> updateSPD(@Field("provinsi") String provinsi,
                                @Field("ruas") String ruas,
                                @Field("segment") int segment,
                                @Field("subseg") int subseg,
                                @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSPLGet.php")
    Call<DataSpLuar> updateSPL(@Field("provinsi") String provinsi,
                               @Field("ruas") String ruas,
                               @Field("segment") int segment,
                               @Field("subseg") int subseg,
                               @Field("posisi") String posisi);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poLaneGet.php")
    Call<DataLane> updateLane(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi,
                              @Field("lajur") String lajur);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poMedianGet.php")
    Call<DataMedian> updateMedian(@Field("provinsi") String provinsi,
                                  @Field("ruas") String ruas,
                                  @Field("segment") int segment,
                                  @Field("subseg") int subseg);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poSegmentGet.php")
    Call<DataSegmen> updateSegment(@Field("provinsi") String provinsi,
                                   @Field("ruas") String ruas,
                                   @Field("segment") int segment,
                                   @Field("subseg") int subseg);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poInletGet.php")
    Call<DataInletTrotoar> updateInlet(@Field("provinsi") String provinsi,
                                         @Field("ruas") String ruas,
                                         @Field("segment") int segment,
                                         @Field("subseg") int subseg,
                                       @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poMinletGet.php")
    Call<DataInletMedian> updateMinlet(@Field("provinsi") String provinsi,
                                       @Field("ruas") String ruas,
                                       @Field("segment") int segment,
                                       @Field("subseg") int subseg,
                                       @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poOutletGet.php")
    Call<DataOutlet> updateOutlet(@Field("provinsi") String provinsi,
                                 @Field("ruas") String ruas,
                                 @Field("segment") int segment,
                                 @Field("subseg") int subseg,
                                  @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poGorongGet.php")
    Call<DataCrossDrain> updateGorong(@Field("provinsi") String provinsi,
                                     @Field("ruas") String ruas,
                                     @Field("segment") int segment,
                                     @Field("subseg") int subseg,
                                      @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/poLerengGet.php")
    Call<DataDrainase> updateLereng(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg,
                                    @Field("posisi") String posisi);



    //nanti dulu

    @FormUrlEncoded
    @POST("apiandro2021/UndLahan.php")
    Call<String> setLahanUnd(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("id") String segment,
                          @Field("posisi") String posisi,
                          @Field("tipe") String tipe,
                          @Field("tagun") String tagun,
                          @Field("panjang") Float panjang);

    @FormUrlEncoded
    @POST("apiandro2021/UndSaluran.php")
    Call<String> setSaluranUnd(@Field("provinsi") String provinsi,
                            @Field("ruas") String ruas,
                            @Field("id") String segment,
                            @Field("posisi") String posisi,
                            @Field("tipe") String tipe,
                            @Field("lebar") float lebar,
                            @Field("panjang") float panjang);


    @FormUrlEncoded
    @POST("apiandro2021/UndBahu.php")
    Call<String> setBahuUnd(@Field("provinsi") String provinsi,
                         @Field("ruas") String ruas,
                         @Field("id") String segment,
                         @Field("posisi") String posisi,
                         @Field("tipe") String tipe,
                         @Field("lebar") float lebar,
                        @Field("tipeinner") String tipeinner,
                        @Field("lebarinner") float lebarinner);


    @FormUrlEncoded
    @POST("apiandro2021/UndLane.php")
    Call<String> setLaneUnd(@Field("provinsi") String provinsi,
                         @Field("ruas") String ruas,
                         @Field("id") String segment,
                         @Field("posisi") String posisi,
                         @Field("tipe") String tagun,
                         @Field("surface") String tipe,
                         @Field("panjang") float panjang);

    @FormUrlEncoded
    @POST("apiandro2021/UndMedian.php")
    Call<String> setMedianUnd(@Field("provinsi") String provinsi,
                           @Field("ruas") String ruas,
                           @Field("id") String segment,
                           @Field("tipe") String tipe,
                           @Field("lebar") float panjang);

    @FormUrlEncoded
    @POST("apiandro2021/UndImage.php")
    Call<String> setImageUnd(@Field("provinsi") String provinsi,
                          @Field("ruas") String ruas,
                          @Field("id") int segment,
                          @Field("tipe") String tipe,
                          @Field("posisi") String posisi,
                          @Field("nama_file") String nama_file,
                          @Field("folder") String folder,
                          @Field("latitude") String latitude,
                          @Field("longtitude") String longtitude,
                          @Field("urut") int urut);


    //Drainase


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/downloadDetailExt.php")
    Call<ListDownload> downAssetExtDrainase(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/downloadDetail.php")
    Call<ListDownload> downloadfirstDrainase(@Field("provinsi") String provinsi,
                                     @Field("ruas") String ruas);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/inputSinkronDetail.php")
    Call<String> setSinkrondetailDrainase(@Field("provinsi") String provinsi,
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
    @POST("apiandrobaru/apiAppRM/apidrainase/inputSinkronId.php")
    Call<String> setSinkronidDrainase(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("id") String id,
                              @Field("user") String user,
                              @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/cekSinkronId.php")
    Call<CekSinkronku> cekSinkronidDrainase(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/cekSinkronUrut.php")
    Call<Integer> cekSinkronUrutDrainase(@Field("provinsi") String provinsi,
                                        @Field("ruas") String ruas,
                                         @Field("flag_reset") int flag);






    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/postSaluran.php")
    Call<String> setSaluranDrainase(@Field("provinsi") String provinsi,
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
    @POST("apiandrobaru/apiAppRM/apidrainase/postBahu.php")
    Call<String> setBahuDrainase(@Field("provinsi") String provinsi,
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
    @POST("apiandrobaru/apiAppRM/apidrainase/postLane.php")
    Call<String> setLaneDrainase(@Field("provinsi") String provinsi,
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
    @POST("apiandrobaru/apiAppRM/apidrainase/postImage.php")
    Call<String> setImageDrainase(@Field("provinsi") String provinsi,
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
    @POST("apiandrobaru/apiAppRM/apidrainase/getBahu.php")
    Call<DataBahu> updateBahuDrainase(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi);


    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/getSaluran.php")
    Call<DataSaluran> updateSaluranDrainase(@Field("provinsi") String provinsi,
                                    @Field("ruas") String ruas,
                                    @Field("segment") int segment,
                                    @Field("subseg") int subseg,
                                    @Field("posisi") String posisi);

    @FormUrlEncoded
    @POST("apiandrobaru/apiAppRM/apidrainase/getLane.php")
    Call<DataLane> updateLaneDrainase(@Field("provinsi") String provinsi,
                              @Field("ruas") String ruas,
                              @Field("segment") int segment,
                              @Field("subseg") int subseg,
                              @Field("posisi") String posisi,
                              @Field("lajur") String lajur);


}
