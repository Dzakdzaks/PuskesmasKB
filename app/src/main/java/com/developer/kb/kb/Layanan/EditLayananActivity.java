package com.developer.kb.kb.Layanan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Layanan.response.Posisirahim;
import com.developer.kb.kb.Layanan.response.ResponseAddLayanan;
import com.developer.kb.kb.Layanan.response.ResponsePosisiRahim;
import com.developer.kb.kb.Layanan.response.ResponseStatus;
import com.developer.kb.kb.Layanan.response.ResponseTumor;
import com.developer.kb.kb.Layanan.response.Status;
import com.developer.kb.kb.Layanan.response.Tumor;
import com.developer.kb.kb.Pasien.response.Metode;
import com.developer.kb.kb.Pasien.response.ResponseMetode;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLayananActivity extends AppCompatActivity {

    @BindView(R.id.input_nama_petugas)
    EditText inputNamaPetugas;
    @BindView(R.id.tvPas)
    TextView tvPas;
    @BindView(R.id.spinner_id_pas)
    Spinner spinnerIdPas;
    @BindView(R.id.tv_ttl_kunjungan)
    TextView tvTtlKunjungan;
    @BindView(R.id.relative_ttl_kunjungan)
    RelativeLayout relativeTtlKunjungan;
    @BindView(R.id.tv_ttl_haid)
    TextView tvTtlHaid;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.relative_ttl_haid)
    RelativeLayout relativeTtlHaid;
    @BindView(R.id.input_bb)
    EditText inputBB;
    @BindView(R.id.input_tekanan_darah)
    EditText inputTekananDarah;
    @BindView(R.id.tvKuning)
    TextView tvKuning;
    @BindView(R.id.spinner_sakit_kuning)
    Spinner spinnerSakitKuning;
    @BindView(R.id.tvpendarahan)
    TextView tvpendarahan;
    @BindView(R.id.spinner_pendarahan)
    Spinner spinnerPendarahan;
    @BindView(R.id.tvTumor)
    TextView tvTumor;
    @BindView(R.id.spinner_tumor)
    Spinner spinnerTumor;
    @BindView(R.id.tvHiv)
    TextView tvHiv;
    @BindView(R.id.spinner_HIV)
    Spinner spinnerHIV;
    @BindView(R.id.tvrahim)
    TextView tvrahim;
    @BindView(R.id.spinner_rahim)
    Spinner spinnerRahim;
    @BindView(R.id.tvDiabetes)
    TextView tvDiabetes;
    @BindView(R.id.spinner_diabetes)
    Spinner spinnerDiabetes;
    @BindView(R.id.tvPembDarah)
    TextView tvPembDarah;
    @BindView(R.id.spinner_pemb_darah)
    Spinner spinnerPembDarah;
    @BindView(R.id.input_es)
    EditText inputEs;
    @BindView(R.id.input_komplikasi)
    EditText inputKomplikasi;
    @BindView(R.id.tv_ttl_kembali)
    TextView tvTtlKembali;
    @BindView(R.id.relative_ttl_kembali)
    RelativeLayout relativeTtlKembali;
    @BindView(R.id.tvMetode)
    TextView tvMetode;
    @BindView(R.id.spinner_metode)
    Spinner spinnerMetode;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.spinner_status)
    Spinner spinnerStatus;

    String itemKuning, itemPendarahan, itemHIV, itemDiabetes, itemPembDarah;
    String namaPetugas, BB, tekananDarah, ES, komplikasi, ttlKunjungan, ttlKembali, ttlHaid, idLayanan, idPosyandu, namaPosyandu;
    String idPasien, namaPasien;
    String idMetode, namaMetode;
    String idTumor, namaTumor;
    String idPosisi, namaPosisi;
    String idStatus, namaStatus;

    UsersSession usersSession;

    List<String> valueIdPas = new ArrayList<String>();
    List<String> valueNamaPas = new ArrayList<String>();
    List<String> valueIdMet = new ArrayList<String>();
    List<String> valueNamaMet = new ArrayList<String>();
    List<String> valueIdTumor = new ArrayList<String>();
    List<String> valueNamaTumor = new ArrayList<String>();
    List<String> valueIdPosisi = new ArrayList<String>();
    List<String> valueNamaPosisi = new ArrayList<String>();
    List<String> valueIdStatus = new ArrayList<String>();
    List<String> valueNamaStatus = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_layanan);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usersSession = new UsersSession(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        idLayanan = getIntent().getStringExtra("id_layanan");
        idPasien = getIntent().getStringExtra("id_pas");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPasien = getIntent().getStringExtra("nama");
        namaPetugas = getIntent().getStringExtra("nama_pet");
        namaPosyandu = getIntent().getStringExtra("nama_pos");
        ttlKunjungan = getIntent().getStringExtra("tgl_kunjungan");
        ttlKembali = getIntent().getStringExtra("tgl_kembali");
        ttlHaid = getIntent().getStringExtra("haid_terakhir");
        BB = getIntent().getStringExtra("berat_badan");
        tekananDarah = getIntent().getStringExtra("tekanan_darah");
        itemKuning = getIntent().getStringExtra("sakit_kuning");
        itemPendarahan = getIntent().getStringExtra("pendarahan");
        namaTumor = getIntent().getStringExtra("tumor");
        itemHIV = getIntent().getStringExtra("hiv_aids");
        namaPosisi = getIntent().getStringExtra("posisi_rahim");
        itemDiabetes = getIntent().getStringExtra("diabetes");
        itemPembDarah = getIntent().getStringExtra("pembekuan_darah");
        komplikasi = getIntent().getStringExtra("komplikasi");
        namaMetode = getIntent().getStringExtra("metode");
        namaStatus = getIntent().getStringExtra("status");
        ES = getIntent().getStringExtra("ES");

        String title = getString(R.string.title_activity_edit_layanan);
        getSupportActionBar().setTitle(title + " " + namaPasien);

        inputNamaPetugas.setText(namaPetugas);
        inputNamaPetugas.setEnabled(false);
        inputNamaPetugas.setFocusable(false);
        tvPas.setText("Nama Pasien : " + namaPasien);
        inputBB.setText(BB);
        inputTekananDarah.setText(tekananDarah);
        inputEs.setText(ES);
        inputKomplikasi.setText(komplikasi);
        tvTtlKunjungan.setVisibility(View.VISIBLE);
        tvTtlHaid.setVisibility(View.VISIBLE);
        tvTtlKembali.setVisibility(View.VISIBLE);
        tvTtlKunjungan.setText(ttlKunjungan);
        tvTtlHaid.setText(ttlHaid);
        tvTtlKembali.setText(ttlKembali);
        relativeTtlKunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateKunjungan();
            }
        });
        relativeTtlKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateKembali();
            }
        });
        relativeTtlHaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateHaid();
            }
        });
        spinnerIdPas.setVisibility(View.GONE);
        getSpinnerDiabetes();
        getSpinnerHIV();
        getSpinnerMetode();
        getSpinnerPembekuanDarah();
        getSpinnerPendarahan();
        getSpinnerMetode();
        getSpinnerPosisi();
        getSpinnerSakitKuning();
        getSpinnerTumor();
        getSpinnerStatus();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.done:
                postEditLayanan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postEditLayanan() {
        namaPetugas = inputNamaPetugas.getText().toString();
        BB = inputBB.getText().toString();
        tekananDarah = inputTekananDarah.getText().toString();
        ES = inputEs.getText().toString();
        komplikasi = inputKomplikasi.getText().toString();
        ttlKunjungan = tvTtlKunjungan.getText().toString();
        ttlHaid = tvTtlHaid.getText().toString();
        ttlKembali = tvTtlKunjungan.getText().toString();
        if (namaPetugas.isEmpty()) {
            inputNamaPetugas.setError("belum diisi");
            inputNamaPetugas.requestFocus();
        } else if (BB.isEmpty()) {
            inputBB.setError("belum diisi");
            inputBB.requestFocus();
        } else if (tekananDarah.isEmpty()) {
            inputTekananDarah.setError("belum diisi");
            inputTekananDarah.requestFocus();
        } else if (ES.isEmpty()) {
            inputEs.setError("belum diisi");
            inputEs.requestFocus();
        } else if (komplikasi.isEmpty()) {
            inputKomplikasi.setError("belum diisi");
            inputKomplikasi.requestFocus();
        }

        ApiService apiServiceLayanan = InitLibrary.getInstance();

        Call<ResponseAddLayanan> callLayanan = apiServiceLayanan.editLayanan(idLayanan,idPasien,namaPetugas,ttlKunjungan,ttlHaid,BB,tekananDarah,itemKuning,itemPendarahan,namaTumor,itemHIV,namaPosisi,itemDiabetes,itemPembDarah,ES,komplikasi,ttlKembali,namaMetode,namaStatus);

        callLayanan.enqueue(new Callback<ResponseAddLayanan>() {
            @Override
            public void onResponse(Call<ResponseAddLayanan> call, Response<ResponseAddLayanan> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), LayananActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(EditLayananActivity.this, msg, Toast.LENGTH_SHORT).show();
                System.out.println("id_pasien : " + idPasien );
                System.out.println("namaPetugas : " + namaPetugas );
                System.out.println("ttlKunjungan : " + ttlKunjungan );
                System.out.println("ttlHaid : " + ttlHaid );
                System.out.println("BB : " + BB );
                System.out.println("tekananDarah : " + tekananDarah );
                System.out.println("itemKuning : " + itemKuning );
                System.out.println("itemPendarahan : " + itemPendarahan );
                System.out.println("namaTumor : " + namaTumor );
                System.out.println("hiv_aids : " + itemHIV );
                System.out.println("namaPosisi : " + namaPosisi );
                System.out.println("diabetes : " + itemDiabetes );
                System.out.println("pembDarah : " + itemPembDarah );
                System.out.println("es : " + ES );
                System.out.println("komplikasi : " + komplikasi );
                System.out.println("ttlKembali : " + ttlKembali );
                System.out.println("namaMetode : " + namaMetode );
                System.out.println("status : " + namaStatus );
            }

            @Override
            public void onFailure(Call<ResponseAddLayanan> call, Throwable t) {
                Toast.makeText(EditLayananActivity.this, "failed edit layanan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getDateKunjungan(){
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvTtlKunjungan.setVisibility(View.VISIBLE);
                tvTtlKunjungan.setText(sdf.format(myCalendar.getTime()));
            }
        };
        relativeTtlKunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditLayananActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getDateKembali(){
        final Calendar myCalendarss = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datess = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarss.set(Calendar.YEAR, year);
                myCalendarss.set(Calendar.MONTH, month);
                myCalendarss.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvTtlKembali.setVisibility(View.VISIBLE);
                tvTtlKembali.setText(sdf.format(myCalendarss.getTime()));
            }
        };
        relativeTtlKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditLayananActivity.this, datess, myCalendarss
                        .get(Calendar.YEAR), myCalendarss.get(Calendar.MONTH),
                        myCalendarss.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getDateHaid(){
        final Calendar myCalendars = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendars.set(Calendar.YEAR, year);
                myCalendars.set(Calendar.MONTH, month);
                myCalendars.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvTtlHaid.setVisibility(View.VISIBLE);
                tvTtlHaid.setText(sdf.format(myCalendars.getTime()));
            }
        };
        relativeTtlHaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditLayananActivity.this, dates, myCalendars
                        .get(Calendar.YEAR), myCalendars.get(Calendar.MONTH),
                        myCalendars.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

