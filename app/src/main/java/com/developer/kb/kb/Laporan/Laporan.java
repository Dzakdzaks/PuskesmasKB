package com.developer.kb.kb.Laporan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.developer.kb.kb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Laporan extends AppCompatActivity {


    @BindView(R.id.cardKartu)
    CardView cardKartu;
    @BindView(R.id.cardLayanan)
    CardView cardLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @OnClick({R.id.cardKartu, R.id.cardLayanan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardKartu:
                startActivity(new Intent(getApplicationContext(), LaporanKartu.class));
                break;
            case R.id.cardLayanan:
                startActivity(new Intent(getApplicationContext(), LaporanPelayanan.class));
                break;
        }
    }
}
