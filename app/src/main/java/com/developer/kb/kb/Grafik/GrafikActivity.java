package com.developer.kb.kb.Grafik;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Grafik.response.ResponseGrafikMetode;
import com.developer.kb.kb.Grafik.response.ResponseGrafikStatus;
import com.developer.kb.kb.R;
import com.developer.kb.kb.Retrofit.ApiService;
import com.developer.kb.kb.Retrofit.InitLibrary;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrafikActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.chart)
    BarChart chart;
    UsersSession usersSession;
    float barWidth;
    float barSpace;
    float groupSpace;
    int jumlah;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_grafik);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usersSession = new UsersSession(this);
        String title = getString(R.string.title_activity_grafik);
        getSupportActionBar().setTitle(title + " Status");
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
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
                getchart();
                getChartMetode();
            }
        });



    }
    @Override
    public void onRefresh() {
        getchart();
        getChartMetode();
    }

    private void getChartMetode() {
        barWidth = 0.3f;
        barSpace = 0.01f;
        groupSpace = 0.08f;

        String id = usersSession.getSpIdUsers();
        ApiService apiService1 = InitLibrary.getInstance();
        Call<ResponseGrafikMetode> call1 = apiService1.getGrafikMetode(id);

        call1.enqueue(new Callback<ResponseGrafikMetode>() {
            @Override
            public void onResponse(Call<ResponseGrafikMetode> call, Response<ResponseGrafikMetode> response) {
//                        ArrayList posList = new ArrayList();
//                        ArrayList kbPascaPersalinanList = new ArrayList();
                swipeRefresh.setRefreshing(false);

                List<String> responseKBAktif = response.body().getPil();

                List<String> responseKBPascaPersalinan = response.body().getSuntik();

                List<String> responsePus4tberKB = response.body().getKondom();

                List<String> responsePos = response.body().getPosyandu();

                jumlah = response.body().getJmlPos();


                String kbaktif1 = responseKBAktif.get(0);
                String kbpasca1 = responseKBPascaPersalinan.get(0);
                String kbpus4t1 = responsePus4tberKB.get(0);
                float fKbaktif1 = 0, fkbpasca1 = 0, fkbpus4t1 = 0;
                if (kbaktif1 == null || kbpasca1 == null || kbpus4t1 == null) {
                    fKbaktif1 = 0;
                    fkbpasca1 = 0;
                    fkbpus4t1 = 0;
                } else {
                    fKbaktif1 = Float.valueOf(kbaktif1);
                    fkbpasca1 = Float.valueOf(kbpasca1);
                    fkbpus4t1 = Float.valueOf(kbpus4t1);
                }


                String kbaktif2 = responseKBAktif.get(1);
                String kbpasca2 = responseKBPascaPersalinan.get(1);
                String kbpus4t2 = responsePus4tberKB.get(1);
                float fKbaktif2 = 0, fkbpasca2 = 0, fkbpus4t2 = 0;
                if (kbaktif2 == null || kbpasca2 == null || kbpus4t2 == null) {
                    fKbaktif2 = 0;
                    fkbpasca2 = 0;
                    fkbpus4t2 = 0;
                } else {
                    fKbaktif2 = Float.valueOf(kbaktif2);
                    fkbpasca2 = Float.valueOf(kbpasca2);
                    fkbpus4t2 = Float.valueOf(kbpus4t2);
                }

                String kbaktif3 = responseKBAktif.get(2);
                String kbpasca3 = responseKBPascaPersalinan.get(2);
                String kbpus4t3 = responsePus4tberKB.get(2);
                float fKbaktif3 = 0, fkbpasca3 = 0, fkbpus4t3 = 0;
                if (kbaktif3 == null || kbpasca3 == null || kbpus4t3 == null) {
                    fKbaktif3 = 0;
                    fkbpasca3 = 0;
                    fkbpus4t3 = 0;
                } else {
                    fKbaktif3 = Float.valueOf(kbaktif3);
                    fkbpasca3 = Float.valueOf(kbpasca3);
                    fkbpus4t3 = Float.valueOf(kbpus4t3);
                }

                String kbaktif4 = responseKBAktif.get(3);
                String kbpasca4 = responseKBPascaPersalinan.get(3);
                String kbpus4t4 = responsePus4tberKB.get(3);
                float fKbaktif4 = 0, fkbpasca4 = 0, fkbpus4t4 = 0;
                if (kbaktif4 == null || kbpasca4 == null || kbpus4t4 == null) {
                    fKbaktif4 = 0;
                    fkbpasca4 = 0;
                    fkbpus4t4 = 0;
                } else {
                    fKbaktif4 = Float.valueOf(kbaktif4);
                    fkbpasca4 = Float.valueOf(kbpasca4);
                    fkbpus4t4 = Float.valueOf(kbpus4t4);
                }

                String kbaktif5 = responseKBAktif.get(4);
                String kbpasca5 = responseKBPascaPersalinan.get(4);
                String kbpus4t5 = responsePus4tberKB.get(4);
                float fKbaktif5 = 0, fkbpasca5 = 0, fkbpus4t5 = 0;
                if (kbaktif5 == null || kbpasca5 == null || kbpus4t5 == null) {
                    fKbaktif5 = 0;
                    fkbpasca5 = 0;
                    fkbpus4t5 = 0;
                } else {
                    fKbaktif5 = Float.valueOf(kbaktif5);
                    fkbpasca5 = Float.valueOf(kbpasca5);
                    fkbpus4t5 = Float.valueOf(kbpus4t5);
                }

                String kbaktif6 = responseKBAktif.get(5);
                String kbpasca6 = responseKBPascaPersalinan.get(5);
                String kbpus4t6 = responsePus4tberKB.get(5);
                float fKbaktif6 = 0, fkbpasca6 = 0, fkbpus4t6 = 0;
                if (kbaktif6 == null || kbpasca6 == null || kbpus4t6 == null) {
                    fKbaktif6 = 0;
                    fkbpasca6 = 0;
                    fkbpus4t6 = 0;
                } else {
                    fKbaktif6 = Float.valueOf(kbaktif6);
                    fkbpasca6 = Float.valueOf(kbpasca6);
                    fkbpus4t6 = Float.valueOf(kbpus4t6);
                }

                String kbaktif7 = responseKBAktif.get(6);
                String kbpasca7 = responseKBPascaPersalinan.get(6);
                String kbpus4t7 = responsePus4tberKB.get(6);
                float fKbaktif7 = 0, fkbpasca7 = 0, fkbpus4t7 = 0;
                if (kbaktif7 == null || kbpasca7 == null || kbpus4t7 == null) {
                    fKbaktif7 = 0;
                    fkbpasca7 = 0;
                    fkbpus4t7 = 0;
                } else {
                    fKbaktif7 = Float.valueOf(kbaktif7);
                    fkbpasca7 = Float.valueOf(kbpasca7);
                    fkbpus4t7 = Float.valueOf(kbpus4t7);
                }

                ArrayList yVals1 = new ArrayList();
                ArrayList yVals2 = new ArrayList();
                ArrayList yVals3 = new ArrayList();


                yVals1.add(new BarEntry(1, (float) fKbaktif1));
                yVals2.add(new BarEntry(1, (float) fkbpasca1));
                yVals3.add(new BarEntry(1, (float) fkbpus4t1));
                yVals1.add(new BarEntry(2, (float) fKbaktif2));
                yVals2.add(new BarEntry(2, (float) fkbpasca2));
                yVals3.add(new BarEntry(2, (float) fkbpus4t2));
                yVals1.add(new BarEntry(3, (float) fKbaktif3));
                yVals2.add(new BarEntry(3, (float) fkbpasca3));
                yVals3.add(new BarEntry(3, (float) fkbpus4t3));
                yVals1.add(new BarEntry(4, (float) fKbaktif4));
                yVals2.add(new BarEntry(4, (float) fkbpasca4));
                yVals3.add(new BarEntry(4, (float) fkbpus4t4));
                yVals1.add(new BarEntry(5, (float) fKbaktif5));
                yVals2.add(new BarEntry(5, (float) fkbpasca5));
                yVals3.add(new BarEntry(5, (float) fkbpus4t5));
                yVals1.add(new BarEntry(6, (float) fKbaktif6));
                yVals2.add(new BarEntry(6, (float) fkbpasca6));
                yVals3.add(new BarEntry(6, (float) fkbpus4t6));
                yVals1.add(new BarEntry(7, (float) fKbaktif7));
                yVals2.add(new BarEntry(7, (float) fkbpasca7));
                yVals3.add(new BarEntry(7, (float) fkbpus4t7));

                BarDataSet set1, set2, set3;
                set1 = new BarDataSet(yVals1, "Pil");
                set1.setColor(Color.parseColor("#f35f6c"));
                set2 = new BarDataSet(yVals2, "Suntik");
                set2.setColor(Color.parseColor("#198c70"));
                set3 = new BarDataSet(yVals3, "Kondom");
                set3.setColor(Color.parseColor("#f6821f"));
                BarData data = new BarData(set1, set2, set3);
                data.setValueFormatter(new LargeValueFormatter());
                chart.setData(data);
                chart.getBarData().setBarWidth(barWidth);
                chart.animateY(3000);
                chart.getXAxis().setAxisMinimum(0);
                chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * jumlah);
                chart.getXAxis().setCenterAxisLabels(true);
                chart.groupBars(0, groupSpace, barSpace);
                chart.getData().setHighlightEnabled(false);
//                Description description = new Description();
//                description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
//                description.setText("Grafik Metode");
//                chart.setDescription(description);
                chart.invalidate();

                Legend l = chart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(true);
                l.setYOffset(20f);
                l.setXOffset(0f);
                l.setYEntrySpace(0f);
                l.setTextSize(8f);


                //X-axis
                XAxis xAxis = chart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setCenterAxisLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setAxisMaximum(jumlah);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(responsePos));
                System.out.println("pos \n" + responsePos);
                //Y-axis
                chart.getAxisRight().setEnabled(false);
                YAxis leftAxis = chart.getAxisLeft();
                leftAxis.setValueFormatter(new LargeValueFormatter());
                leftAxis.setDrawGridLines(true);
                leftAxis.setSpaceTop(35f);
                leftAxis.setAxisMinimum(0f);

            }

            @Override
            public void onFailure(Call<ResponseGrafikMetode> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(GrafikActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getchart() {
        barWidth = 0.3f;
        barSpace = 0.01f;
        groupSpace = 0.08f;

        String id = usersSession.getSpIdUsers();
        ApiService apiService1 = InitLibrary.getInstance();
        Call<ResponseGrafikStatus> call1 = apiService1.getGrafikStatus(id);

        call1.enqueue(new Callback<ResponseGrafikStatus>() {
            @Override
            public void onResponse(Call<ResponseGrafikStatus> call, Response<ResponseGrafikStatus> response) {
//                        ArrayList posList = new ArrayList();
//                        ArrayList kbPascaPersalinanList = new ArrayList();
                swipeRefresh.setRefreshing(false);
                assert response.body() != null;
                List<String> responseKBAktif = null;
                if (response.body() != null) {
                    responseKBAktif = response.body().getKbAktif();
                }
                List<String> responseKBPascaPersalinan = null;
                if (response.body() != null) {
                    responseKBPascaPersalinan = response.body().getKbPascaPersalinan();
                }
                List<String> responsePus4tberKB = null;
                if (response.body() != null) {
                    responsePus4tberKB = response.body().getPus4TBerkb();
                }
                List<String> responsePos = null;
                if (response.body() != null) {
                    responsePos = response.body().getPosyandu();
                }

                jumlah = response.body().getmJmlPos();


                String kbaktif1 = responseKBAktif.get(0);
                String kbpasca1 = responseKBPascaPersalinan.get(0);
                String kbpus4t1 = responsePus4tberKB.get(0);
                float fKbaktif1 = 0, fkbpasca1 = 0, fkbpus4t1 = 0;
                if (kbaktif1 == null || kbpasca1 == null || kbpus4t1 == null) {
                    fKbaktif1 = 0;
                    fkbpasca1 = 0;
                    fkbpus4t1 = 0;
                } else {
                    fKbaktif1 = Float.valueOf(kbaktif1);
                    fkbpasca1 = Float.valueOf(kbpasca1);
                    fkbpus4t1 = Float.valueOf(kbpus4t1);
                }


                String kbaktif2 = responseKBAktif.get(1);
                String kbpasca2 = responseKBPascaPersalinan.get(1);
                String kbpus4t2 = responsePus4tberKB.get(1);
                float fKbaktif2 = 0, fkbpasca2 = 0, fkbpus4t2 = 0;
                if (kbaktif2 == null || kbpasca2 == null || kbpus4t2 == null) {
                    fKbaktif2 = 0;
                    fkbpasca2 = 0;
                    fkbpus4t2 = 0;
                } else {
                    fKbaktif2 = Float.valueOf(kbaktif2);
                    fkbpasca2 = Float.valueOf(kbpasca2);
                    fkbpus4t2 = Float.valueOf(kbpus4t2);
                }

                String kbaktif3 = responseKBAktif.get(2);
                String kbpasca3 = responseKBPascaPersalinan.get(2);
                String kbpus4t3 = responsePus4tberKB.get(2);
                float fKbaktif3 = 0, fkbpasca3 = 0, fkbpus4t3 = 0;
                if (kbaktif3 == null || kbpasca3 == null || kbpus4t3 == null) {
                    fKbaktif3 = 0;
                    fkbpasca3 = 0;
                    fkbpus4t3 = 0;
                } else {
                    fKbaktif3 = Float.valueOf(kbaktif3);
                    fkbpasca3 = Float.valueOf(kbpasca3);
                    fkbpus4t3 = Float.valueOf(kbpus4t3);
                }

                String kbaktif4 = responseKBAktif.get(3);
                String kbpasca4 = responseKBPascaPersalinan.get(3);
                String kbpus4t4 = responsePus4tberKB.get(3);
                float fKbaktif4 = 0, fkbpasca4 = 0, fkbpus4t4 = 0;
                if (kbaktif4 == null || kbpasca4 == null || kbpus4t4 == null) {
                    fKbaktif4 = 0;
                    fkbpasca4 = 0;
                    fkbpus4t4 = 0;
                } else {
                    fKbaktif4 = Float.valueOf(kbaktif4);
                    fkbpasca4 = Float.valueOf(kbpasca4);
                    fkbpus4t4 = Float.valueOf(kbpus4t4);
                }

                String kbaktif5 = responseKBAktif.get(4);
                String kbpasca5 = responseKBPascaPersalinan.get(4);
                String kbpus4t5 = responsePus4tberKB.get(4);
                float fKbaktif5 = 0, fkbpasca5 = 0, fkbpus4t5 = 0;
                if (kbaktif5 == null || kbpasca5 == null || kbpus4t5 == null) {
                    fKbaktif5 = 0;
                    fkbpasca5 = 0;
                    fkbpus4t5 = 0;
                } else {
                    fKbaktif5 = Float.valueOf(kbaktif5);
                    fkbpasca5 = Float.valueOf(kbpasca5);
                    fkbpus4t5 = Float.valueOf(kbpus4t5);
                }

                String kbaktif6 = responseKBAktif.get(5);
                String kbpasca6 = responseKBPascaPersalinan.get(5);
                String kbpus4t6 = responsePus4tberKB.get(5);
                float fKbaktif6 = 0, fkbpasca6 = 0, fkbpus4t6 = 0;
                if (kbaktif6 == null || kbpasca6 == null || kbpus4t6 == null) {
                    fKbaktif6 = 0;
                    fkbpasca6 = 0;
                    fkbpus4t6 = 0;
                } else {
                    fKbaktif6 = Float.valueOf(kbaktif6);
                    fkbpasca6 = Float.valueOf(kbpasca6);
                    fkbpus4t6 = Float.valueOf(kbpus4t6);
                }

                String kbaktif7 = responseKBAktif.get(6);
                String kbpasca7 = responseKBPascaPersalinan.get(6);
                String kbpus4t7 = responsePus4tberKB.get(6);
                float fKbaktif7 = 0, fkbpasca7 = 0, fkbpus4t7 = 0;
                if (kbaktif7 == null || kbpasca7 == null || kbpus4t7 == null) {
                    fKbaktif7 = 0;
                    fkbpasca7 = 0;
                    fkbpus4t7 = 0;
                } else {
                    fKbaktif7 = Float.valueOf(kbaktif7);
                    fkbpasca7 = Float.valueOf(kbpasca7);
                    fkbpus4t7 = Float.valueOf(kbpus4t7);
                }

                ArrayList yVals1 = new ArrayList();
                ArrayList yVals2 = new ArrayList();
                ArrayList yVals3 = new ArrayList();


                yVals1.add(new BarEntry(1, (float) fKbaktif1));
                yVals2.add(new BarEntry(1, (float) fkbpasca1));
                yVals3.add(new BarEntry(1, (float) fkbpus4t1));
                yVals1.add(new BarEntry(2, (float) fKbaktif2));
                yVals2.add(new BarEntry(2, (float) fkbpasca2));
                yVals3.add(new BarEntry(2, (float) fkbpus4t2));
                yVals1.add(new BarEntry(3, (float) fKbaktif3));
                yVals2.add(new BarEntry(3, (float) fkbpasca3));
                yVals3.add(new BarEntry(3, (float) fkbpus4t3));
                yVals1.add(new BarEntry(4, (float) fKbaktif4));
                yVals2.add(new BarEntry(4, (float) fkbpasca4));
                yVals3.add(new BarEntry(4, (float) fkbpus4t4));
                yVals1.add(new BarEntry(5, (float) fKbaktif5));
                yVals2.add(new BarEntry(5, (float) fkbpasca5));
                yVals3.add(new BarEntry(5, (float) fkbpus4t5));
                yVals1.add(new BarEntry(6, (float) fKbaktif6));
                yVals2.add(new BarEntry(6, (float) fkbpasca6));
                yVals3.add(new BarEntry(6, (float) fkbpus4t6));
                yVals1.add(new BarEntry(7, (float) fKbaktif7));
                yVals2.add(new BarEntry(7, (float) fkbpasca7));
                yVals3.add(new BarEntry(7, (float) fkbpus4t7));

                BarDataSet set1, set2, set3;
                set1 = new BarDataSet(yVals1, "KB Aktif");
                set1.setColor(Color.parseColor("#f35f6c"));
                set2 = new BarDataSet(yVals2, "KB Pasca Persalinan");
                set2.setColor(Color.parseColor("#198c70"));
                set3 = new BarDataSet(yVals3, "PUS 4T BerKB");
                set3.setColor(Color.parseColor("#f6821f"));
                BarData data = new BarData(set1, set2, set3);
                data.setValueFormatter(new LargeValueFormatter());
                chart.setData(data);
                chart.getBarData().setBarWidth(barWidth);
                chart.animateY(3000);
                chart.getXAxis().setAxisMinimum(0);
                chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * jumlah);
                chart.getXAxis().setCenterAxisLabels(true);
                chart.groupBars(0, groupSpace, barSpace);
                chart.getData().setHighlightEnabled(false);
//                Description description = new Description();
//                description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
//                description.setText("Grafik Status");
//                chart.setDescription(description);

                chart.invalidate();

                Legend l = chart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(true);
                l.setYOffset(20f);
                l.setXOffset(0f);
                l.setYEntrySpace(0f);
                l.setTextSize(8f);


                //X-axis
                XAxis xAxis = chart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setCenterAxisLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setAxisMaximum(jumlah);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(responsePos));
                System.out.println("pos \n" + responsePos);
                //Y-axis
                chart.getAxisRight().setEnabled(false);
                YAxis leftAxis = chart.getAxisLeft();
                leftAxis.setValueFormatter(new LargeValueFormatter());
                leftAxis.setDrawGridLines(true);
                leftAxis.setSpaceTop(35f);
                leftAxis.setAxisMinimum(0f);

            }

            @Override
            public void onFailure(Call<ResponseGrafikStatus> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(GrafikActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.status:
                getchart();
                String title = getString(R.string.title_activity_grafik);
                getSupportActionBar().setTitle(title + " Status");

                return true;
            case R.id.metode:
                getChartMetode();
                String title2 = getString(R.string.title_activity_grafik);
                getSupportActionBar().setTitle(title2 + " Metode");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

//    private void getchart(){
//        barWidth = 0.3f;
//        barSpace = 0f;
//        groupSpace = 0.4f;
//
//        int groupCount = 6;
//
//        ArrayList xVals = new ArrayList();
//
//        xVals.add("Jan");
//        xVals.add("Feb");
//        xVals.add("Mar");
//        xVals.add("Apr");
//        xVals.add("May");
//        xVals.add("Jun");
//
//        ArrayList yVals1 = new ArrayList();
//        ArrayList yVals2 = new ArrayList();
//
//        yVals1.add(new BarEntry(1, (float) 1));
//        yVals2.add(new BarEntry(1, (float) 2));
//        yVals1.add(new BarEntry(2, (float) 3));
//        yVals2.add(new BarEntry(2, (float) 4));
//        yVals1.add(new BarEntry(3, (float) 5));
//        yVals2.add(new BarEntry(3, (float) 6));
//        yVals1.add(new BarEntry(4, (float) 7));
//        yVals2.add(new BarEntry(4, (float) 8));
//        yVals1.add(new BarEntry(5, (float) 9));
//        yVals2.add(new BarEntry(5, (float) 10));
//        yVals1.add(new BarEntry(6, (float) 11));
//        yVals2.add(new BarEntry(6, (float) 12));
//
//
//        BarDataSet set1, set2;
//        set1 = new BarDataSet(yVals1, "A");
//        set1.setColor(Color.RED);
//        set2 = new BarDataSet(yVals2, "B");
//        set2.setColor(Color.BLUE);
//        BarData data = new BarData(set1, set2);
//        data.setValueFormatter(new LargeValueFormatter());
//        chart.setData(data);
//        chart.getBarData().setBarWidth(barWidth);
//        chart.animateY(3000);
//        chart.getXAxis().setAxisMinimum(0);
//        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//        chart.groupBars(0, groupSpace, barSpace);
//        chart.getData().setHighlightEnabled(false);
//        chart.invalidate();
//
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);
//
//        //X-axis
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setAxisMaximum(6);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//        //Y-axis
//        chart.getAxisRight().setEnabled(false);
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setSpaceTop(35f);
//        leftAxis.setAxisMinimum(0f);
//
//    }

//    private void getchart(){
//        String id = usersSession.getSpIdUsers();
//        ApiService apiService = InitLibrary.getInstance();
//        Call<ResponsePosyandu> call = apiService.getPosyandu(id);
//        call.enqueue(new Callback<ResponsePosyandu>() {
//            @Override
//            public void onResponse(Call<ResponsePosyandu> call, Response<ResponsePosyandu> response) {
//                String msg = response.body().getMsg();
//                ArrayList<String> pos = new ArrayList<String>();
//                List<Posyandu> posyanduList = response.body().getPosyandu();
//                String namaPos = posyanduList.get(0).getNamaPos();
//                pos.add(namaPos);
//                //===================================
//
//                float groupSpace = 0.08f;
//                float barSpace = 0.03f; // x4 DataSet
//                float barWidth = 0.2f; // x4 DataSet
//                // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
//
//
//                int startYear = 1980;
//                int endYear = 1985;
//
//                chart.animateY(3000);
//
//                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//                ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
//                ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
//
//
//
//                for (int i = startYear; i < endYear; i++) {
//                    yVals1.add(new BarEntry(i, (float) (Math.random())));
//                    yVals2.add(new BarEntry(i, (float) (Math.random())));
//                    yVals3.add(new BarEntry(i, (float) (Math.random())));
//                }
//
//                BarDataSet set1, set2, set3;
//
//                if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
//
//                    set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
//                    set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
//                    set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
//                    set1.setValues(yVals1);
//                    set2.setValues(yVals2);
//                    set3.setValues(yVals3);
//                    chart.getData().notifyDataChanged();
//                    chart.notifyDataSetChanged();
//
//                } else {
//                    // create 4 DataSets
//                    set1 = new BarDataSet(yVals1, "Company A");
//                    set1.setColor(Color.rgb(104, 241, 175));
//                    set2 = new BarDataSet(yVals2, "Company B");
//                    set2.setColor(Color.rgb(164, 228, 251));
//                    set3 = new BarDataSet(yVals3, "Company C");
//                    set3.setColor(Color.rgb(242, 247, 158));
//
//                    BarData data = new BarData(set1, set2, set3);
//                    data.setValueFormatter(new LargeValueFormatter());
//                    chart.setData(data);
//                }
//
//                // specify the width each bar should have
//                chart.getBarData().setBarWidth(barWidth);
//
//                // restrict the x-axis range
//                chart.getXAxis().setAxisMinimum(startYear);
//
//
//                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(pos));
//                chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//
//                chart.getAxisLeft().setStartAtZero(true);
//                // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//                chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * 20);
//                chart.groupBars(startYear, groupSpace, barSpace);
//                chart.invalidate();
//
//                //===================================
////                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePosyandu> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//    }

//    public class LabelFormatter implements IAxisValueFormatter {
//        private final String[] mLabels;
//
//        public LabelFormatter(String[] labels) {
//            mLabels = labels;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return mLabels[(int) value];
//        }
//    }