//    private void getSpinnerPasienByPos(){
//        String id = usersSession.getSpIdUsers().toString();
//
//        ApiService apiService = InitLibrary.getInstance();
//
//        Call<ResponsePasienByPos> call = apiService.pasienByPosGet(id, idPosyandu);
//
//        call.enqueue(new Callback<ResponsePasienByPos>() {
//            @Override
//            public void onResponse(Call<ResponsePasienByPos> call, Response<ResponsePasienByPos> response) {
//                final List<Pasienbypo> pasienbyposList = response.body().getPasienbypos();
//                valueIdPas = new ArrayList<String>();
//                valueNamaPas = new ArrayList<String>();
//
//                for (int i = 0; i < pasienbyposList.size(); i++) {
//                    valueIdPas.add(pasienbyposList.get(i).getIdPas());
//                    valueNamaPas.add(pasienbyposList.get(i).getNama());
//                }
//
//
//                ArrayAdapter<String> spinnerPasienAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPas);
//                spinnerPasienAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerIdPas.setAdapter(spinnerPasienAdapter);
//
//                String compareValue = namaPasien;
//
//                if (compareValue != null) {
//                    int spinnerPosition = spinnerPasienAdapter.getPosition(compareValue);
//                    spinnerIdPas.setSelection(spinnerPosition);
//                }
//                spinnerIdPas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        idPasien = valueIdPas.get(position);
//                        namaPasien = valueNamaPas.get(position);
////                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePasienByPos> call, Throwable t) {
//                Toast.makeText(EditLayananActivity.this, "fail", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    private void getSpinnerTumor(){
        String id = usersSession.getSpIdUsers().toString();

        ApiService apiServiceTumor = InitLibrary.getInstance();

        Call<ResponseTumor> callTumor = apiServiceTumor.getTumor(id);

        callTumor.enqueue(new Callback<ResponseTumor>() {
            @Override
            public void onResponse(Call<ResponseTumor> call, Response<ResponseTumor> response) {
                final List<Tumor> tumorList = response.body().getTumor();
                valueIdTumor = new ArrayList<String>();
                valueNamaTumor = new ArrayList<String>();

                for (int i = 0; i < tumorList.size(); i++) {
                    valueIdTumor.add(tumorList.get(i).getIdTumor());
                    valueNamaTumor.add(tumorList.get(i).getNamaTumor());
                }


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaTumor);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTumor.setAdapter(spinnerTumorAdapter);

                String compareValue = namaTumor;

                if (compareValue != null) {
                    int spinnerPosition = spinnerTumorAdapter.getPosition(compareValue);
                    spinnerTumor.setSelection(spinnerPosition);
                }
                spinnerTumor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idTumor = valueIdTumor.get(position);
                        namaTumor = valueNamaTumor.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseTumor> call, Throwable t) {
                Toast.makeText(EditLayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getSpinnerPosisi(){
        String id = usersSession.getSpIdUsers().toString();
        ApiService apiServicePosisi = InitLibrary.getInstance();

        Call<ResponsePosisiRahim> callPosisi = apiServicePosisi.getRahim(id);

        callPosisi.enqueue(new Callback<ResponsePosisiRahim>() {
            @Override
            public void onResponse(Call<ResponsePosisiRahim> call, Response<ResponsePosisiRahim> response) {
                final List<Posisirahim> posisirahimList = response.body().getPosisirahim();
                valueIdPosisi = new ArrayList<String>();
                valueNamaPosisi = new ArrayList<String>();

                for (int i = 0; i < posisirahimList.size(); i++) {
                    valueIdPosisi.add(posisirahimList.get(i).getIdPosisi());
                    valueNamaPosisi.add(posisirahimList.get(i).getNamaPosisi());
                }


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPosisi);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRahim.setAdapter(spinnerTumorAdapter);

                String compareValue = namaPosisi;

                if (compareValue != null) {
                    int spinnerPosition = spinnerTumorAdapter.getPosition(compareValue);
                    spinnerRahim.setSelection(spinnerPosition);
                }
                spinnerRahim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPosisi = valueIdPosisi.get(position);
                        namaPosisi = valueNamaPosisi.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosisiRahim> call, Throwable t) {
                Toast.makeText(EditLayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getSpinnerStatus(){
        String id = usersSession.getSpIdUsers().toString();

        ApiService apiServiceStatus = InitLibrary.getInstance();

        Call<ResponseStatus> callStatus = apiServiceStatus.getStatus(id);

        callStatus.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                final List<Status> statusList = response.body().getStatus();
                valueIdStatus = new ArrayList<String>();
                valueNamaStatus = new ArrayList<String>();

                for (int i = 0; i < statusList.size(); i++) {
                    valueIdStatus.add(statusList.get(i).getIdStatus());
                    valueNamaStatus.add(statusList.get(i).getNamaStatus());
                }


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaStatus);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerStatus.setAdapter(spinnerTumorAdapter);
                String compareValue = namaStatus;

                if (compareValue != null) {
                    int spinnerPosition = spinnerTumorAdapter.getPosition(compareValue);
                    spinnerStatus.setSelection(spinnerPosition);
                }
                spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idStatus = valueIdStatus.get(position);
                        namaStatus = valueNamaStatus.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Toast.makeText(EditLayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getSpinnerMetode(){
        String id = usersSession.getSpIdUsers().toString();
        ApiService apiServiceMetode = InitLibrary.getInstance();

        Call<ResponseMetode> callMetode = apiServiceMetode.getMetode(id);

        callMetode.enqueue(new Callback<ResponseMetode>() {
            @Override
            public void onResponse(Call<ResponseMetode> call, Response<ResponseMetode> response) {
                final List<Metode> tumorList = response.body().getMetode();
                valueIdMet = new ArrayList<String>();
                valueNamaMet = new ArrayList<String>();

                for (int i = 0; i < tumorList.size(); i++) {
                    valueIdMet.add(tumorList.get(i).getIdMetode());
                    valueNamaMet.add(tumorList.get(i).getNamaMetode());
                }


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaMet);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMetode.setAdapter(spinnerTumorAdapter);
                String compareValue = namaMetode;

                if (compareValue != null) {
                    int spinnerPosition = spinnerTumorAdapter.getPosition(compareValue);
                    spinnerMetode.setSelection(spinnerPosition);
                }
                spinnerMetode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idMetode = valueIdMet.get(position);
                        namaMetode = valueNamaMet.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMetode> call, Throwable t) {
                Toast.makeText(EditLayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void getSpinnerSakitKuning(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        ArrayAdapter<String> spinnerSakitKuningAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerSakitKuningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSakitKuning.setAdapter(spinnerSakitKuningAdapter);
        String compareValue = itemKuning;

        if (compareValue != null) {
            int spinnerPosition = spinnerSakitKuningAdapter.getPosition(compareValue);
            spinnerSakitKuning.setSelection(spinnerPosition);
        }
        spinnerSakitKuning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemKuning = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  void getSpinnerPendarahan(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        ArrayAdapter<String> spinnerPendarahanAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPendarahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPendarahan.setAdapter(spinnerPendarahanAdapter);
        if (itemPendarahan != null) {
            int spinnerPosition = spinnerPendarahanAdapter.getPosition(itemPendarahan);
            spinnerPendarahan.setSelection(spinnerPosition);
        }
        spinnerPendarahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPendarahan = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  void getSpinnerHIV(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        ArrayAdapter<String> spinnerHIVAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerHIVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHIV.setAdapter(spinnerHIVAdapter);
        if (itemHIV != null) {
            int spinnerPosition = spinnerHIVAdapter.getPosition(itemHIV);
            spinnerHIV.setSelection(spinnerPosition);
        }
        spinnerHIV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemHIV = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  void getSpinnerDiabetes(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        ArrayAdapter<String> spinnerDiabetesAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerDiabetesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiabetes.setAdapter(spinnerDiabetesAdapter);
        if (itemDiabetes != null) {
            int spinnerPosition = spinnerDiabetesAdapter.getPosition(itemDiabetes);
            spinnerDiabetes.setSelection(spinnerPosition);
        }
        spinnerDiabetes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemDiabetes = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  void getSpinnerPembekuanDarah(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        ArrayAdapter<String> spinnerPembDarahAdapter = new ArrayAdapter<String>(EditLayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPembDarahAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPembDarah.setAdapter(spinnerPembDarahAdapter);
        if (itemPembDarah != null) {
            int spinnerPosition = spinnerPembDarahAdapter.getPosition(itemPembDarah);
            spinnerPembDarah.setSelection(spinnerPosition);
        }
        spinnerPembDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPembDarah = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
