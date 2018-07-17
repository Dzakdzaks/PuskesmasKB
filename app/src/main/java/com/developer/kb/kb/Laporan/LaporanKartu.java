package com.developer.kb.kb.Laporan;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.FileUtils;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Laporan.response.Laporankartu;
import com.developer.kb.kb.Laporan.response.ResponseKartu;
import com.developer.kb.kb.Pasien.response.Pasien;
import com.developer.kb.kb.Pasien.response.ResponsePasien;
import com.developer.kb.kb.Permission.PermissionsActivity;
import com.developer.kb.kb.Permission.PermissionsChecker;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.developer.kb.kb.LogUtils.LOGE;
import static com.developer.kb.kb.Permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.developer.kb.kb.Permission.PermissionsChecker.REQUIRED_PERMISSION;

public class LaporanKartu extends AppCompatActivity {

    PermissionsChecker checker;

    Context mContext;

    @BindView(R.id.tvPasien)
    TextView tvPasien;
    @BindView(R.id.spinner_pasien)
    Spinner spinnerPasien;

    UsersSession usersSession;

    String nomorPasien, idPasien, namaPasien, umur, alamat, pend, pek, jmlanak, status, hamil, bb, tekDarah, haid, sakKun, pendarahan, tumor, HIV, posisiRahim, diabetes, pembDarah, metode, tglLayani, tglKembali, ES, komplikasi;
    String namaPetugas, nip;
    List<String> valueIdPas = new ArrayList<String>();
    List<String> valueNamaPas = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_kartu);
        mContext = getApplicationContext();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usersSession = new UsersSession(this);
        getPasien();
        namaPetugas = usersSession.getSpName();
        nip = usersSession.getSpNip();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(LaporanKartu.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    createPdf(FileUtils.getAppPath(mContext) + "Kartu Pasien "+ namaPasien +".pdf");
//                    startActivity(new Intent(getApplicationContext(), Laporan.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                    finish();
                }
            }
        });

        checker = new PermissionsChecker(this);
