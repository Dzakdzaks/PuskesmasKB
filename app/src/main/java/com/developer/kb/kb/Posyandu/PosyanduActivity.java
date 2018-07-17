package com.developer.kb.kb.Posyandu;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Global.EmptyRecyclerView;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Login.users.ResponseUsersKaderGet;
import com.developer.kb.kb.Login.users.User;
import com.developer.kb.kb.Posyandu.adapter.AdapterPosyandu;
import com.developer.kb.kb.Posyandu.response.Posyandu;
import com.developer.kb.kb.Posyandu.response.ResponsePosyandu;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosyanduActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rclPosyandu)
    EmptyRecyclerView rclPosyandu;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    LinearLayoutManager linearLayoutManager;

    UsersSession usersSession;

    String idPetugas, namaPetugas;
    String namaPosyandu, alamatPosyandu, noTelpPosyandu;
    EditText inputNamaPosyandu, inputAlamatPosyandu, inputNoTelpPosyandu;
    TextView tvPos;
    Spinner spinnerPetugas;

    List<String> valueIdPetugas = new ArrayList<String>();
    List<String> valueNamaPetugas = new ArrayList<String>();

    //gone
//    EditText inputUmurPasien, inputAlamatPasien,inputPendidikanPasien, inputPekerjaanPasien, inputAnakPasien;
//    Spinner spinnerHamil, spinnerGakin, spinnerPus4t, spinnerMetode;
//    RelativeLayout relativeLayoutTTL;
//    TextView tvTTL, tvHam, tvGak, tvPus, tvMet;
    //gone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posyandu);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersSession = new UsersSession(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddPosyandu();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rclPosyandu.setLayoutManager(linearLayoutManager);
        rclPosyandu.setHasFixedSize(true);

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
                getPosyanduKu();
            }
        });

        String idRole = usersSession.getSpIdRole();

        if (idRole.equals("3") || idRole.equals("4")){
            fab.setVisibility(View.GONE);
        }
    }


    private void getPosyanduKu(){
        String id = usersSession.getSpIdUsers();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponsePosyandu> call = apiService.getPosyandu(id);
        call.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
            String msg = response.body().getMsg();
                List<Posyandu> posyanduList = response.body().getPosyandu();
                rclPosyandu.setAdapter(new AdapterPosyandu(posyanduList, R.layout.list_pasien, getApplicationContext()));
                swipeRefresh.setRefreshing(false);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onRefresh() {
        getPosyanduKu();
    }

    private void dialogAddPosyandu() {
        String id = usersSession.getSpIdUsers().toString();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_posyandu, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Posyandu");
        alert.setIcon(R.drawable.ic_home_black_24dp);

        inputNamaPosyandu = dialogView.findViewById(R.id.input_nama_posyandu);
        inputAlamatPosyandu = dialogView.findViewById(R.id.input_alamat_posyandu);
        inputNoTelpPosyandu = dialogView.findViewById(R.id.input_no_telpon_posyandu);
        tvPos = dialogView.findViewById(R.id.tvPos);
        spinnerPetugas = dialogView.findViewById(R.id.spinner_id_pet);

//        inputNamaPosyandu.setHint("Nama Posyandu");
//        inputAlamatPosyandu.setHint("Alamat Posyandu");
//        inputNoTelpPosyandu.setHint("Nomor Telepon Posyandu");
//        tvPos.setText("Nama Pembina");

//        //GONE
//        inputUmurPasien = dialogView.findViewById(R.id.input_umur_pasien);
//        inputAlamatPasien = dialogView.findViewById(R.id.input_alamat_pasien);
//        inputPendidikanPasien = dialogView.findViewById(R.id.input_pendidikan_pasien);
//        inputPekerjaanPasien = dialogView.findViewById(R.id.input_pekerjaan_pasien);
//        inputAnakPasien = dialogView.findViewById(R.id.input_anak_pasien);
//        spinnerHamil = dialogView.findViewById(R.id.spinner_hamil);
//        spinnerGakin = dialogView.findViewById(R.id.spinner_gakin);
//        spinnerPus4t = dialogView.findViewById(R.id.spinner_pus4t);
//        spinnerMetode = dialogView.findViewById(R.id.spinner_metode);
//        relativeLayoutTTL = dialogView.findViewById(R.id.relative_ttl_pasien);
//        tvTTL = dialogView.findViewById(R.id.tv_ttl);
//        tvHam = dialogView.findViewById(R.id.tvHam);
//        tvGak = dialogView.findViewById(R.id.tvGak);
//        tvPus = dialogView.findViewById(R.id.tvPus);
//        tvMet = dialogView.findViewById(R.id.tvMet);
//
//        inputUmurPasien.setVisibility(View.GONE);
//        inputAlamatPasien.setVisibility(View.GONE);
//        inputPendidikanPasien.setVisibility(View.GONE);
//        inputPekerjaanPasien.setVisibility(View.GONE);
//        inputAnakPasien.setVisibility(View.GONE);
//        spinnerHamil.setVisibility(View.GONE);
//        spinnerGakin.setVisibility(View.GONE);
//        spinnerPus4t.setVisibility(View.GONE);
//        spinnerMetode.setVisibility(View.GONE);
//        relativeLayoutTTL.setVisibility(View.GONE);
//        tvTTL.setVisibility(View.GONE);
//        tvHam.setVisibility(View.GONE);
//        tvGak.setVisibility(View.GONE);
//        tvPus.setVisibility(View.GONE);
//        tvMet.setVisibility(View.GONE);
//
//        //GONE


        // get spinner Kader
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseUsersKaderGet> call = apiService.getKader(id);

        call.enqueue(new Callback<ResponseUsersKaderGet>() {
            @Override
            public void onResponse(Call<ResponseUsersKaderGet> call, Response<ResponseUsersKaderGet> response) {
                String msg = response.body().getMsg();
                final List<User> userList = response.body().getUsers();
                valueIdPetugas = new ArrayList<String>();
                valueNamaPetugas = new ArrayList<String>();

                for (int i = 0; i < userList.size(); i++) {
                    valueIdPetugas.add(userList.get(i).getIdUsers());
                    valueNamaPetugas.add(userList.get(i).getNama());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(PosyanduActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPetugas);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPetugas.setAdapter(spinnerPosyanduAdapter);
                spinnerPetugas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPetugas = valueIdPetugas.get(position);
                        namaPetugas = valueNamaPetugas.get(position);
//                                Toast.makeText(PasienActivity.this, idPosyandu + " " + namaPosyandu, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                        Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUsersKaderGet> call, Throwable t) {
                Toast.makeText(PosyanduActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner Kader

        alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                namaPosyandu = inputNamaPosyandu.getText().toString();
                alamatPosyandu = inputAlamatPosyandu.getText().toString();
                noTelpPosyandu = inputNoTelpPosyandu.getText().toString();
                if (namaPosyandu.isEmpty()) {
                    inputNamaPosyandu.setError("belum diisi");
                    inputNamaPosyandu.requestFocus();
                } else if (alamatPosyandu.isEmpty()) {
                    inputAlamatPosyandu.setError("belum diisi");
                    inputAlamatPosyandu.requestFocus();
                } else if (noTelpPosyandu.isEmpty()) {
                    inputNoTelpPosyandu.setError("belum diisi");
                    inputNoTelpPosyandu.requestFocus();
                } else {
                    postAddPosyandu();
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

    private void postAddPosyandu() {
        namaPosyandu = inputNamaPosyandu.getText().toString();
        alamatPosyandu = inputAlamatPosyandu.getText().toString();
        noTelpPosyandu = inputNoTelpPosyandu.getText().toString();
        if (namaPosyandu.isEmpty()) {
            inputNamaPosyandu.setError("belum diisi");
            inputNamaPosyandu.requestFocus();
        } else if (alamatPosyandu.isEmpty()) {
            inputAlamatPosyandu.setError("belum diisi");
            inputAlamatPosyandu.requestFocus();
        } else if (noTelpPosyandu.isEmpty()) {
            inputNoTelpPosyandu.setError("belum diisi");
            inputNoTelpPosyandu.requestFocus();
        }

        ApiService apiServicePasien = InitLibrary.getInstance();

        Call<ResponsePosyandu> callPasien = apiServicePasien.addPosyandu(idPetugas,namaPosyandu,alamatPosyandu,noTelpPosyandu);

        callPasien.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PosyanduActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(PosyanduActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(PosyanduActivity.this, "failed add posyandu", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
