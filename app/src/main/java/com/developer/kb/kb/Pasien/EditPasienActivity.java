package com.developer.kb.kb.Pasien;

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
import com.developer.kb.kb.Pasien.response.Metode;
import com.developer.kb.kb.Pasien.response.ResponseAddPasien;
import com.developer.kb.kb.Pasien.response.ResponseMetode;
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

public class EditPasienActivity extends AppCompatActivity {

    @BindView(R.id.input_id_pasien)
    EditText inputIdPasien;
    @BindView(R.id.spinner_id_pos)
    Spinner spinnerIdPos;
    @BindView(R.id.input_nama_pasien)
    EditText inputNamaPasien;
    @BindView(R.id.input_no_telpon_pasien)
    EditText inputNoTelponPasien;
    @BindView(R.id.tv_ttl)
    TextView tvTtl;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.relative_ttl_pasien)
    RelativeLayout relativeTtlPasien;
    @BindView(R.id.input_umur_pasien)
    EditText inputUmurPasien;
    @BindView(R.id.input_alamat_pasien)
    EditText inputAlamatPasien;
    @BindView(R.id.input_pendidikan_pasien)
    EditText inputPendidikanPasien;
    @BindView(R.id.input_pekerjaan_pasien)
    EditText inputPekerjaanPasien;
    @BindView(R.id.input_anak_pasien)
    EditText inputAnakPasien;
    @BindView(R.id.spinner_hamil)
    Spinner spinnerHamil;
    @BindView(R.id.spinner_gakin)
    Spinner spinnerGakin;
    @BindView(R.id.spinner_pus4t)
    Spinner spinnerPus4t;
    @BindView(R.id.spinner_metode)
    Spinner spinnerMetode;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;


    String idPasien, nama, no, usia, alamat, pend, pek, anak, ttl;
    String idPosyandu, namaPosyandu;
    String idMetode, namaMetode;
    String itemHamil, itemGakin, itemPus4t;

    List<String> valueIdPos = new ArrayList<String>();
    List<String> valueNamaPos = new ArrayList<String>();
    List<String> valueIdMetode = new ArrayList<String>();
    List<String> valueNamaMetode = new ArrayList<String>();

    UsersSession usersSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pasien);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usersSession = new UsersSession(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        idPasien = getIntent().getStringExtra("id_pasien");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPosyandu = getIntent().getStringExtra("nama_pos");
        nama = getIntent().getStringExtra("nama");
        no = getIntent().getStringExtra("no_telp");
        ttl = getIntent().getStringExtra("tgl_lahir");
        usia = getIntent().getStringExtra("umur");
        alamat = getIntent().getStringExtra("alamat");
        pend = getIntent().getStringExtra("pendidikan");
        pek = getIntent().getStringExtra("pekerjaan");
        anak = getIntent().getStringExtra("jumlah_anak");
        itemHamil = getIntent().getStringExtra("hamil");
        itemGakin = getIntent().getStringExtra("gakin");
        itemPus4t = getIntent().getStringExtra("pus4t");
        namaMetode = getIntent().getStringExtra("metode");

        String title = getString(R.string.title_activity_edit_pasien);
        getSupportActionBar().setTitle(title + " " + nama);

        inputIdPasien.setText(idPasien);
        inputNamaPasien.setText(nama);
        inputAlamatPasien.setText(alamat);
        inputAnakPasien.setText(anak);
        inputNoTelponPasien.setText(no);
        inputPekerjaanPasien.setText(pek);
        inputPendidikanPasien.setText(pend);
        inputUmurPasien.setText(usia);
        tvTtl.setVisibility(View.VISIBLE);
        tvTtl.setText(ttl);
        relativeTtlPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });


        getSpinnerPosyandu();
        getSpinnerMetode();
        getSpinners();


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
                postEditPasien();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDate(){
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
                tvTtl.setVisibility(View.VISIBLE);
                tvTtl.setText(sdf.format(myCalendar.getTime()));
            }
        };
        relativeTtlPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditPasienActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Datepicker
    }

    private void getSpinnerPosyandu() {
        String id = usersSession.getSpIdUsers();
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


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, valueNamaPos);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIdPos.setAdapter(spinnerPosyanduAdapter);
                if (namaPosyandu != null) {
                    int spinnerPosition = spinnerPosyanduAdapter.getPosition(namaPosyandu);
                    spinnerIdPos.setSelection(spinnerPosition);
                }

                spinnerIdPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner posyandu
    }

    private void getSpinnerMetode() {
        String id = usersSession.getSpIdUsers();
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


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, valueNamaMetode);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMetode.setAdapter(spinnerPosyanduAdapter);
                if (namaMetode != null) {
                    int spinnerPosition = spinnerPosyanduAdapter.getPosition(namaMetode);
                    spinnerMetode.setSelection(spinnerPosition);
                }
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
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner metode
    }

    private void getSpinners(){
        List<String> options = new ArrayList<String>();
        options.add("Ya");
        options.add("Tidak");

        //spinner hamil
        ArrayAdapter<String> spinnerHamilAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, options);
        spinnerHamilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHamil.setAdapter(spinnerHamilAdapter);
        if (itemHamil != null) {
            int spinnerPosition = spinnerHamilAdapter.getPosition(itemHamil);
            spinnerHamil.setSelection(spinnerPosition);
        }
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
        ArrayAdapter<String> spinnerGakinAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, options);
        spinnerGakinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGakin.setAdapter(spinnerGakinAdapter);
        if (itemGakin != null) {
            int spinnerPosition = spinnerGakinAdapter.getPosition(itemGakin);
            spinnerGakin.setSelection(spinnerPosition);
        }
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
        ArrayAdapter<String> spinnerPus4tAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, options);
        spinnerPus4tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPus4t.setAdapter(spinnerPus4tAdapter);
        if (itemPus4t != null) {
            int spinnerPosition = spinnerPus4tAdapter.getPosition(itemPus4t);
            spinnerPus4t.setSelection(spinnerPosition);
        }
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
    }

    private void postEditPasien() {
        idPasien = inputIdPasien.getText().toString();
        nama = inputNamaPasien.getText().toString();
        no = inputNoTelponPasien.getText().toString();
        usia = inputUmurPasien.getText().toString();
        alamat = inputAlamatPasien.getText().toString();
        pend = inputPendidikanPasien.getText().toString();
        pek = inputPekerjaanPasien.getText().toString();
        anak = inputAnakPasien.getText().toString();
        ttl = tvTtl.getText().toString();

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

        Call<ResponseAddPasien> callPasien = apiServicePasien.editPasien(idPasien, idPosyandu, nama, no, ttl, usia, alamat, pend, pek, anak, itemHamil, itemGakin, itemPus4t, namaMetode);

        callPasien.enqueue(new Callback<ResponseAddPasien>() {
            @Override
            public void onResponse(Call<ResponseAddPasien> call, Response<ResponseAddPasien> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PasienActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseAddPasien> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed edit pasien", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
