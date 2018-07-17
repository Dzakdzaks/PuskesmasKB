package com.developer.kb.kb.Posyandu;

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

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Posyandu.response.ResponsePosyandu;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPosyanduActivity extends AppCompatActivity {
    String idPosyandu, idPetugas, namaPosyandu, namaPetugas, alamat, noTelp;
    @BindView(R.id.tvPosyandu)
    TextView tvPosyandu;
    @BindView(R.id.tvPetugas)
    TextView tvPetugas;
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

    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, card_open, card_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_posyandu);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        session = new UsersSession(this);
        String idRole = session.getSpIdRole();
        if (idRole.equals("2") || idRole.equals("3")) {
            fab2.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
        } else if (idRole.equals("4")){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                animateFAB();
            }
        });
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        card_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_open);
        card_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_close);


        idPetugas = getIntent().getStringExtra("id_petugas");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPosyandu = getIntent().getStringExtra("nama_pos");
        namaPetugas = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat_pos");
        noTelp = getIntent().getStringExtra("no_telp_pos");

        String r = getString(R.string.title_activity_detail_posyandu);
        getSupportActionBar().setTitle(r + " " + namaPosyandu);

        tvPosyandu.setText(namaPosyandu);
        tvPetugas.setText(namaPetugas);
        tvAlamat.setText(alamat);
        tvNo.setText(noTelp);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditPos();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailPosyanduActivity.this);
                alert.setCancelable(true);
                alert.setTitle("Hapus Posyandu");
                alert.setIcon(R.drawable.warning);
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePos();
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

    private void getEditPos() {
        Intent i = new Intent(getApplicationContext(), EditPosyanduActivity.class);
        i.putExtra("id_pos", idPosyandu);
        i.putExtra("nama_pos", namaPosyandu);
        i.putExtra("nama", namaPetugas);
        i.putExtra("id_petugas", idPetugas);
        i.putExtra("alamat_pos", alamat);
        i.putExtra("no_telp_pos", noTelp);
        Toast.makeText(getApplicationContext(), namaPosyandu + " clicked", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    private void deletePos() {

        ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePosyandu> call = apiService.deletePosyandu(idPosyandu);

        call.enqueue(new Callback<ResponsePosyandu>() {
            @Override
            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PosyanduActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail delete pasien", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
