package com.developer.kb.kb.Petugas.adapter;

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
import com.developer.kb.kb.Petugas.DetailKaderActivity;
import com.developer.kb.kb.Login.users.User;
import com.developer.kb.kb.R;

import java.util.List;

public class AdapterKader extends RecyclerView.Adapter<AdapterKader.ViewKaderHolder> {

    private List<User> users;
    private int rowLayout;
    private Context context;
    UsersSession usersSession;

    public AdapterKader(List<User> users, int rowLayout, Context context) {
        this.users = users;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewKaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        usersSession = new UsersSession(context);
        return new ViewKaderHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewKaderHolder holder, final int position) {
        holder.tvNamaPasien.setText(users.get(position).getNama());
        holder.tvIdPasien.setText(users.get(position).getNip());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailKaderActivity.class);
                i.putExtra("id_users", users.get(position).getIdUsers());
                i.putExtra("id_posyandu", users.get(position).getIdPosyandu());
                i.putExtra("nama", users.get(position).getNama());
                i.putExtra("nama_pos", users.get(position).getNamaPos());
                i.putExtra("alamat", users.get(position).getAlamat());
                i.putExtra("no_telp", users.get(position).getNomorTelpon());
                i.putExtra("username", users.get(position).getUsername());
                i.putExtra("password", users.get(position).getPassword());
                i.putExtra("nip", users.get(position).getNip());
                i.putExtra("role", users.get(position).getIdRole());
                Toast.makeText(context, users.get(position).getNama() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users == null){
            return 0;
        } else {
            return users.size();
        }

    }

    public static class ViewKaderHolder extends RecyclerView.ViewHolder {

        TextView tvNamaPasien, tvIdPasien;
        CardView cardView;

        public ViewKaderHolder(View v) {
            super(v);
            tvNamaPasien = v.findViewById(R.id.listtvNamaPasien);
            tvIdPasien = v.findViewById(R.id.listtvIdPasien);
            cardView = v.findViewById(R.id.listcardPasien);
        }
    }

}
