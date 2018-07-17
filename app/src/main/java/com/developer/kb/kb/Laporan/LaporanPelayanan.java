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
import com.developer.kb.kb.Laporan.response.ResponseLaporanLayanan;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.developer.kb.kb.LogUtils.LOGE;
import static com.developer.kb.kb.Permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.developer.kb.kb.Permission.PermissionsChecker.REQUIRED_PERMISSION;

public class LaporanPelayanan extends AppCompatActivity {

    @BindView(R.id.tvBulan)
    TextView tvBulan;
    @BindView(R.id.spinner_bulan)
    Spinner spinnerBulan;
    @BindView(R.id.tvTahun)
    TextView tvTahun;
    @BindView(R.id.spinner_tahun)
    Spinner spinnerTahun;

    List<String> valueBulan = new ArrayList<String>();
    List<String> valueTahun = new ArrayList<String>();
    String bulan, noBulan;
    String tahun;
    String formattedDate, namaPetugas, nip;
    String posyandu, kbAktif, kbPasca, kbPus4t, kondomAktif, kondomPasca, pilAktif, pilPasca, suntikAktif, suntikPasca;
    int jumlahPos;

    List<String> listDumpPosyandu;
    List<String> listPosyandu;
    List<String> listKBAktif;
    List<String> listKBPasca;
    List<String> listKBPUS4T;
    List<String> listKondomAktif;
    List<String> listPilAktif;
    List<String> listSuntikAktif;
    List<String> listKondomPasca;
    List<String> listPilPasca;
    List<String> listSuntikPasca;

    PermissionsChecker checker;

    Context mContext;

    UsersSession usersSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pelayanan);
        mContext = getApplicationContext();
        usersSession = new UsersSession(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        namaPetugas = usersSession.getSpName();
        nip = usersSession.getSpNip();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(LaporanPelayanan.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    getTable(FileUtils.getAppPath(mContext) + "Laporan Layanan Bulan " + bulan + " tahun " + tahun + ".pdf");
//                    startActivity(new Intent(getApplicationContext(), Laporan.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                    finish();
                }
            }
        });
        checker = new PermissionsChecker(this);

        getBulan();
        getTahun();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
//        Toast.makeText(this, formattedDate, Toast.LENGTH_LONG).show();

//        getLaporan();

        //JIKA POSYANDU DI EDIT INI JUGA DI EDIT
        listDumpPosyandu = new ArrayList<>();
        listDumpPosyandu.add("VIJARS"); //7
        listDumpPosyandu.add("Melati"); //2
        listDumpPosyandu.add("Mawar"); //4
        listDumpPosyandu.add("Kerinci"); //5
        listDumpPosyandu.add("Kambing"); //1
        listDumpPosyandu.add("Jasmine"); //6
        listDumpPosyandu.add("Anggur"); //3
    }

    private void getBulan() {
        valueBulan.add("Januari");
        valueBulan.add("Februari");
        valueBulan.add("Maret");
        valueBulan.add("April");
        valueBulan.add("Mei");
        valueBulan.add("Juni");
        valueBulan.add("Juli");
        valueBulan.add("Agustus");
        valueBulan.add("Oktober");
        valueBulan.add("November");
        valueBulan.add("Desember");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueBulan);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerBulan.setAdapter(dataAdapter);

        spinnerBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = parent.getItemAtPosition(position).toString();
                if (bulan.equals("Januari") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Januari", "01");
                } else if (bulan.equals("Februari") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Februari", "02");
                } else if (bulan.equals("Maret") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Maret", "03");
                } else if (bulan.equals("April") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("April", "04");
                } else if (bulan.equals("Mei") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Mei", "05");
                } else if (bulan.equals("Juni") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Juni", "06");
                } else if (bulan.equals("Juli") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Juli", "07");
                } else if (bulan.equals("Agustus") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Agustus", "08");
                } else if (bulan.equals("September") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("September", "09");
                } else if (bulan.equals("Oktober") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Oktober", "10");
                } else if (bulan.equals("November") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("November", "11");
                } else if (bulan.equals("Desember") && !bulan.isEmpty()) {
                    noBulan = bulan.replace("Desember", "12");
                }
                getLaporan();

