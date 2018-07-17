package com.developer.kb.kb.Petugas;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Bidan.BidanActivity;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Kader.Kadersss;
import com.developer.kb.kb.Petugas.response.ResponseKader;
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

public class EditKaderActivity extends AppCompatActivity {

    String idUsers, idPosyandu, namaPos, namaKader, nipKader, username, password, alamat, noTelp, role;

    @BindView(R.id.input_nama_kader)
    EditText inputNamaKader;
    @BindView(R.id.input_nip_kader)
    EditText inputNipKader;
    @BindView(R.id.spinner_id_pet)
    Spinner spinnerIdPet;
    @BindView(R.id.input_alamat_kader)
    EditText inputAlamatKader;
    @BindView(R.id.input_no_kader)
    EditText inputNoKader;
    @BindView(R.id.input_uname_kader)
    EditText inputUnameKader;
    @BindView(R.id.input_pass_kader)
    EditText inputPassKader;


    String idPos, namePos;
    List<String> valueIdPos = new ArrayList<String>();
    List<String> valueNamaPos = new ArrayList<String>();

    UsersSession usersSession;
    @BindView(R.id.tvPosss)
    TextView tvPosss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kader);
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

        idUsers = getIntent().getStringExtra("id_users");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPos = getIntent().getStringExtra("nama_pos");
        namaKader = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat");
        noTelp = getIntent().getStringExtra("no_telp");
        nipKader = getIntent().getStringExtra("nip");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        role = getIntent().getStringExtra("role");

        String title = getString(R.string.title_activity_edit_kader);
        getSupportActionBar().setTitle(title + " " + namaKader);

        inputNamaKader.setText(namaKader);
        inputNipKader.setText(nipKader);
        inputAlamatKader.setText(alamat);
        inputNoKader.setText(noTelp);
        inputUnameKader.setText(username);
        inputPassKader.setText(password);
        getSpinnerPos();

        if (role.equals("3")) {
            spinnerIdPet.setVisibility(View.GONE);
            tvPosss.setVisibility(View.GONE);
            idPos = "0";
            getSupportActionBar().setTitle("Edit Bidan " + namaKader);
        } else if (role.equals("4")){
            spinnerIdPet.setVisibility(View.GONE);
            tvPosss.setVisibility(View.GONE);
            idPos = "0";
            getSupportActionBar().setTitle("Edit Kader " + namaKader);
        }
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
                postEditKader();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSpinnerPos() {
        String id = usersSession.getSpIdUsers();

        ApiService apiServiceMetode = InitLibrary.getInstance();

        Call<ResponsePosyandu> callMetode = apiServiceMetode.getPosyandu(id);

        callMetode.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                final List<Posyandu> metodeList = response.body().getPosyandu();
                valueIdPos = new ArrayList<String>();
                valueNamaPos = new ArrayList<String>();

                for (int i = 0; i < metodeList.size(); i++) {
                    valueIdPos.add(metodeList.get(i).getIdPos());
                    valueNamaPos.add(metodeList.get(i).getNamaPos());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, valueNamaPos);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIdPet.setAdapter(spinnerPosyanduAdapter);
                if (namaPos != null) {
                    int spinnerPosition = spinnerPosyanduAdapter.getPosition(namaPos);
                    spinnerIdPet.setSelection(spinnerPosition);
                }
                spinnerIdPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idPos = valueIdPos.get(position);
                        namePos = valueNamaPos.get(position);
//                        Toast.makeText(PasienActivity.this, idMetode + " " + namaMetode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                Toast.makeText(PasienActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postEditKader() {
        namaKader = inputNamaKader.getText().toString();
        nipKader = inputNipKader.getText().toString();
        alamat = inputAlamatKader.getText().toString();
        noTelp = inputNoKader.getText().toString();
        username = inputUnameKader.getText().toString();
        password = inputPassKader.getText().toString();


        if (namaKader.isEmpty()) {
            inputNamaKader.setError("belum diisi");
            inputNamaKader.requestFocus();
        } else if (nipKader.isEmpty()) {
            inputNipKader.setError("belum diisi");
            inputNipKader.requestFocus();
        } else if (alamat.isEmpty()) {
            inputAlamatKader.setError("belum diisi");
            inputAlamatKader.requestFocus();
        } else if (noTelp.isEmpty()) {
            inputNoKader.setError("belum diisi");
            inputNoKader.requestFocus();
        } else if (username.isEmpty()) {
            inputUnameKader.setError("belum diisi");
            inputUnameKader.requestFocus();
        } else if (password.isEmpty()) {
            inputPassKader.setError("belum diisi");
            inputPassKader.requestFocus();
        }

        ApiService apiServicePasien = InitLibrary.getInstance();

        Call<ResponseKader> callPasien = apiServicePasien.editKader(idUsers, idPos, namaKader, nipKader, username, password, alamat, noTelp);

        callPasien.enqueue(new Callback<ResponseKader>() {
            @Override
            public void onResponse(Call<ResponseKader> call, Response<ResponseKader> response) {
                String msg = response.body().getMsg();
                if (role.equals("3")){
                    startActivity(new Intent(getApplicationContext(), BidanActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), "Sukses edit bidan", Toast.LENGTH_SHORT).show();

                } else if (role.equals("4")){
                    startActivity(new Intent(getApplicationContext(), Kadersss.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), KaderActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKader> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed edit kader", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