//        createPdf(FileUtils.getAppPath(mContext) + "123.pdf");

    }

    private void getPasien(){
        String id = usersSession.getSpIdUsers();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePasien> call = apiService.getPasien(id);

        call.enqueue(new Callback<ResponsePasien>() {
            @Override
            public void onResponse(Call<ResponsePasien> call, Response<ResponsePasien> response) {
                final List<Pasien> pasienbyposList = response.body().getPasien();
                valueIdPas = new ArrayList<String>();
                valueNamaPas = new ArrayList<String>();

                for (int i = 0; i < pasienbyposList.size(); i++) {
                    valueIdPas.add(pasienbyposList.get(i).getIdPas());
                    valueNamaPas.add(pasienbyposList.get(i).getNama());
                }


                ArrayAdapter<String> spinnerPasienAdapter = new ArrayAdapter<String>(LaporanKartu.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPas);
                spinnerPasienAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPasien.setAdapter(spinnerPasienAdapter);
                spinnerPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPasien = valueIdPas.get(position);
                        namaPasien = valueNamaPas.get(position);
                        getLaporan();
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePasien> call, Throwable t) {
                Toast.makeText(LaporanKartu.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getLaporan(){
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseKartu> call = apiService.getLaporanKartu(namaPasien);

        call.enqueue(new Callback<ResponseKartu>() {
            @Override
            public void onResponse(Call<ResponseKartu> call, Response<ResponseKartu> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<Laporankartu> kartuList = response.body().getLaporankartu();
                    nomorPasien = kartuList.get(0).getIdPasien();
                    System.out.println("nomorrr " + nomorPasien );
                    umur = kartuList.get(0).getUmur();
                    alamat = kartuList.get(0).getAlamat();
                    pend = kartuList.get(0).getPendidikan();
                    pek = kartuList.get(0).getPekerjaan();
                    jmlanak = kartuList.get(0).getJumlahAnak();
                    status = kartuList.get(0).getStatus();
                    hamil = kartuList.get(0).getHamil();
                    bb = kartuList.get(0).getBeratBadan();
                    tekDarah = kartuList.get(0).getTekananDarah();
                    haid = kartuList.get(0).getHaidTerakhir();
                    sakKun = kartuList.get(0).getSakitKuning();
                    pendarahan = kartuList.get(0).getPendarahan();
                    tumor = kartuList.get(0).getTumor();
                    HIV = kartuList.get(0).getHivAids();
                    posisiRahim = kartuList.get(0).getPosisiRahim();
                    diabetes = kartuList.get(0).getDiabetes();
                    pembDarah = kartuList.get(0).getPembekuanDarah();
                    metode = kartuList.get(0).getMetode();
                    tglLayani = kartuList.get(0).getTglKunjungan();
                    tglKembali = kartuList.get(0).getTglKembali();
                    ES = kartuList.get(0).getES();
                    komplikasi = kartuList.get(0).getKomplikasi();

//                    Toast.makeText(LaporanKartu.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKartu> call, Throwable t) {
                Toast.makeText(LaporanKartu.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPdf(String dest){
        if (new File(dest).exists()) {
            new File(dest).delete();
        }
        try {
            /**
             * Creating Document
             */
            Document document = new Document();

            // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open to write
            document.open();

            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Fahri");
            document.addCreator("Dzakdzaks");

            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 14.0f;
            float mValueFontSize = 16.0f;

            /**
             * How to USE FONT....
             */
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Font mOrderDetailsTitleFont = new Font(urName, 24.0f, Font.BOLD, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Kartu Status Peserta KB", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            // Fields of Order Details...
            // Adding Chunks for Title and value
            Font mIdPasienFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mIdPasienChunk = new Chunk("Nomor Kartu : " + nomorPasien, mIdPasienFont);
            Paragraph mPasienParagraph = new Paragraph(mIdPasienChunk);
            mPasienParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mPasienParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaPasienFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaPasienChunk = new Chunk("Nama : " + namaPasien + "                                            " + "Pendidikan: " + pend, mNamaPasienFont);
            Paragraph mNamaPasienParagraph = new Paragraph(mNamaPasienChunk);
            document.add(mNamaPasienParagraph);

            Font mNamaUmurFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunk = new Chunk("Umur : " + umur + "                                                   " + "Pekerjaan: " + pek, mNamaUmurFont);
            Paragraph mNamaUmurParagraph = new Paragraph(mNamaUmurChunk);
            document.add(mNamaUmurParagraph);

            Font mNamaAlamatFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaAlamatChunk = new Chunk("Alamat : " + alamat, mNamaAlamatFont);
            Paragraph mNamaAlamatParagraph = new Paragraph(mNamaAlamatChunk);
            document.add(mNamaAlamatParagraph);

//            document.add(new Paragraph("\n"));
//            document.add(new Paragraph("\n"));

            Font mNamaAnakFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaAnakChunk = new Chunk("Jumlah Anak : " + jmlanak, mNamaAnakFont);
            Paragraph mNamaAnakParagraph = new Paragraph(mNamaAnakChunk);
            document.add(mNamaAnakParagraph);

            Font mNamaStatusFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaStatusChunk = new Chunk("Status Pasien KB : " + status, mNamaStatusFont);
            Paragraph mNamaStatusParagraph = new Paragraph(mNamaStatusChunk);
            document.add(mNamaStatusParagraph);

            Font mNamahamilFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamahamilChunk = new Chunk("Hamil : " + hamil + "                                                " + "Tekanan Darah: " + tekDarah, mNamahamilFont);
            Paragraph mNamahamilParagraph = new Paragraph(mNamahamilChunk);
            document.add(mNamahamilParagraph);

            Font mNamabbFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamabbChunk = new Chunk("Berat Badan : " + bb + "kg" + "                                        " + "Haid Terakhir: " + haid, mNamabbFont);
            Paragraph mNamabbParagraph = new Paragraph(mNamabbChunk);
            document.add(mNamabbParagraph);


            // Adding Line Breakable Space....
//            document.add(new Paragraph("\n"));
            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            Font mNamaKeadaanFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaKeadaanChunk = new Chunk("Keadaan Peserta KB saat ini : \n" +
                    "   a. Sakit Kuning : " + sakKun +"\n" +
                    "   b. Pendarahan Pervaginaan : " + pendarahan + "\n" +
                    "   c. Tumor : " + tumor + "\n" +
                    "   d. HIV/AIDS : " + HIV, mNamaKeadaanFont);
            Paragraph mNamaKeadaanParagraph = new Paragraph(mNamaKeadaanChunk);
            document.add(mNamaKeadaanParagraph);

//            document.add(new Paragraph("\n"));

            Chunk mNamaPosisiChunk = new Chunk("Posisi Rahim : " + posisiRahim, mNamaKeadaanFont);
            Paragraph mNamaPosisiParagraph = new Paragraph(mNamaPosisiChunk);
            document.add(mNamaPosisiParagraph);

            Chunk mNamaDiabetesChunk = new Chunk("Diabetes : " + diabetes, mNamaKeadaanFont);
            Paragraph mNamaDiabetesParagraph = new Paragraph(mNamaDiabetesChunk);
            document.add(mNamaDiabetesParagraph);

            Chunk mNamaPembDarahChunk = new Chunk("Pembekuan Darah : " + pembDarah, mNamaKeadaanFont);
            Paragraph mNamaPembDarahParagraph = new Paragraph(mNamaPembDarahChunk);
            document.add(mNamaPembDarahParagraph);

            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            Chunk mNamaygChunk = new Chunk("Kontrasepsi yang diberikan : " + metode, mNamaKeadaanFont);
            Paragraph mNamaygParagraph = new Paragraph(mNamaygChunk);
            document.add(mNamaygParagraph);

            Chunk mNamatglLayaniChunk = new Chunk("Tanggal dilayani : " + tglLayani, mNamaKeadaanFont);
            Paragraph mNamatglLayaniParagraph = new Paragraph(mNamatglLayaniChunk);
            document.add(mNamatglLayaniParagraph);

            Chunk mNamatglKembaliChunk = new Chunk("Tanggal dipesan kembali : " + tglKembali, mNamaKeadaanFont);
            Paragraph mNamatglKembaliParagraph = new Paragraph(mNamatglKembaliChunk);
            document.add(mNamatglKembaliParagraph);

            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            Chunk mttdChunk = new Chunk("Penanggung Jawab Pelayanan KB", mNamaKeadaanFont);
            Paragraph mttdParagraph = new Paragraph(mttdChunk);
            mttdParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mttdParagraph);

            Chunk mwChunk = new Chunk("Dokter/Bidan/Perawat Kesehatan", mNamaKeadaanFont);
            Paragraph mwParagraph = new Paragraph(mwChunk);
            mwParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mwParagraph);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            Chunk maChunk = new Chunk(namaPetugas, mNamaKeadaanFont);
            Paragraph maParagraph = new Paragraph(maChunk);
            maParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maParagraph);

            Chunk maeChunk = new Chunk("___________________________", mNamaKeadaanFont);
            Paragraph maeParagraph = new Paragraph(maeChunk);
            maeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maeParagraph);

            Chunk marChunk = new Chunk("NIP. " + nip , mNamaKeadaanFont);
            Paragraph marParagraph = new Paragraph(marChunk);
            marParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(marParagraph);

            document.close();

            Toast.makeText(mContext, "Created... :)", Toast.LENGTH_SHORT).show();

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

}