//                Toast.makeText(parent.getContext(), "Selected: " + noBulan, Toast.LENGTH_SHORT).show();
//                Toast.makeText(parent.getContext(), "Selected: " + bulan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTahun() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            valueTahun.add(Integer.toString(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueTahun);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerTahun.setAdapter(dataAdapter);

        String compareValue = String.valueOf(thisYear);

        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinnerTahun.setSelection(spinnerPosition);
        }

        spinnerTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = parent.getItemAtPosition(position).toString();
                getLaporan();

//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getLaporan() {
        ApiService apiService = InitLibrary.getInstance();
//        Toast.makeText(mContext, "wakwaw \n" +
//                noBulan + "\n" +
//                tahun, Toast.LENGTH_SHORT).show();
        Call<ResponseLaporanLayanan> call = apiService.getLaporanLayanan(noBulan, tahun);

        call.enqueue(new Callback<ResponseLaporanLayanan>() {
            @Override
            public void onResponse(Call<ResponseLaporanLayanan> call, Response<ResponseLaporanLayanan> response) {
                if (response.isSuccessful()) {
                    jumlahPos = response.body().getJmlPos();
                    listPosyandu = response.body().getPosyandu();
                    for (int i = 0; i < listPosyandu.size(); i++) {
                        if (listPosyandu.get(i) != null) {
                            posyandu = listPosyandu.get(i);
                        } else {
                            posyandu = "0";
                        }
//                        Toast.makeText(LaporanPelayanan.this, "posyandu \n" +
//                                posyandu, Toast.LENGTH_SHORT).show();
                    }

                    listKBAktif = response.body().getKBAktif();
                    for (int i = 0; i < listKBAktif.size(); i++) {
                        if (listKBAktif.get(i) != null) {
                            kbAktif = listKBAktif.get(i);
                        } else {
                            kbAktif = "0";
                        }
//                        Toast.makeText(LaporanPelayanan.this, "kondom \n" +
//                                kbAktif, Toast.LENGTH_SHORT).show();
                    }

                    listKBPasca = response.body().getKBPascaPersalinan();
                    for (int i = 0; i < listKBPasca.size(); i++) {
                        if (listKBPasca.get(i) != null) {
                            kbPasca = listKBPasca.get(i);
                        } else {
                            kbPasca = "0";
                        }
                    }

                    listKBPUS4T = response.body().getPUS4TBerKB();
                    for (int i = 0; i < listKBPUS4T.size(); i++) {
                        if (listKBPUS4T.get(i) != null) {
                            kbPus4t = listKBPUS4T.get(i);
                        } else {
                            kbPus4t = "0";
                        }
                    }

                    listKondomAktif = response.body().getKondom();
                    for (int i = 0; i < listKondomAktif.size(); i++) {
                        if (listKondomAktif.get(i) != null) {
                            kondomAktif = listKondomAktif.get(i);
                        } else {
                            kondomAktif = "0";
                        }
                    }
                    listPilAktif = response.body().getPil();
                    for (int i = 0; i < listPilAktif.size(); i++) {
                        if (listPilAktif.get(i) != null) {
                            pilAktif = listPilAktif.get(i);
                        } else {
                            pilAktif = "0";
                        }
                    }
                    listSuntikAktif = response.body().getSuntik();
                    for (int i = 0; i < listSuntikAktif.size(); i++) {
                        if (listSuntikAktif.get(i) != null) {
                            suntikAktif = listSuntikAktif.get(i);
                        } else {
                            suntikAktif = "0";
                        }
                    }

                    listKondomPasca = response.body().getKondom1();
                    for (int i = 0; i < listKondomPasca.size(); i++) {
                        if (listKondomPasca.get(i) != null) {
                            kondomPasca = listKondomPasca.get(i);
                        } else {
                            kondomPasca = "0";
                        }
                    }
                    listPilPasca = response.body().getPil1();
                    for (int i = 0; i < listPilPasca.size(); i++) {
                        if (listPilPasca.get(i) != null) {
                            pilPasca = listPilPasca.get(i);
                        } else {
                            pilPasca = "0";
                        }
                    }
                    listSuntikPasca = response.body().getSuntik1();
                    for (int i = 0; i < listSuntikPasca.size(); i++) {
                        if (listSuntikPasca.get(i) != null) {
                            suntikPasca = listSuntikPasca.get(i);
                        } else {
                            suntikPasca = "0";
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseLaporanLayanan> call, Throwable t) {

            }
        });
    }

    private void getTable(String dest) {
        if (new File(dest).exists()) {
            new File(dest).delete();
        }

        try {
            Document document = new Document(PageSize.A4.rotate());
//            document.left(100f);
//            document.top(150f);

            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

//            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 14.0f;
            float mValueFontSize = 16.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font mOrderTitleFont = new Font(urName, mValueFontSize, Font.BOLD, BaseColor.BLACK);
            Chunk mOrderTitleChunk = new Chunk("DINAS KESEHATAN\n" +
                    "PUSKESMAS KELURAHAN JATIRAHAYU", mOrderTitleFont);
            Paragraph mOrderTitleParagraph = new Paragraph(mOrderTitleChunk);
            mOrderTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderTitleParagraph);

            Font mOrderDetailsTitleFont = new Font(urName, mHeadingFontSize, Font.BOLD, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Kel.Jatirahayu, Kec.Pondok Melati, Kota Bekasi", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Chunk(lineSeparator));


            Font mOrderFont = new Font(urName, mHeadingFontSize, Font.BOLD, BaseColor.BLACK);
            Chunk mOrderChunk = new Chunk("PELAYANAN KELUARGA BERENCANA\n" +
                    "DI WILAYAH PUSKESMAS KELURAHAN JATIRAHAYU " + tahun, mOrderFont);
            Paragraph mOrderParagraph = new Paragraph(mOrderChunk);
            mOrderParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderParagraph);

            Font mBulanFont = new Font(urName, 14.0f, Font.NORMAL, BaseColor.BLACK);
            Chunk mBulanChunk = new Chunk("Bulan : " + bulan, mBulanFont);
            Paragraph mBulanParagraph = new Paragraph(mBulanChunk);
//            mBulanParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mBulanParagraph);

            document.add(new Paragraph("\n"));

//            PdfPTable table = new PdfPTable(4); // 3 columns.
//            table.setWidthPercentage(50); // Width 100%
//            table.setSpacingBefore(10f); // Space before table
//            table.setSpacingAfter(10f); // Space after table
//
//            // Set Column widths
//            float[] columnWidths = {3f, 6f, 6f, 6f};
//            table.setWidths(columnWidths);
//
//            // creating table and set the column width
//            table.setHeaderRows(1);
//
//            // add cell of table - header cell
//            PdfPCell cell = new PdfPCell(new Phrase("Posyandu"));
//            cell.setBackgroundColor(new BaseColor(0, 173, 239));
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase("Pelayanan Keluarga Berencana"));
//            cell.setBackgroundColor(new BaseColor(0, 173, 239));
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase("Jumlah KB Aktif Menurut Metode Kontrasepsi"));
//            cell.setBackgroundColor(new BaseColor(0, 173, 239));
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase("Jumlah KB Pasca Persalinan Menurut Metode Kontrasepsi"));
//            cell.setBackgroundColor(new BaseColor(0, 173, 239));
//            table.addCell(cell);
//
////            table.addCell(cell1);
////            table.addCell(cell2);
////            table.addCell(cell3);
////            table.addCell(cell4);
//
//            Phrase ph;
//            // looping the table cell for adding definition
//            for (int ctr = 1; ctr <= 4; ctr++) {
//
//                cell = new PdfPCell();
//                ph = new Phrase("WS-" + ctr);
//                cell.addElement(ph);
//                table.addCell(cell);
//
//                cell = new PdfPCell();
//                ph = new Phrase("Sandeep Sharma " + ctr);
//                cell.addElement(ph);
//                table.addCell(cell);
//
//                cell = new PdfPCell();
//                ph = new Phrase("2000" + ctr);
//                cell.addElement(ph);
//                table.addCell(cell);
//
//                cell = new PdfPCell();
//                ph = new Phrase("5000" + ctr);
//                cell.addElement(ph);
//                table.addCell(cell);
//            }

            // Creates a table with four column. The first rows
            // will have cell 1 to cell 4.
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Posyandu"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Pelayanan Keluarga Berencana"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Jumlah KB Aktif Menurut Metode Kontrasepsi"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Jumlah KB Pasca Persalinan Menurut Metode Kontrasepsi\n"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            table.completeRow();

            // Creates another row that only have to columns.
            // The cell 5 and cell 6 width will span two columns
            // in width.
            cell = new PdfPCell(new Phrase("KB Aktif"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("KB Pasca Persalinan"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("PUS 4T BerKB"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Kondom"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Pil"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Suntik"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Kondom"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Pil"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Suntik"));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            Phrase ph = null;
            for (int aw = 0; aw < listPosyandu.size(); aw++) {
                if (listPosyandu.get(aw) != null) {
                    cell = new PdfPCell();
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    ph = new Phrase(listPosyandu.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell();
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    ph = new Phrase(listDumpPosyandu.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                }

//                    if (listPosyandu.get(0) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("VIJARS");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                        System.out.println("0 " + ph);
//                    } else if (listPosyandu.get(1) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Melati");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                        System.out.println("1 " + ph);
//                    } else if (listPosyandu.get(2) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Mawar");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                    } else if (listPosyandu.get(3) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Kerinci");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                    } else if (listPosyandu.get(4) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Kambing");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                    } else if (listPosyandu.get(5) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Jasmine");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                    } else if (listPosyandu.get(6) == null) {
//                        cell = new PdfPCell();
//                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        ph = new Phrase("Anggur");
//                        cell.addElement(ph);
//                        table.addCell(cell);
//                    }


                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listKBAktif.get(aw) != null) {
                    ph = new Phrase(listKBAktif.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }

                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listKBPasca.get(aw) != null) {
                    ph = new Phrase(listKBPasca.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }

                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listKBPUS4T.get(aw) != null) {
                    ph = new Phrase(listKBPUS4T.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }

                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listKondomAktif.get(aw) != null) {
                    ph = new Phrase(listKondomAktif.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }
                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listPilAktif.get(aw) != null) {
                    ph = new Phrase(listPilAktif.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }
                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listSuntikAktif.get(aw) != null) {
                    ph = new Phrase(listSuntikAktif.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }

                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listKondomPasca.get(aw) != null) {
                    ph = new Phrase(listKondomPasca.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }
                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listPilPasca.get(aw) != null) {
                    ph = new Phrase(listPilPasca.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }
                cell = new PdfPCell();
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (listSuntikPasca.get(aw) != null) {
                    ph = new Phrase(listSuntikPasca.get(aw));
                    cell.addElement(ph);
                    table.addCell(cell);
                } else {
                    ph = new Phrase("0");
                    cell.addElement(ph);
                    table.addCell(cell);
                }
            }


            table.completeRow();


            document.add(table);

            Font mtglFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mtglChunk = new Chunk("Bekasi, " + formattedDate, mtglFont);
            Paragraph mtglParagraph = new Paragraph(mtglChunk);
            mtglParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mtglParagraph);

            Font mPetFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mPetChunk = new Chunk("Petugas Pelayanan KB,", mPetFont);
            Paragraph mPetParagraph = new Paragraph(mPetChunk);
            mPetParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mPetParagraph);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            Font mnPetFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mnPetChunk = new Chunk(namaPetugas, mnPetFont);
            Paragraph mnPetParagraph = new Paragraph(mnPetChunk);
            mnPetParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mnPetParagraph);

            Chunk maeChunk = new Chunk("___________________________", mnPetFont);
            Paragraph maeParagraph = new Paragraph(maeChunk);
            maeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maeParagraph);

            Chunk marChunk = new Chunk("NIP. " + nip, mnPetFont);
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
