package com.developer.kb.kb.Posyandu;

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
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Login.users.ResponseUsersKaderGet;
import com.developer.kb.kb.Login.users.User;
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

public class EditPosyanduActivity extends AppCompatActivity {
    String idPosyandu, idPetugas, namaPosyandu, namaPetugas, alamat, noTelp;
    @BindView(R.id.input_nama_pos)
    EditText inputNamaPos;
    @BindView(R.id.input_alamat_pos)
    EditText inputAlamatPos;
    @BindView(R.id.input_no_pos)
    EditText inputNoPos;

    String idMetode, namaMetode;
    List<String> valueIdMetode = new ArrayList<String>();
    List<String> valueNamaMetode = new ArrayList<String>();

    UsersSession usersSession;
    @BindView(R.id.spinner_id_pet)
    Spinner spinnerIdPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posyandu);
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

        idPetugas = getIntent().getStringExtra("id_petugas");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPosyandu = getIntent().getStringExtra("nama_pos");
        namaPetugas = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat_pos");
        noTelp = getIntent().getStringExtra("no_telp_pos");

        String title = getString(R.string.title_activity_edit_posyandu);
        getSupportActionBar().setTitle(title + " " + namaPosyandu);

        inputNamaPos.setText(namaPosyandu);
        inputAlamatPos.setText(alamat);
        inputNoPos.setText(noTelp);
        getSpinnerPet();

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
                postEditPosyandu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSpinnerPet() {
        String id = usersSession.getSpIdUsers();
// get spinner metode
        ApiService apiServiceMetode = InitLibrary.getInstance();

        Call<ResponseUsersKaderGet> callMetode = apiServiceMetode.getKader(id);

        callMetode.enqueue(new Callback<ResponseUsersKaderGet>() {
            @Override
            public void onResponse(Call<ResponseUsersKaderGet> call, Response<ResponseUsersKaderGet> response) {
                final List<User> metodeList = response.body().getUsers();
                valueIdMetode = new ArrayList<String>();
                valueNamaMetode = new ArrayList<String>();

                for (int i = 0; i < metodeList.size(); i++) {
                    valueIdMetode.add(metodeList.get(i).getIdUsers());
                    valueNamaMetode.add(metodeList.get(i).getNama());
                }


                ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, valueNamaMetode);
                spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIdPet.setAdapter(spinnerPosyanduAdapter);
                if (namaPetugas != null) {
                    int spinnerPosition = spinnerPosyanduAdapter.getPosition(namaPetugas);
                    spinnerIdPet.setSelection(spinnerPosition);
                }
                spinnerIdPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            public void onFailure(Call<ResponseUsersKaderGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

            }
        });
        // get spinner metode
    }
    private void postEditPosyandu() {
        namaPosyandu = inputNamaPos.getText().toString();
        noTelp = inputNoPos.getText().toString();
        alamat = inputAlamatPos.getText().toString();


        if (namaPosyandu.isEmpty()) {
            inputNamaPos.setError("belum diisi");
            inputNamaPos.requestFocus();
        } else if (noTelp.isEmpty()) {
            inputNoPos.setError("belum diisi");
            inputNoPos.requestFocus();
        } else if (alamat.isEmpty()) {
            inputAlamatPos.setError("belum diisi");
            inputAlamatPos.requestFocus();
        }

        ApiService apiServicePasien = InitLibrary.getInstance();

        Call<ResponsePosyandu> callPasien = apiServicePasien.editPosyandu(idPosyandu,idMetode,namaPosyandu,alamat,noTelp);

        callPasien.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PosyanduActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed edit posyandu", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
