package com.developer.kb.kb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.developer.kb.kb.Bidan.BidanActivity;
import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Grafik.GrafikActivity;
import com.developer.kb.kb.Kader.Kadersss;
import com.developer.kb.kb.Laporan.Laporan;
import com.developer.kb.kb.Petugas.KaderActivity;
import com.developer.kb.kb.Layanan.LayananActivity;
import com.developer.kb.kb.Pasien.PasienActivity;
import com.developer.kb.kb.Posyandu.PosyanduActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.llProgressBar)
    LinearLayout llProgressBar;
    @BindView(R.id.cardPasien)
    CardView cardPasien;
    @BindView(R.id.cardGrafik)
    CardView cardGrafik;
    @BindView(R.id.cardLayanan)
    CardView cardLayanan;
    @BindView(R.id.cardLaporan)
    CardView cardLaporan;
    @BindView(R.id.cardPosyandu)
    CardView cardPosyandu;
    @BindView(R.id.cardKader)
    CardView cardKader;
    @BindView(R.id.cardBidan)
    CardView cardBidan;
    @BindView(R.id.cardAbout)
    CardView cardAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setTitle("Log Out");
        alert.setMessage("Are You Sure ?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UsersSession sesi = new UsersSession(HomeActivity.this);
                sesi.logout(); //logout
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    @OnClick({R.id.cardPasien, R.id.cardGrafik, R.id.cardLayanan, R.id.cardLaporan, R.id.cardPosyandu, R.id.cardKader, R.id.cardBidan, R.id.cardAbout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardPasien:
                startActivity(new Intent(getApplicationContext(), PasienActivity.class));
                break;
            case R.id.cardGrafik:
                startActivity(new Intent(getApplicationContext(), GrafikActivity.class));
                break;
            case R.id.cardLayanan:
                startActivity(new Intent(getApplicationContext(), LayananActivity.class));
                break;
            case R.id.cardLaporan:
                startActivity(new Intent(getApplicationContext(), Laporan.class));
                break;
            case R.id.cardPosyandu:
                startActivity(new Intent(getApplicationContext(), PosyanduActivity.class));
                break;
            case R.id.cardKader:
                startActivity(new Intent(getApplicationContext(), KaderActivity.class));
                break;
            case R.id.cardBidan:
                startActivity(new Intent(getApplicationContext(), BidanActivity.class));
                break;
            case R.id.cardAbout:
                startActivity(new Intent(getApplicationContext(), Kadersss.class));
                break;
        }
    }
}
