package com.developer.kb.kb.Layanan;

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
import com.developer.kb.kb.Layanan.response.ResponseLayanan;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLayananActivity extends AppCompatActivity {

    @BindView(R.id.tvEditLayanan)
    TextView tvEditLayanan;
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.tvHapusLayanan)
    TextView tvHapusLayanan;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;

    UsersSession usersSession;

    @BindView(R.id.tvNamaPetugas)
    TextView tvNamaPetugas;
    @BindView(R.id.tvNamaPasien)
    TextView tvNamaPasien;
    @BindView(R.id.tvPosyandu)
    TextView tvPosyandu;
    @BindView(R.id.tvTtlKunjungan)
    TextView tvTtlKunjungan;
    @BindView(R.id.tvHaidTerakhir)
    TextView tvHaidTerakhir;
    @BindView(R.id.tvBB)
    TextView tvBB;
    @BindView(R.id.tvTekDarah)
    TextView tvTekDarah;
    @BindView(R.id.tvSakKuning)
    TextView tvSakKuning;
    @BindView(R.id.tvPendarahan)
    TextView tvPendarahan;
    @BindView(R.id.tvTumor)
    TextView tvTumor;
    @BindView(R.id.tvHivAids)
    TextView tvHivAids;
    @BindView(R.id.tvPosRahim)
    TextView tvPosRahim;
    @BindView(R.id.tvDiabetes)
    TextView tvDiabetes;
    @BindView(R.id.tvPembDarah)
    TextView tvPembDarah;
    @BindView(R.id.tvES)
    TextView tvES;
    @BindView(R.id.tvKomplikasi)
    TextView tvKomplikasi;
    @BindView(R.id.tvTtlKembali)
    TextView tvTtlKembali;
    @BindView(R.id.tvMetode)
    TextView tvMetode;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, card_open, card_close;


    String itemKuning, itemPendarahan, itemHIV, itemDiabetes, itemPembDarah;
    String namaPetugas, BB, tekananDarah, ES, komplikasi, ttlKunjungan, ttlKembali, ttlHaid, idLayanan, idPosyandu, namaPosyandu;
    String idPasien, namaPasien;
    String namaTumor;
    String namaPosisi;
    String namaStatus;
    String namaMetode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        card_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_open);
        card_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_close);

        usersSession = new UsersSession(this);
        String idRole = usersSession.getSpIdRole();
        if (idRole.equals("4") ) {
            fab.setVisibility(View.GONE);
        }

        idLayanan = getIntent().getStringExtra("id_layanan");
        idPasien = getIntent().getStringExtra("id_pas");
        idPosyandu = getIntent().getStringExtra("id_pos");
        namaPasien = getIntent().getStringExtra("nama");
        namaPetugas = getIntent().getStringExtra("nama_pet");
        namaPosyandu = getIntent().getStringExtra("nama_pos");
        ttlKunjungan = getIntent().getStringExtra("tgl_kunjungan");
        ttlKembali = getIntent().getStringExtra("tgl_kembali");
        ttlHaid = getIntent().getStringExtra("haid_terakhir");
        BB = getIntent().getStringExtra("berat_badan");
        tekananDarah = getIntent().getStringExtra("tekanan_darah");
        itemKuning = getIntent().getStringExtra("sakit_kuning");
        itemPendarahan = getIntent().getStringExtra("pendarahan");
        namaTumor = getIntent().getStringExtra("tumor");
        itemHIV = getIntent().getStringExtra("hiv_aids");
        namaPosisi = getIntent().getStringExtra("posisi_rahim");
        itemDiabetes = getIntent().getStringExtra("diabetes");
        itemPembDarah = getIntent().getStringExtra("pembekuan_darah");
        komplikasi = getIntent().getStringExtra("komplikasi");
        namaMetode = getIntent().getStringExtra("metode");
        namaStatus = getIntent().getStringExtra("status");
        ES = getIntent().getStringExtra("ES");

        String title = getString(R.string.title_activity_detail_layanan);
        getSupportActionBar().setTitle(title + " " + namaPasien);

        tvNamaPetugas.setText(namaPetugas);
        tvNamaPasien.setText(namaPasien);
        tvPosyandu.setText(namaPosyandu);
        tvTtlKunjungan.setText(ttlKunjungan);
        tvHaidTerakhir.setText(ttlHaid);
        tvBB.setText(BB + "kg");
        tvTekDarah.setText(tekananDarah);
        tvSakKuning.setText(itemKuning);
        tvPendarahan.setText(itemPendarahan);
        tvTumor.setText(namaTumor);
        tvHivAids.setText(itemHIV);
        tvDiabetes.setText(itemDiabetes);
        tvES.setText(ES);
        tvPosRahim.setText(namaPosisi);
        tvKomplikasi.setText(komplikasi);
        tvTtlKembali.setText(ttlKembali);
        tvMetode.setText(namaMetode);
        tvStatus.setText(namaStatus);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLayanan();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailLayananActivity.this);
                alert.setCancelable(true);
                alert.setTitle("Hapus Layanan");
                alert.setIcon(R.drawable.warning);
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteLayananKu();
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

    private void editLayanan(){
        Intent i = new Intent(DetailLayananActivity.this, EditLayananActivity.class);
        i.putExtra("id_layanan", idLayanan);
        i.putExtra("id_pas", idPasien);
        i.putExtra("id_pos", idPosyandu);
        i.putExtra("nama", namaPasien);
        i.putExtra("nama_pet", namaPetugas);
        i.putExtra("nama_pos", namaPosyandu);
        i.putExtra("tgl_kunjungan", ttlKunjungan);
        i.putExtra("tgl_kembali", ttlKembali);
        i.putExtra("haid_terakhir", ttlHaid);
        i.putExtra("berat_badan", BB);
        i.putExtra("tekanan_darah", tekananDarah);
        i.putExtra("sakit_kuning", itemKuning);
        i.putExtra("pendarahan", itemPendarahan);
        i.putExtra("tumor", namaTumor);
        i.putExtra("hiv_aids", itemHIV);
        i.putExtra("posisi_rahim", namaPosisi);
        i.putExtra("diabetes", itemDiabetes);
        i.putExtra("pembekuan_darah", itemPembDarah);
        i.putExtra("komplikasi", komplikasi);
        i.putExtra("metode", namaMetode);
        i.putExtra("ES", ES);
        i.putExtra("status", namaStatus);
        startActivity(i);
    }

    private void deleteLayananKu() {
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseLayanan> call = apiService.deleteLayanan(idLayanan);

        call.enqueue(new Callback<ResponseLayanan>() {
            @Override
            public void onResponse(Call<ResponseLayanan> call, Response<ResponseLayanan> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), LayananActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseLayanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail delete layanan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
