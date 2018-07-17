package com.developer.kb.kb.Pasien;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.developer.kb.kb.Global.EmptyRecyclerView;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Pasien.adapter.AdapterPasien;
import com.developer.kb.kb.Pasien.response.Metode;
import com.developer.kb.kb.Pasien.response.Pasien;
import com.developer.kb.kb.Pasien.response.ResponseAddPasien;
import com.developer.kb.kb.Pasien.response.ResponseMetode;
import com.developer.kb.kb.Pasien.response.ResponsePasien;
import com.developer.kb.kb.Pasien.response.ResponseSearchPasien;
import com.developer.kb.kb.Pasien.response.Resultsproduk;
import com.developer.kb.kb.Posyandu.response.Posyandu;
import com.developer.kb.kb.Posyandu.response.ResponsePosyandu;
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

public class PasienActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @BindView(R.id.rclPasien)
    EmptyRecyclerView rclPasien;
    LinearLayoutManager linearLayoutManager;
    UsersSession usersSession;
    String idPasien, nama, no, usia, alamat, pend, pek, anak, ttl;
    String idPosyandu, namaPosyandu;
    String idMetode, namaMetode;
    String itemHamil, itemGakin, itemPus4t;
    List<String> valueIdPos = new ArrayList<String>();
    List<String> valueNamaPos = new ArrayList<String>();
    List<String> valueIdMetode = new ArrayList<String>();
    List<String> valueNamaMetode = new ArrayList<String>();
    EditText inputIdPasien, inputNamaPasien, inputNoTelponPasien, inputUmurPasien, inputAlamatPasien, inputPendidikanPasien, inputPekerjaanPasien, inputAnakPasien;
    Spinner spinnerPosyandu, spinnerHamil, spinnerGakin, spinnerPus4t, spinnerMetode;
    RelativeLayout relativeLayoutTTL;
    TextView tvTTL;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    List<Pasien> pasienbypoList = new ArrayList<>();
    AdapterPasien adapterPasien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                dialogAddPasien();
            }
        });



        usersSession = new UsersSession(this);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rclPasien.setLayoutManager(linearLayoutManager);
        rclPasien.setHasFixedSize(true);

        adapterPasien = new AdapterPasien(pasienbypoList, this);
        rclPasien.setAdapter(adapterPasien);

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
        );
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                getPasien();
            }
        });

        String idRole = usersSession.getSpIdRole();

