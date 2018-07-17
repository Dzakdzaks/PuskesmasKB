package com.developer.kb.kb.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.HomeActivity;
import com.developer.kb.kb.Login.response.ResponseLogin;
import com.developer.kb.kb.Login.response.Users;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_pass)
    EditText inputPass;
    @BindView(R.id.btn_login)
    Button btnLogin;

    UsersSession session;
    @BindView(R.id.llProgressBar)
    LinearLayout llProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new UsersSession(this);

        if (session.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
//        Intent newIntent = new Intent(LoginActivity.this, HomeActivity.class);
//        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(newIntent);
        requestLogin();
    }

    private void requestLogin() {
        String username = inputName.getText().toString();
        String password = inputPass.getText().toString();
        llProgressBar.setVisibility(View.VISIBLE);
        if (username.isEmpty()) {
            llProgressBar.setVisibility(View.GONE);
            inputName.setError("Belum Diisi!");
            inputName.requestFocus();
        } else if (password.isEmpty()) {
            llProgressBar.setVisibility(View.GONE);
            inputPass.setError("Belum Diisi!");
            inputPass.requestFocus();
        } else if (!username.isEmpty() && !password.isEmpty()) {
            ApiService apiService = InitLibrary.getInstance();
            Call<ResponseLogin> callLogin = apiService.requestlogin(username, password);
            callLogin.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.isSuccessful()) {
                        llProgressBar.setVisibility(View.GONE);
                        String stats = String.valueOf(response.body().getError());
                        if (stats.equals("false")) {
                            Users users = response.body().getUsers();
                            String idUsers = users.getIdUsers();
                            String idPos = null;
                            if(users.getIdPos() == null){
                                idPos = "0";
                            } else {
                                idPos = users.getIdPos();
                            }
                            String idRole = users.getIdRole();
                            String nama = users.getNama();
                            String nip = users.getNip();
                            String uName = users.getUsername();
                            String pass = users.getPassword();
                            String alamat = users.getAlamat();
                            String no = users.getNomorTelpon();

                            Log.d("pos", idPos);

                            session.saveSPString(UsersSession.SP_ID_USERS, idUsers);
                            session.saveSPString(UsersSession.SP_ID_POS, idPos);
                            session.saveSPString(UsersSession.SP_ID_ROLE, idRole);
                            session.saveSPString(UsersSession.SP_NAME, nama);
                            session.saveSPString(UsersSession.SP_NIP, nip);
                            session.saveSPString(UsersSession.SP_USERNAME, uName);
                            session.saveSPString(UsersSession.SP_PASSWORD, pass);
                            session.saveSPString(UsersSession.SP_ADDRESS, alamat);
                            session.saveSPString(UsersSession.SP_NO, no);
                            session.saveSPBoolean(UsersSession.SP_SUDAH_LOGIN, true);

                            System.out.println("nama users : " + nama);

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            Toast.makeText(LoginActivity.this, "Success login!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            llProgressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        llProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Failed Login, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    llProgressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }
}
