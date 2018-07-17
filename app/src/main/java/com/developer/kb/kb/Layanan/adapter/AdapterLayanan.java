package com.developer.kb.kb.Layanan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Layanan.DetailLayananActivity;
import com.developer.kb.kb.Layanan.response.Layanan;
import com.developer.kb.kb.R;

import java.util.List;

public class AdapterLayanan extends RecyclerView.Adapter<AdapterLayanan.ViewLayananHolder> {

    private List<Layanan> layanan;
    private int rowLayout;
    private Context context;
    UsersSession usersSession;

    public AdapterLayanan(List<Layanan> layanan, int rowLayout, Context context) {
        this.layanan = layanan;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewLayananHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        usersSession = new UsersSession(context);
        return new ViewLayananHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLayananHolder holder, final int position) {
        holder.tvNamaPasien.setText(layanan.get(position).getNama());
        holder.tvIdPasien.setText(layanan.get(position).getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailLayananActivity.class);
                i.putExtra("id_layanan", layanan.get(position).getIdLayanan());
                i.putExtra("id_pas", layanan.get(position).getIdPas());
                i.putExtra("id_pos", layanan.get(position).getIdPos());
                i.putExtra("nama", layanan.get(position).getNama());
                i.putExtra("nama_pet", layanan.get(position).getNamaPet());
                i.putExtra("nama_pos", layanan.get(position).getNamaPos());
                i.putExtra("tgl_kunjungan", layanan.get(position).getTglKunjungan());
                i.putExtra("tgl_kembali", layanan.get(position).getTglKembali());
                i.putExtra("haid_terakhir", layanan.get(position).getHaidTerakhir());
                i.putExtra("berat_badan", layanan.get(position).getBeratBadan());
                i.putExtra("tekanan_darah", layanan.get(position).getTekananDarah());
                i.putExtra("sakit_kuning", layanan.get(position).getSakitKuning());
                i.putExtra("pendarahan", layanan.get(position).getPendarahan());
                i.putExtra("tumor", layanan.get(position).getTumor());
                i.putExtra("hiv_aids", layanan.get(position).getHivAids());
                i.putExtra("posisi_rahim", layanan.get(position).getPosisiRahim());
                i.putExtra("diabetes", layanan.get(position).getDiabetes());
                i.putExtra("pembekuan_darah", layanan.get(position).getPembekuanDarah());
                i.putExtra("komplikasi", layanan.get(position).getKomplikasi());
                i.putExtra("metode", layanan.get(position).getMetode());
                i.putExtra("ES", layanan.get(position).getEs());
                i.putExtra("status", layanan.get(position).getStatus());
//                Toast.makeText(context, layanan.get(position).getNama() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (layanan == null) {
            return 0;
        } else {
            return layanan.size();
        }
    }

    public static class ViewLayananHolder extends RecyclerView.ViewHolder {

        TextView tvNamaPasien, tvIdPasien;
        CardView cardView;

        public ViewLayananHolder(View v) {
            super(v);
            tvNamaPasien = v.findViewById(R.id.listtvNamaPasien);
            tvIdPasien = v.findViewById(R.id.listtvIdPasien);
            cardView = v.findViewById(R.id.listcardPasien);
        }
    }
}
