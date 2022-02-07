package com.example.jurnalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterJurnalRecyclerView extends RecyclerView.Adapter<AdapterJurnalRecyclerView.ViewHolder> {

    private ArrayList<Jurnal> daftarJurnal;
    private Context context;
    FirebaseDataListener listener;

    public AdapterJurnalRecyclerView(ArrayList<Jurnal> jurnals, Context ctx) {
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarJurnal = jurnals;
        context = ctx;
        listener = (FirebaseDBReadActivity) ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         
         */
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namajurnal);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jurnal, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = daftarJurnal.get(position).getJudul();

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(FirebaseDBCreateActivity.getActIntent((Activity) context).putExtra("data", daftarJurnal.get(position)));
                            }
                        }
                );

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                listener.onDeleteData(daftarJurnal.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada Jurnal
         */
        return daftarJurnal.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Jurnal jurnal, int position);
    }
}
