package com.developer.kb.kb.Pasien.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kb.kb.Global.UsersSession;
import com.developer.kb.kb.Pasien.DetailPasienActivity;
import com.developer.kb.kb.Pasien.response.Pasien;
import com.developer.kb.kb.R;

import java.util.List;

public class AdapterPasien extends RecyclerView.Adapter<AdapterPasien.ViewPasienHolder> {
    private List<Pasien> pasien;
    private Context context;
    UsersSession usersSession;


    public AdapterPasien(List<Pasien> pasien, Context context) {
        this.pasien = pasien;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewPasienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pasien,parent,false);
        usersSession = new UsersSession(context);
        return new ViewPasienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPasienHolder holder, final int position) {
        holder.tvNamaPasien.setText(pasien.get(position).getNama());
        holder.tvIdPasien.setText(pasien.get(position).getNamaPos());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailPasienActivity.class);
                i.putExtra("id_pasien", pasien.get(position).getIdPasien());
                i.putExtra("id_pos", pasien.get(position).getIdPos());
                i.putExtra("nama_pos", pasien.get(position).getNamaPos());
                i.putExtra("nama", pasien.get(position).getNama());
                i.putExtra("no_telp", pasien.get(position).getNoTelp());
                i.putExtra("tgl_lahir", pasien.get(position).getTglLahir());
                i.putExtra("umur", pasien.get(position).getUmur());
                i.putExtra("alamat", pasien.get(position).getAlamat());
                i.putExtra("pendidikan", pasien.get(position).getPendidikan());
                i.putExtra("pekerjaan", pasien.get(position).getPekerjaan());
                i.putExtra("jumlah_anak", pasien.get(position).getJumlahAnak());
                i.putExtra("hamil", pasien.get(position).getHamil());
                i.putExtra("gakin", pasien.get(position).getGakin());
                i.putExtra("pus4t", pasien.get(position).getPus4t());
                i.putExtra("metode", pasien.get(position).getMetode());
                System.out.println("pos " + pasien.get(position).getNamaPos());
                Toast.makeText(context, pasien.get(position).getNama() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pasien == null){
            return 0;
        } else {
            return pasien.size();
        }

    }

    public static class ViewPasienHolder extends RecyclerView.ViewHolder {

        TextView tvNamaPasien, tvIdPasien;
        CardView cardView;

        public ViewPasienHolder(View v) {
            super(v);
            tvNamaPasien = v.findViewById(R.id.listtvNamaPasien);
            tvIdPasien = v.findViewById(R.id.listtvIdPasien);
            cardView = v.findViewById(R.id.listcardPasien);
        }
    }
}
