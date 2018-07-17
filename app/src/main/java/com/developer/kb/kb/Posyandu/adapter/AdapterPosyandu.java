package com.developer.kb.kb.Posyandu.adapter;

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
import com.developer.kb.kb.Posyandu.DetailPosyanduActivity;
import com.developer.kb.kb.Posyandu.response.Posyandu;
import com.developer.kb.kb.R;

import java.util.List;

public class AdapterPosyandu extends RecyclerView.Adapter<AdapterPosyandu.ViewPosyanduHolder> {

    private List<Posyandu> posyandu;
    private int rowLayout;
    private Context context;
    UsersSession usersSession;

    public AdapterPosyandu(List<Posyandu> posyandu, int rowLayout, Context context) {
        this.posyandu = posyandu;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPosyanduHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        usersSession = new UsersSession(context);
        return new ViewPosyanduHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPosyanduHolder holder, final int position) {
        holder.tvNamaPasien.setText(posyandu.get(position).getNamaPos());
        holder.tvIdPasien.setText(posyandu.get(position).getAlamatPos());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailPosyanduActivity.class);
                i.putExtra("id_pos", posyandu.get(position).getIdPos());
                i.putExtra("id_petugas", posyandu.get(position).getIdPetugas());
                i.putExtra("nama", posyandu.get(position).getNama());
                i.putExtra("nama_pos", posyandu.get(position).getNamaPos());
                i.putExtra("alamat_pos", posyandu.get(position).getAlamatPos());
                i.putExtra("no_telp_pos", posyandu.get(position).getNoTelpPos());
                Toast.makeText(context, posyandu.get(position).getNamaPos() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (posyandu == null){
            return 0;
        } else {
            return posyandu.size();
        }
    }

    public static class ViewPosyanduHolder extends RecyclerView.ViewHolder {

        TextView tvNamaPasien, tvIdPasien;
        CardView cardView;

        public ViewPosyanduHolder(View v) {
            super(v);
            tvNamaPasien = v.findViewById(R.id.listtvNamaPasien);
            tvIdPasien = v.findViewById(R.id.listtvIdPasien);
            cardView = v.findViewById(R.id.listcardPasien);
        }
    }
}
