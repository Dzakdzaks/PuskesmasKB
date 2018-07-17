package com.developer.kb.kb.Retrofit;

import com.developer.kb.kb.Grafik.response.ResponseGrafikMetode;
import com.developer.kb.kb.Grafik.response.ResponseGrafikStatus;
import com.developer.kb.kb.Laporan.response.ResponseKartu;
import com.developer.kb.kb.Laporan.response.ResponseLaporanLayanan;
import com.developer.kb.kb.Pasien.response.ResponseSearchPasien;
import com.developer.kb.kb.Petugas.response.ResponseKader;
import com.developer.kb.kb.Layanan.response.ResponseAddLayanan;
import com.developer.kb.kb.Layanan.response.ResponseLayanan;
import com.developer.kb.kb.Layanan.response.ResponsePasienByPos;
import com.developer.kb.kb.Layanan.response.ResponsePosisiRahim;
import com.developer.kb.kb.Layanan.response.ResponseStatus;
import com.developer.kb.kb.Layanan.response.ResponseTumor;
import com.developer.kb.kb.Login.response.ResponseLogin;
import com.developer.kb.kb.Login.users.ResponseUsersKaderGet;
import com.developer.kb.kb.Pasien.response.ResponseAddPasien;
import com.developer.kb.kb.Pasien.response.ResponseMetode;
import com.developer.kb.kb.Pasien.response.ResponsePasien;
import com.developer.kb.kb.Posyandu.response.ResponsePosyandu;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    //USERS===============================================
    @FormUrlEncoded
    @POST("users_login.php")
    Call<ResponseLogin> requestlogin(
                    @Field("username") String uName,
                    @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("petugas_get.php")
    Call<ResponseUsersKaderGet> getKader(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("bidan_get.php")
    Call<ResponseUsersKaderGet> getBidan(
                    @Field("id_users") String id
            );

    @FormUrlEncoded
    @POST("kader_get.php")
    Call<ResponseUsersKaderGet> getPet(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("petugas_add.php")
    Call<ResponseUsersKaderGet> addKader(
            @Field("id_posyandu") String as,
            @Field("nama") String asd,
            @Field("nip") String asds,
            @Field("username") String asdsss,
            @Field("password") String asdssss,
            @Field("alamat") String asdsssss,
            @Field("nomor_telpon") String asdssssss
    );

    @FormUrlEncoded
    @POST("bidan_add.php")
    Call<ResponseUsersKaderGet> addBidan(
            @Field("nama") String asd,
            @Field("nip") String asds,
            @Field("username") String asdsss,
            @Field("password") String asdssss,
            @Field("alamat") String asdsssss,
            @Field("nomor_telpon") String asdssssss
    );

    @FormUrlEncoded
    @POST("kader_add.php")
    Call<ResponseUsersKaderGet> addPet(
            @Field("nama") String asd,
            @Field("nip") String asds,
            @Field("username") String asdsss,
            @Field("password") String asdssss,
            @Field("alamat") String asdsssss,
            @Field("nomor_telpon") String asdssssss
    );

    @FormUrlEncoded
    @POST("petugas_delete.php")
    Call<ResponseKader> deleteKader(
            @Field("id_users") String idPasien
    );

    @FormUrlEncoded
    @POST("bidan_delete.php")
    Call<ResponseKader> deleteBidan(
            @Field("id_users") String idPasien
    );

    @FormUrlEncoded
    @POST("petugas_edit.php")
    Call<ResponseKader> editKader(
            @Field("id_users") String id,
            @Field("id_posyandu") String as,
            @Field("nama") String asd,
            @Field("nip") String asds,
            @Field("username") String asdsss,
            @Field("password") String asdssss,
            @Field("alamat") String asdsssss,
            @Field("nomor_telpon") String asdssssss
    );

    @FormUrlEncoded
    @POST("bidan_edit.php")
    Call<ResponseKader> editBidan(
            @Field("id_users") String id,
            @Field("id_posyandu") String as,
            @Field("nama") String asd,
            @Field("nip") String asds,
            @Field("username") String asdsss,
            @Field("password") String asdssss,
            @Field("alamat") String asdsssss,
            @Field("nomor_telpon") String asdssssss
    );

    //PASIEN==============================================
    @FormUrlEncoded
    @POST("pasien_get.php")
    Call<ResponsePasien> getPasien(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("pasien_add.php")
    Call<ResponseAddPasien> addPasien(
            @Field("id_pasien") String idPasien,
            @Field("id_pos") String idPos,
            @Field("nama") String nama,
            @Field("no_telp") String no,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("umur") String umur,
            @Field("alamat") String alamat,
            @Field("pendidikan") String pendidikan,
            @Field("pekerjaan") String pekerjaan,
            @Field("jumlah_anak") String jumlah_anak,
            @Field("hamil") String hamil,
            @Field("gakin") String gakin,
            @Field("pus4t") String pus4t,
            @Field("metode") String metode
    );

    @FormUrlEncoded
    @POST("pasien_edit.php")
    Call<ResponseAddPasien> editPasien(
                    @Field("id_pasien") String idPasien,
                    @Field("id_pos") String idPos,
                    @Field("nama") String nama,
                    @Field("no_telp") String no,
                    @Field("tgl_lahir") String tgl_lahir,
                    @Field("umur") String umur,
                    @Field("alamat") String alamat,
                    @Field("pendidikan") String pendidikan,
                    @Field("pekerjaan") String pekerjaan,
                    @Field("jumlah_anak") String jumlah_anak,
                    @Field("hamil") String hamil,
                    @Field("gakin") String gakin,
                    @Field("pus4t") String pus4t,
                    @Field("metode") String metode
            );

    @FormUrlEncoded
    @POST("pasien_delete.php")
    Call<ResponseAddPasien> deletePasien(
            @Field("id_pasien") String idPasien
    );

    @FormUrlEncoded
    @POST("pasienbypos.php")
    Call<ResponsePasienByPos> pasienByPosGet(
            @Field("id_users") String idUsers,
            @Field("id_pos") String idPos
    );



    //POSYANDU==============================================
    @FormUrlEncoded
    @POST("posyandu_get.php")
    Call<ResponsePosyandu> getPosyandu(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("posyandu_delete.php")
    Call<ResponsePosyandu> deletePosyandu(
            @Field("id_pos") String idPasien
    );

    @FormUrlEncoded
    @POST("posyandu_edit.php")
    Call<ResponsePosyandu> editPosyandu(
            @Field("id_pos") String idPasien,
            @Field("id_petugas") String as,
            @Field("nama_pos") String asd,
            @Field("alamat_pos") String asds,
            @Field("no_telp_pos") String asdss
    );

    @FormUrlEncoded
    @POST("posyandu_add.php")
    Call<ResponsePosyandu> addPosyandu(
            @Field("id_petugas") String as,
            @Field("nama_pos") String asd,
            @Field("alamat_pos") String asds,
            @Field("no_telp_pos") String asdss
    );

    //METODE==============================================
    @FormUrlEncoded
    @POST("metode_get.php")
    Call<ResponseMetode> getMetode(
            @Field("id_users") String id
    );

    //STATUS==============================================
    @FormUrlEncoded
    @POST("status_get.php")
    Call<ResponseStatus> getStatus(
            @Field("id_users") String id
    );

    //Posisi Rahim==============================================
    @FormUrlEncoded
    @POST("posisirahim_get.php")
    Call<ResponsePosisiRahim> getRahim(
            @Field("id_users") String id
    );

    //Tumor==============================================
    @FormUrlEncoded
    @POST("tumor_get.php")
    Call<ResponseTumor> getTumor(
            @Field("id_users") String id
    );

    //LAYANAN==============================================
    @FormUrlEncoded
    @POST("layanan_get.php")
    Call<ResponseLayanan> getLayanan(
                    @Field("id_users") String id
            );

    @FormUrlEncoded
    @POST("layanan_all_get.php")
    Call<ResponseLayanan> getAllLayanan(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("layanan_add.php")
    Call<ResponseAddLayanan> addLayanan(
            @Field("id_pas") String id,
            @Field("nama_petugas") String namaPet,
            @Field("tgl_kunjungan") String tglKunj,
            @Field("haid_terakhir") String haidTerak,
            @Field("berat_badan") String bb,
            @Field("tekanan_darah") String tekananDarah,
            @Field("sakit_kuning") String sakitKuning,
            @Field("pendarahan") String pend,
            @Field("tumor") String tumor,
            @Field("hiv_aids") String hiv,
            @Field("posisi_rahim") String posisi,
            @Field("diabetes") String dia,
            @Field("pembekuan_darah") String pembDarah,
            @Field("ES") String es,
            @Field("komplikasi") String komp,
            @Field("tgl_kembali") String tglKemb,
            @Field("status") String status,
            @Field("metode") String metode
    );

    @FormUrlEncoded
    @POST("layanan_edit.php")
    Call<ResponseAddLayanan> editLayanan(
            @Field("id_layanan") String idLayanan,
            @Field("id_pas") String idPasien,
            @Field("nama_petugas") String namaPet,
            @Field("tgl_kunjungan") String tglKunj,
            @Field("haid_terakhir") String haidTerak,
            @Field("berat_badan") String bb,
            @Field("tekanan_darah") String tekananDarah,
            @Field("sakit_kuning") String sakitKuning,
            @Field("pendarahan") String pend,
            @Field("tumor") String tumor,
            @Field("hiv_aids") String hiv,
            @Field("posisi_rahim") String posisi,
            @Field("diabetes") String dia,
            @Field("pembekuan_darah") String pembDarah,
            @Field("ES") String es,
            @Field("komplikasi") String komp,
            @Field("tgl_kembali") String tglKemb,
            @Field("metode") String metode,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("layanan_delete.php")
    Call<ResponseLayanan> deleteLayanan(
            @Field("id_layanan") String id
    );

    //GRAFIK==============================================
    @FormUrlEncoded
    @POST("grafik_get.php")
    Call<ResponseGrafikStatus> getGrafikStatus(
            @Field("id_users") String id
    );

    @FormUrlEncoded
    @POST("grafik_metode_get.php")
    Call<ResponseGrafikMetode> getGrafikMetode(
            @Field("id_users") String id
    );

    //LAPORAN===============================================
    @FormUrlEncoded
    @POST("laporan_kartu.php")
    Call<ResponseKartu> getLaporanKartu(
            @Field("nama_pas") String nama
    );

    @FormUrlEncoded
    @POST("laporan_pelayanan.php")
    Call<ResponseLaporanLayanan> getLaporanLayanan(
            @Field("bulan") String bulan,
            @Field("tahun") String tahun
    );

    //SEARCH==================================================
    @FormUrlEncoded
    @POST("search_pasien.php")
    Call<ResponseSearchPasien> searchPasien(
            @Field("keyword") String nama
    );
}
