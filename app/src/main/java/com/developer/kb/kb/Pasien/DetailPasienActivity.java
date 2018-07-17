package com.developer.kb.kb.Pasien;

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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Pasien.response.ResponseAddPasien;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPasienActivity extends AppCompatActivity {

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

    UsersSession usersSession;

    String idPasien, nama, no, usia, alamat, pend, pek, anak, ttl;
    String idPosyandu, namaPosyandu;
    String namaMetode;
    String itemHamil, itemGakin, itemPus4t;

    @BindView(R.id.posyandu)
    TextView posyandu;
    @BindView(R.id.hamil)
    TextView hamil;
    @BindView(R.id.gakin)
    TextView gakin;
    @BindView(R.id.pus4t)
    TextView pus4t;
    @BindView(R.id.metode)
    TextView metode;
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, card_open, card_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        usersSession = new UsersSession(this);
        String idRole = usersSession.getSpIdRole();
        if (idRole.equals("4")) {
            fab2.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
        }
//        else if (idRole.equals("4")){
//            fab.setVisibility(View.GONE);
//        }


        inputIdPasien.setEnabled(false);
        inputNamaPasien.setEnabled(false);
        inputAlamatPasien.setEnabled(false);
        inputAnakPasien.setEnabled(false);
        inputNoTelponPasien.setEnabled(false);
        inputPekerjaanPasien.setEnabled(false);
        inputPendidikanPasien.setEnabled(false);
        inputUmurPasien.setEnabled(false);
        inputIdPasien.setFocusable(false);
        inputNamaPasien.setFocusable(false);
        inputAlamatPasien.setFocusable(false);
        inputAnakPasien.setFocusable(false);
        inputNoTelponPasien.setFocusable(false);
        inputPekerjaanPasien.setFocusable(false);
        inputPendidikanPasien.setFocusable(false);
        inputUmurPasien.setFocusable(false);
        tvTtl.setVisibility(View.VISIBLE);
        spinnerIdPos.setVisibility(View.GONE);
        spinnerHamil.setVisibility(View.GONE);
        spinnerGakin.setVisibility(View.GONE);
        spinnerPus4t.setVisibility(View.GONE);
        spinnerMetode.setVisibility(View.GONE);

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

        String title = getString(R.string.title_activity_detail_pasien);
        getSupportActionBar().setTitle(title + " " + nama);


        inputIdPasien.setText(idPasien);
        inputNamaPasien.setText(nama);
        inputAlamatPasien.setText(alamat);
        inputAnakPasien.setText(anak);
        inputNoTelponPasien.setText(no);
        inputPekerjaanPasien.setText(pek);
        inputPendidikanPasien.setText(pend);
        inputUmurPasien.setText(usia);
        tvTtl.setText(ttl);

        posyandu.setText("Posyandu : " + namaPosyandu);

        hamil.setText("Hamil : " + itemHamil);

        gakin.setText("Gakin : " + itemGakin);

        pus4t.setText("Hamil : " + itemPus4t);

        metode.setText("Metode : " + namaMetode);


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditPasien();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailPasienActivity.this);
                alert.setCancelable(true);
                alert.setTitle("Hapus Pasien");
                alert.setIcon(R.drawable.warning);
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePasienKu();
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

    private void getEditPasien() {
        Intent i = new Intent(getApplicationContext(), EditPasienActivity.class);
        i.putExtra("id_pasien", idPasien);
        i.putExtra("id_pos", idPosyandu);
        i.putExtra("nama_pos", namaPosyandu);
        i.putExtra("nama", nama);
        i.putExtra("no_telp", no);
        i.putExtra("tgl_lahir", ttl);
        i.putExtra("umur", usia);
        i.putExtra("alamat", alamat);
        i.putExtra("pendidikan", pend);
        i.putExtra("pekerjaan", pek);
        i.putExtra("jumlah_anak", anak);
        i.putExtra("hamil", itemHamil);
        i.putExtra("gakin", itemGakin);
        i.putExtra("pus4t", itemPus4t);
        i.putExtra("metode", namaMetode);
        Toast.makeText(getApplicationContext(), nama + " clicked", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    private void deletePasienKu() {
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseAddPasien> call = apiService.deletePasien(idPasien);

        call.enqueue(new Callback<ResponseAddPasien>() {
            @Override
            public void onResponse(Call<ResponseAddPasien> call, Response<ResponseAddPasien> response) {
                String msg = response.body().getMsg();
                startActivity(new Intent(getApplicationContext(), PasienActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseAddPasien> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail delete pasien", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