//        if (idRole.equals("3")){
//            fab.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onRefresh() {
        getPasien();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_pasien, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search_produk);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        pasienbypoList.clear();
        return false;
    }

    private void searchData(String keyword) {
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseSearchPasien> responseSearchProdukCall = apiService.searchPasien(keyword);
        responseSearchProdukCall.enqueue(new Callback<ResponseSearchPasien>() {
            @Override
            public void onResponse(Call<ResponseSearchPasien> call, Response<ResponseSearchPasien> response) {
                int value = response.body().getValue();
                if (value == 1) {
                    pasienbypoList.clear();
                    adapterPasien.notifyDataSetChanged();

                    List<Resultsproduk> resultsproduks = response.body().getResultsproduk();

                    for (int i = 0; i < resultsproduks.size(); i++) {
                        Resultsproduk resultsproduk = resultsproduks.get(i);
                        Pasien allproduk = new Pasien();
                        allproduk.setIdPas(resultsproduk.getIdPas());
                        allproduk.setIdPasien(resultsproduk.getIdPasien());
                        allproduk.setIdPos(resultsproduk.getIdPos());
                        allproduk.setNama(resultsproduk.getNama());
                        allproduk.setNamaPos(resultsproduk.getNamaPos());
                        allproduk.setNoTelp(resultsproduk.getNoTelp());
                        allproduk.setTglLahir(resultsproduk.getTglLahir());
                        allproduk.setUmur(resultsproduk.getUmur());
                        allproduk.setAlamat(resultsproduk.getAlamat());
                        allproduk.setPendidikan(resultsproduk.getPendidikan());
                        allproduk.setPekerjaan(resultsproduk.getPekerjaan());
                        allproduk.setJumlahAnak(resultsproduk.getJumlahAnak());
                        allproduk.setHamil(resultsproduk.getHamil());
                        allproduk.setGakin(resultsproduk.getGakin());
                        allproduk.setPus4t(resultsproduk.getPus4t());
                        allproduk.setMetode(resultsproduk.getMetode());
                        pasienbypoList.add(allproduk);

                    }
                    adapterPasien.notifyDataSetChanged();
                    Toast.makeText(PasienActivity.this, "sukses", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PasienActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchPasien> call, Throwable t) {
                Toast.makeText(PasienActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getPasien() {
        String id = usersSession.getSpIdUsers();
        pasienbypoList.clear();
        adapterPasien.notifyDataSetChanged();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePasien> call = apiService.getPasien(id);

        call.enqueue(new Callback<ResponsePasien>() {
            @Override
            public void onResponse(Call<ResponsePasien> call, Response<ResponsePasien> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    for (Pasien data : response.body().getPasien()){
                        pasienbypoList.add(data);
                    }
                    adapterPasien.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePasien> call, Throwable t) {
                Toast.makeText(PasienActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogAddPasien() {
        String id = usersSession.getSpIdUsers().toString();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_pasien, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Pasien");
        alert.setIcon(R.drawable.user_black);

        inputIdPasien = dialogView.findViewById(R.id.input_id_pasien);
        inputNamaPasien = dialogView.findViewById(R.id.input_nama_pasien);
        inputNoTelponPasien = dialogView.findViewById(R.id.input_no_telpon_pasien);
        inputUmurPasien = dialogView.findViewById(R.id.input_umur_pasien);
        inputAlamatPasien = dialogView.findViewById(R.id.input_alamat_pasien);
        inputPendidikanPasien = dialogView.findViewById(R.id.input_pendidikan_pasien);
        inputPekerjaanPasien = dialogView.findViewById(R.id.input_pekerjaan_pasien);
        inputAnakPasien = dialogView.findViewById(R.id.input_anak_pasien);
        spinnerPosyandu = dialogView.findViewById(R.id.spinner_id_pos);
        spinnerHamil = dialogView.findViewById(R.id.spinner_hamil);
        spinnerGakin = dialogView.findViewById(R.id.spinner_gakin);
        spinnerPus4t = dialogView.findViewById(R.id.spinner_pus4t);
        spinnerMetode = dialogView.findViewById(R.id.spinner_metode);
        relativeLayoutTTL = dialogView.findViewById(R.id.relative_ttl_pasien);
        tvTTL = dialogView.findViewById(R.id.tv_ttl);

        //Datepicker
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvTTL.setVisibility(View.VISIBLE);
                tvTTL.setText(sdf.format(myCalendar.getTime()));
            }
        };
        relativeLayoutTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PasienActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Datepicker

        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        //spinner hamil
        ArrayAdapter<String> spinnerHamilAdapter = new ArrayAdapter<String>(PasienActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerHamilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHamil.setAdapter(spinnerHamilAdapter);
        spinnerHamil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemHamil = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner hamil

        //spinner gakin
        ArrayAdapter<String> spinnerGakinAdapter = new ArrayAdapter<String>(PasienActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerGakinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGakin.setAdapter(spinnerGakinAdapter);
        spinnerGakin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemGakin = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner gakin

        //spinner pus4t
        ArrayAdapter<String> spinnerPus4tAdapter = new ArrayAdapter<String>(PasienActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPus4tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPus4t.setAdapter(spinnerPus4tAdapter);
        spinnerPus4t.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPus4t = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner pus4t

        // get spinner posyandu
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePosyandu> call = apiService.getPosyandu(id);

        call.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                String msg = response.body().getMsg();
                final List<Posyandu> posyanduList = response.body().getPosyandu();
                valueIdPos = new ArrayList<String>();
                valueNamaPos = new ArrayList<String>();

                for (int i = 0; i < posyanduList.size(); i++) {
                    valueIdPos.add(posyanduList.get(i).getIdPos());
                    valueNamaPos.add(posyanduList.get(i).getNamaPos());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(PasienActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPos);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPosyandu.setAdapter(spinnerPosyanduAdapter);
                spinnerPosyandu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPosyandu = valueIdPos.get(position);
                        namaPosyandu = valueNamaPos.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(PasienActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner posyandu

        // get spinner metode
        ApiService apiServiceMetode = InitLibrary.getInstance();

        Call<ResponseMetode> callMetode = apiServiceMetode.getMetode(id);

        callMetode.enqueue(new Callback<ResponseMetode>() {
            @Override
            public void onResponse(Call<ResponseMetode> call, Response<ResponseMetode> response) {
                String msg = response.body().getMsg();
                final List<Metode> metodeList = response.body().getMetode();
                valueIdMetode = new ArrayList<String>();
                valueNamaMetode = new ArrayList<String>();

                for (int i = 0; i < metodeList.size(); i++) {
                    valueIdMetode.add(metodeList.get(i).getIdMetode());
                    valueNamaMetode.add(metodeList.get(i).getNamaMetode());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(PasienActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaMetode);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMetode.setAdapter(spinnerPosyanduAdapter);
                spinnerMetode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idMetode = valueIdMetode.get(position);
                        namaMetode = valueNamaMetode.get(position);
//                        Toast.makeText(PasienActivity.this, idMetode + " " + namaMetode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMetode> call, Throwable t) {
                Toast.makeText(PasienActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner metode

        alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                idPasien = inputIdPasien.getText().toString();
                nama = inputNamaPasien.getText().toString();
                no = inputNoTelponPasien.getText().toString();
                usia = inputUmurPasien.getText().toString();
                alamat = inputAlamatPasien.getText().toString();
                pend = inputPendidikanPasien.getText().toString();
                pek = inputPekerjaanPasien.getText().toString();
                anak = inputAnakPasien.getText().toString();
                if (idPasien.isEmpty()) {
                    inputIdPasien.setError("belum diisi");
                    inputIdPasien.requestFocus();
                } else if (nama.isEmpty()) {
                    inputNamaPasien.setError("belum diisi");
                    inputNamaPasien.requestFocus();
                } else if (no.isEmpty()) {
                    inputNoTelponPasien.setError("belum diisi");
                    inputNoTelponPasien.requestFocus();
                } else if (usia.isEmpty()) {
                    inputUmurPasien.setError("belum diisi");
                    inputUmurPasien.requestFocus();
                } else if (alamat.isEmpty()) {
                    inputAlamatPasien.setError("belum diisi");
                    inputAlamatPasien.requestFocus();
                } else if (pend.isEmpty()) {
                    inputPendidikanPasien.setError("belum diisi");
                    inputPendidikanPasien.requestFocus();
                } else if (pek.isEmpty()) {
                    inputPekerjaanPasien.setError("belum diisi");
                    inputPekerjaanPasien.requestFocus();
                } else if (anak.isEmpty()) {
                    inputAnakPasien.setError("belum diisi");
                    inputAnakPasien.requestFocus();
                } else {
                    postAddPasien();
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


    private void postAddPasien() {
        idPasien = inputIdPasien.getText().toString();
        nama = inputNamaPasien.getText().toString();
        no = inputNoTelponPasien.getText().toString();
        usia = inputUmurPasien.getText().toString();
        alamat = inputAlamatPasien.getText().toString();
        pend = inputPendidikanPasien.getText().toString();
        pek = inputPekerjaanPasien.getText().toString();
        anak = inputAnakPasien.getText().toString();
        ttl = tvTTL.getText().toString();

        if (idPasien.isEmpty()) {
            inputIdPasien.setError("belum diisi");
            inputIdPasien.requestFocus();
        } else if (nama.isEmpty()) {
            inputNamaPasien.setError("belum diisi");
            inputNamaPasien.requestFocus();
        } else if (no.isEmpty()) {
            inputNoTelponPasien.setError("belum diisi");
            inputNoTelponPasien.requestFocus();
        } else if (usia.isEmpty()) {
            inputUmurPasien.setError("belum diisi");
            inputUmurPasien.requestFocus();
        } else if (alamat.isEmpty()) {
            inputAlamatPasien.setError("belum diisi");
            inputAlamatPasien.requestFocus();
        } else if (pend.isEmpty()) {
            inputPendidikanPasien.setError("belum diisi");
            inputPendidikanPasien.requestFocus();
        } else if (pek.isEmpty()) {
            inputPekerjaanPasien.setError("belum diisi");
            inputPekerjaanPasien.requestFocus();
        } else if (anak.isEmpty()) {
            inputAnakPasien.setError("belum diisi");
            inputAnakPasien.requestFocus();
        }

        ApiService apiServicePasien = InitLibrary.getInstance();

        Call<ResponseAddPasien> callPasien = apiServicePasien.addPasien(idPasien, idPosyandu, nama, no, ttl, usia, alamat, pend, pek, anak, itemHamil, itemGakin, itemPus4t, namaMetode);

        callPasien.enqueue(new Callback<ResponseAddPasien>() {
            @Override
            public void onResponse(Call<ResponseAddPasien> call, Response<ResponseAddPasien> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PasienActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseAddPasien> call, Throwable t) {
                Toast.makeText(PasienActivity.this, "failed add pasien", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
