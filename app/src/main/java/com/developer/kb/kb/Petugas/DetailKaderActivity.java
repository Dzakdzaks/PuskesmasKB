package com.developer.kb.kb.Petugas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Bidan.BidanActivity;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Kader.Kadersss;
import com.developer.kb.kb.Petugas.response.ResponseKader;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKaderActivity extends AppCompatActivity {

    @BindView(R.id.tvNamaPetugas)
    TextView tvNama;
    @BindView(R.id.tvPos)
    TextView tvPosyandu;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.tvNo)
    TextView tvNo;
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;

    UsersSession session;
    @BindView(R.id.tvNip)
    TextView tvNip;
    @BindView(R.id.tvPOS)
    TextView tvPOS;
    @BindView(R.id.tvEditKader)
    TextView tvEditKader;
    @BindView(R.id.tvHapusKader)
    TextView tvHapusKader;

    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, card_open, card_close;

    String idUsers, idPosyandu, namaPos, namaKader, nipKader, username, password, alamat, noTelp, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kader);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UsersSession(this);
        String idRole = session.getSpIdRole();
        if (idRole.equals("2") || idRole.equals("3") || idRole.equals("4")) {
            fab.setVisibility(View.GONE);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        card_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_open);
        card_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_close);

        idUsers = getIntent().getStringExtra("id_users");
        idPosyandu = getIntent().getStringExtra("id_posyandu");
        namaPos = getIntent().getStringExtra("nama_pos");
        namaKader = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat");
        noTelp = getIntent().getStringExtra("no_telp");
        nipKader = getIntent().getStringExtra("nip");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        role = getIntent().getStringExtra("role");

        String r = getString(R.string.title_activity_detail_kader);
        getSupportActionBar().setTitle(r + " " + namaKader);

        tvNama.setText(namaKader);
        tvPosyandu.setText(namaPos);
        tvAlamat.setText(alamat);
        tvNip.setText(nipKader);
        tvNo.setText(noTelp);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditKader();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailKaderActivity.this);
                alert.setCancelable(true);
                if (role.equals("3")){
                    alert.setTitle("Hapus Bidan");
                } else if (role.equals("4")){
                    alert.setTitle("Hapus Kader");
                } else {
                    alert.setTitle("Hapus Petugas");
                }
                alert.setIcon(R.drawable.warning);
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteKader();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();

            }
        });

        if (role.equals("3")) {
            tvPOS.setVisibility(View.GONE);
            tvPosyandu.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Detail Bidan " + namaKader);
            tvEditKader.setText("Edit Bidan");
            tvHapusKader.setText("Hapus Bidan");
        } else if (role.equals("4")){
            tvPOS.setVisibility(View.GONE);
            tvPosyandu.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Detail Kader " + namaKader);
            tvEditKader.setText("Edit Kader");
            tvHapusKader.setText("Hapus Kader");
        }

    }

    public void animateFAB() {
        /*jika fab dalam keadaan false*/
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            card1.startAnimation(card_close);
            card2.startAnimation(card_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            /*jika dalam keadaan true*/
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            card1.startAnimation(card_open);
            card2.startAnimation(card_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    private void getEditKader() {
        Intent i = new Intent(getApplicationContext(), EditKaderActivity.class);
        i.putExtra("id_users", idUsers);
        i.putExtra("id_pos", idPosyandu);
        i.putExtra("nama_pos", namaPos);
        i.putExtra("nama", namaKader);
        i.putExtra("nip", nipKader);
        i.putExtra("alamat", alamat);
        i.putExtra("no_telp", noTelp);
        i.putExtra("username", username);
        i.putExtra("password", password);
        i.putExtra("role", role);
        Toast.makeText(getApplicationContext(), namaKader + " clicked", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    private void deleteKader() {

        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseKader> call = apiService.deleteKader(idUsers);

        call.enqueue(new Callback<ResponseKader>() {
            @Override
            public void onResponse(Call<ResponseKader> call, Response<ResponseKader> response) {
                String msg = response.body().getMsg();
                if (role.equals("3")) {
                    startActivity(new Intent(getApplicationContext(), BidanActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), "Sukses delete bidan", Toast.LENGTH_SHORT).show();
                } else if (role.equals("4")){
                    startActivity(new Intent(getApplicationContext(), Kadersss.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), KaderActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseKader> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail delete pasien", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
