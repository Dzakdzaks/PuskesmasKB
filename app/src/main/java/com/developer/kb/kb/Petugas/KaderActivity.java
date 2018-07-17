package com.developer.kb.kb.Petugas;

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
import com.developer.kb.kb.Petugas.adapter.AdapterKader;
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

public class KaderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rclKader)
    EmptyRecyclerView rclKader;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    LinearLayoutManager linearLayoutManager;
    UsersSession usersSession;


    String idPos, namaPos;
    String namaKader, alamatKader, noTelpKader, nip, uName, pass;
    EditText inputNamaKader, inputAlamatKader, inputNoTelpKaderu, inputNip, inputUname, inputPass;
    TextView tvPos;
    Spinner spinnerPos;

    List<String> valueIdPetugas = new ArrayList<String>();
    List<String> valueNamaPetugas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kader);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usersSession = new UsersSession(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddKader();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rclKader.setLayoutManager(linearLayoutManager);
        rclKader.setHasFixedSize(true);

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
                getKaderKu();
            }
        });

        String idRole = usersSession.getSpIdRole();

        if (idRole.equals("2") || idRole.equals("3") || idRole.equals("4")){
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        getKaderKu();
    }
    private void getKaderKu(){
        String id = usersSession.getSpIdUsers();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseUsersKaderGet> call = apiService.getKader(id);
        call.enqueue(new Callback<ResponseUsersKaderGet>() {
            @Override
            public void onResponse(Call<ResponseUsersKaderGet> call, Response<ResponseUsersKaderGet> response) {
                String msg = response.body().getMsg();
                List<User> posyanduList = response.body().getUsers();
                rclKader.setAdapter(new AdapterKader(posyanduList, R.layout.list_pasien, getApplicationContext()));
                swipeRefresh.setRefreshing(false);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUsersKaderGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void dialogAddKader() {
        String id = usersSession.getSpIdUsers().toString();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_kader, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Petugas");
        alert.setIcon(R.drawable.user_black);

        inputNamaKader = dialogView.findViewById(R.id.input_nama_kader);
        inputAlamatKader = dialogView.findViewById(R.id.input_alamat_kader);
        inputNoTelpKaderu = dialogView.findViewById(R.id.input_no_telpon_kader);
        inputNip = dialogView.findViewById(R.id.input_nip_kader);
        inputUname = dialogView.findViewById(R.id.input_uname_kader);
        inputPass = dialogView.findViewById(R.id.input_pass_kader);
        tvPos = dialogView.findViewById(R.id.tvPos);
        spinnerPos = dialogView.findViewById(R.id.spinner_id_pos);

        // get spinner pos
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePosyandu> call = apiService.getPosyandu(id);

        call.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                String msg = response.body().getMsg();
                final List<Posyandu> userList = response.body().getPosyandu();
                valueIdPetugas = new ArrayList<String>();
                valueNamaPetugas = new ArrayList<String>();

                for (int i = 0; i < userList.size(); i++) {
                    valueIdPetugas.add(userList.get(i).getIdPos());
                    valueNamaPetugas.add(userList.get(i).getNamaPos());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(KaderActivity.this, android.R.layout.simple_spinner_dropdown_item, valueNamaPetugas);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPos.setAdapter(spinnerPosyanduAdapter);
                spinnerPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPos = valueIdPetugas.get(position);
                        namaPos = valueNamaPetugas.get(position);
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
                Toast.makeText(KaderActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner pos

        alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                namaKader = inputNamaKader.getText().toString();
                nip = inputNip.getText().toString();
                uName = inputUname.getText().toString();
                pass = inputPass.getText().toString();
                alamatKader = inputAlamatKader.getText().toString();
                noTelpKader = inputNoTelpKaderu.getText().toString();
                if (namaKader.isEmpty()) {
                    inputNamaKader.setError("belum diisi");
                    inputNamaKader.requestFocus();
                } else if (nip.isEmpty()) {
                    inputNip.setError("belum diisi");
                    inputNip.requestFocus();
                } else if (uName.isEmpty()) {
                    inputUname.setError("belum diisi");
                    inputUname.requestFocus();
                } else if (pass.isEmpty()) {
                    inputPass.setError("belum diisi");
                    inputPass.requestFocus();
                } else if (alamatKader.isEmpty()) {
                    inputAlamatKader.setError("belum diisi");
                    inputAlamatKader.requestFocus();
                } else if (noTelpKader.isEmpty()) {
                    inputNoTelpKaderu.setError("belum diisi");
                    inputNoTelpKaderu.requestFocus();
                } else {
                    postAddKader();
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

    private void postAddKader() {
        namaKader = inputNamaKader.getText().toString();
        nip = inputNip.getText().toString();
        uName = inputUname.getText().toString();
        pass = inputPass.getText().toString();
        alamatKader = inputAlamatKader.getText().toString();
        noTelpKader = inputNoTelpKaderu.getText().toString();
        if (namaKader.isEmpty()) {
            inputNamaKader.setError("belum diisi");
            inputNamaKader.requestFocus();
        } else if (nip.isEmpty()) {
            inputNip.setError("belum diisi");
            inputNip.requestFocus();
        } else if (uName.isEmpty()) {
            inputUname.setError("belum diisi");
            inputUname.requestFocus();
        } else if (pass.isEmpty()) {
            inputPass.setError("belum diisi");
            inputPass.requestFocus();
        } else if (alamatKader.isEmpty()) {
            inputAlamatKader.setError("belum diisi");
            inputAlamatKader.requestFocus();
        } else if (noTelpKader.isEmpty()) {
            inputNoTelpKaderu.setError("belum diisi");
            inputNoTelpKaderu.requestFocus();
        }
        ApiService apiServicePasien = InitLibrary.getInstance();

        Call<ResponseUsersKaderGet> callPasien = apiServicePasien.addKader(idPos,namaKader,nip,uName,pass,alamatKader,noTelpKader);
        callPasien.enqueue(new Callback<ResponseUsersKaderGet>() {
            @Override
            public void onResponse(Call<ResponseUsersKaderGet> call, Response<ResponseUsersKaderGet> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), KaderActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(KaderActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUsersKaderGet> call, Throwable t) {
                Toast.makeText(KaderActivity.this, "failed add petugas", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
