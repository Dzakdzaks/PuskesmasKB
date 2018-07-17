package com.developer.kb.kb.Layanan;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Global.EmptyRecyclerView;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Layanan.adapter.AdapterLayanan;
import com.developer.kb.kb.Layanan.response.Layanan;
import com.developer.kb.kb.Layanan.response.Pasienbypo;
import com.developer.kb.kb.Layanan.response.Posisirahim;
import com.developer.kb.kb.Layanan.response.ResponseAddLayanan;
import com.developer.kb.kb.Layanan.response.ResponseLayanan;
import com.developer.kb.kb.Layanan.response.ResponsePasienByPos;
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

public class LayananActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rclLayanan)
    EmptyRecyclerView rclLayanan;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    LinearLayoutManager linearLayoutManager;
    UsersSession usersSession;
    @BindView(R.id.empty_view)
    TextView emptyView;

    EditText inputNamaPetugas, inputBB, inputEfekSamping, inputKomplikasi, inputTekananDarah;
    TextView tvTtlKunjungan, tvTtlKembali, tvTtlHaid;
    Spinner spinnerMetode, spinnerPas, spinnerSKuning, spinnerPendarahan, spinnerTumor, spinnerHIV, spinnerPosisiRahim, spinnerDiabetes, spinnerPembekuanDarah, spinnerStatus;
    RelativeLayout RLttlKunjungan, RLttlKembali, RLttHaid;

    String idPasien, namaPasien;
    String idMetode, namaMetode;
    String idTumor, namaTumor;
    String idPosisi, namaPosisi;
    String idStatus, namaStatus;
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

    String itemKuning, itemPendarahan, itemHIV, itemDiabetes, itemPembDarah;


    String namaPetugas, BB, tekananDarah, ES, komplikasi, ttlKunjungan, ttlKembali, ttlHaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersSession = new UsersSession(this);
//        adapterLayanan = new AdapterLayanan(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogAddLayanan();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rclLayanan.setLayoutManager(linearLayoutManager);
        rclLayanan.setHasFixedSize(true);
        rclLayanan.setEmptyView(findViewById(R.id.empty_view));
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
        );


        String idRole = usersSession.getSpIdRole();

        if (idRole.equals("1") || idRole.equals("3") || idRole.equals("4")) {
            fab.setVisibility(View.GONE);
        }

        if (idRole.equals("1") || idRole.equals("3") || idRole.equals("4")){
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(true);
                    getAllLayanan();
                }
            });
        } else {
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(true);
                    getLayanan();
                }
            });
        }

//        setupRecyclerView();
    }

    @Override
    public void onRefresh() {
        String id = usersSession.getSpIdRole();
        if (id.equals("1") || id.equals("3") || id.equals("4")) {
            getAllLayanan();
        } else {
            getLayanan();
        }
    }


    private void getLayanan() {
        String id = usersSession.getSpIdPos();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseLayanan> call = apiService.getLayanan(id);
        call.enqueue(new Callback<ResponseLayanan>() {
            @Override
            public void onResponse(Call<ResponseLayanan> call, Response<ResponseLayanan> response) {
                String msg = response.body().getMsg();
                List<Layanan> layananList = response.body().getLayanan();
                rclLayanan.setAdapter(new AdapterLayanan(layananList, R.layout.list_pasien, getApplicationContext()));
                swipeRefresh.setRefreshing(false);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseLayanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllLayanan() {
        String id = usersSession.getSpIdUsers();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseLayanan> call = apiService.getAllLayanan(id);
        call.enqueue(new Callback<ResponseLayanan>() {
            @Override
            public void onResponse(Call<ResponseLayanan> call, Response<ResponseLayanan> response) {
                String msg = response.body().getMsg();
                List<Layanan> layananList = response.body().getLayanan();
                rclLayanan.setAdapter(new AdapterLayanan(layananList, R.layout.list_pasien, getApplicationContext()));
                swipeRefresh.setRefreshing(false);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseLayanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void dialogAddLayanan(){
        String id = usersSession.getSpIdUsers();
        final String idPos = usersSession.getSpIdPos();
        namaPetugas = usersSession.getSpName();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_layanan, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Layanan");
        alert.setIcon(R.drawable.user_black);

        inputNamaPetugas = dialogView.findViewById(R.id.input_nama_petugas);
        inputBB = dialogView.findViewById(R.id.input_bb);
        inputTekananDarah = dialogView.findViewById(R.id.input_tekanan_darah);
        inputEfekSamping = dialogView.findViewById(R.id.input_es);
        inputKomplikasi = dialogView.findViewById(R.id.input_komplikasi);
        tvTtlKunjungan = dialogView.findViewById(R.id.tv_ttl_kunjungan);
        tvTtlHaid = dialogView.findViewById(R.id.tv_ttl_haid);
        tvTtlKembali = dialogView.findViewById(R.id.tv_ttl_kembali);
        RLttlKunjungan = dialogView.findViewById(R.id.relative_ttl_kunjungan);
        RLttHaid= dialogView.findViewById(R.id.relative_ttl_haid);
        RLttlKembali = dialogView.findViewById(R.id.relative_ttl_kembali);
        spinnerPas = dialogView.findViewById(R.id.spinner_id_pas);
        spinnerTumor = dialogView.findViewById(R.id.spinner_tumor);
        spinnerSKuning = dialogView.findViewById(R.id.spinner_sakit_kuning);
        spinnerPendarahan = dialogView.findViewById(R.id.spinner_pendarahan);
        spinnerHIV = dialogView.findViewById(R.id.spinner_HIV);
        spinnerPosisiRahim = dialogView.findViewById(R.id.spinner_rahim);
        spinnerDiabetes = dialogView.findViewById(R.id.spinner_diabetes);
        spinnerPembekuanDarah = dialogView.findViewById(R.id.spinner_pemb_darah);
        spinnerStatus = dialogView.findViewById(R.id.spinner_status);
        spinnerMetode = dialogView.findViewById(R.id.spinner_metode);

        inputNamaPetugas.setText(namaPetugas);
        inputNamaPetugas.setEnabled(false);
        inputNamaPetugas.setFocusable(false);

        // get spinner pasienbypos
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePasienByPos> call = apiService.pasienByPosGet(id, idPos);

        call.enqueue(new Callback<ResponsePasienByPos>() {
            @Override
            public void onResponse(Call<ResponsePasienByPos> call, Response<ResponsePasienByPos> response) {
                final List<Pasienbypo> pasienbyposList = response.body().getPasienbypos();
                valueIdPas = new ArrayList<String>();
                valueNamaPas = new ArrayList<String>();

                for (int i = 0; i < pasienbyposList.size(); i++) {
                    valueIdPas.add(pasienbyposList.get(i).getIdPas());
                    valueNamaPas.add(pasienbyposList.get(i).getNama());
                }


                ArrayAdapter<String> spinnerPasienAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPas);
                spinnerPasienAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPas.setAdapter(spinnerPasienAdapter);
                spinnerPas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPasien = valueIdPas.get(position);
                        namaPasien = valueNamaPas.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePasienByPos> call, Throwable t) {
                Toast.makeText(LayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        //get spinner pasienbypos

        // get spinner tumor
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


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaTumor);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTumor.setAdapter(spinnerTumorAdapter);
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
                Toast.makeText(LayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        //get spinner tumor


        // get spinner posisi
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


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPosisi);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPosisiRahim.setAdapter(spinnerTumorAdapter);
                spinnerPosisiRahim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(LayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        //get spinner posisi

        // get spinner status
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


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaStatus);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerStatus.setAdapter(spinnerTumorAdapter);
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
                Toast.makeText(LayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        //get spinner status

        // get spinner metode
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


                ArrayAdapter<String> spinnerTumorAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaMet);
                spinnerTumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMetode.setAdapter(spinnerTumorAdapter);
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
                Toast.makeText(LayananActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        //get spinner metode


        //Datepicker kunjugan
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
        RLttlKunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LayananActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Datepicker kunjungan


        //Datepicker haid
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
        RLttHaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LayananActivity.this, dates, myCalendars
                        .get(Calendar.YEAR), myCalendars.get(Calendar.MONTH),
                        myCalendars.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Datepicker haid


        //Datepicker kembali
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
        RLttlKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LayananActivity.this, datess, myCalendarss
                        .get(Calendar.YEAR), myCalendarss.get(Calendar.MONTH),
                        myCalendarss.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Datepicker kembali

        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        //spinner sakit kuning
        ArrayAdapter<String> spinnerSakitKuningAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerSakitKuningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSKuning.setAdapter(spinnerSakitKuningAdapter);
        spinnerSKuning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemKuning = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner sakitkuning

        //spinner pendarahan
        ArrayAdapter<String> spinnerPendarahanAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPendarahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPendarahan.setAdapter(spinnerPendarahanAdapter);
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
        //spinner pendarahan

        ArrayAdapter<String> spinnerHIVAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerHIVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHIV.setAdapter(spinnerHIVAdapter);
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

        ArrayAdapter<String> spinnerDiabetesAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerDiabetesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiabetes.setAdapter(spinnerDiabetesAdapter);
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

        ArrayAdapter<String> spinnerPembDarahAdapter = new ArrayAdapter<String>(LayananActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPembDarahAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPembekuanDarah.setAdapter(spinnerPembDarahAdapter);
        spinnerPembekuanDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPembDarah = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                namaPetugas = inputNamaPetugas.getText().toString();
                BB = inputBB.getText().toString();
                tekananDarah = inputTekananDarah.getText().toString();
                ES = inputEfekSamping.getText().toString();
                komplikasi = inputKomplikasi.getText().toString();
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
                    inputEfekSamping.setError("belum diisi");
                    inputEfekSamping.requestFocus();
                } else if (komplikasi.isEmpty()) {
                    inputKomplikasi.setError("belum diisi");
                    inputKomplikasi.requestFocus();
                } else {
                    postAddLayanan();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();

    }

    private void postAddLayanan() {
        namaPetugas = inputNamaPetugas.getText().toString();
        BB = inputBB.getText().toString();
        tekananDarah = inputTekananDarah.getText().toString();
        ES = inputEfekSamping.getText().toString();
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
            inputEfekSamping.setError("belum diisi");
            inputEfekSamping.requestFocus();
        } else if (komplikasi.isEmpty()) {
            inputKomplikasi.setError("belum diisi");
            inputKomplikasi.requestFocus();
        }

        ApiService apiServiceLayanan = InitLibrary.getInstance();

        Call<ResponseAddLayanan> callLayanan = apiServiceLayanan.addLayanan(idPasien,namaPetugas,ttlKunjungan,ttlHaid,BB,tekananDarah,itemKuning,itemPendarahan,namaTumor,itemHIV,namaPosisi,itemDiabetes,itemPembDarah,ES,komplikasi,ttlKembali,namaStatus,namaMetode);

        callLayanan.enqueue(new Callback<ResponseAddLayanan>() {
            @Override
            public void onResponse(Call<ResponseAddLayanan> call, Response<ResponseAddLayanan> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), LayananActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(LayananActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LayananActivity.this, "failed add layanan", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
