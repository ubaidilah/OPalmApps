package com.master.opalmapps.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.master.opalmapps.DataActivity;
import com.master.opalmapps.R;
import com.master.opalmapps.model.PengujianModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PengujianAdapter extends RecyclerView.Adapter<PengujianAdapter.PengujianViewHolder> {

    private final Context context;
    private final List<PengujianModel> pengujianModelList;

    public PengujianAdapter(Context context, List<PengujianModel> pengujianModelList) {

        this.context = context;
        this.pengujianModelList = pengujianModelList;
    }

    @NonNull
    @Override
    public PengujianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_pengujian, parent, false);

        return new PengujianViewHolder(view);
    }


    @SuppressLint({"SetTextI18n", "Recycle"})
    @Override
    public void onBindViewHolder(@NonNull PengujianViewHolder holder, int position  ) {
        holder.noTextView.setText("No : " + (position + 1));
        holder.tglTextView.setText("Tanggal : " + pengujianModelList.get(position).getTanggalUji());
        holder.wktTextView.setText("Waktu : " + pengujianModelList.get(position).getWaktuUji());
        holder.kadarTextView.setText("Kadar Minyak : " + pengujianModelList.get(position).getKadarM());
        holder.kematanganTextView.setText("Kematangan : " + pengujianModelList.get(position).getKlasifikasi());

        holder.itemView.setOnClickListener(v -> {

            new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Yakin Ingin menghapus Data yang dipilih?")
                    .setConfirmText("HAPUS!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

//                            holder.rowLinearLayout.setBackgroundColor(Color.parseColor("#bcbcbc"));
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pengujian");
                            databaseReference.child(pengujianModelList.get(position).getPengujianId()).removeValue();

                            sDialog
                                    .setTitleText("Hapus!")
                                    .setContentText("Data yang dipilih sudah dihapus !")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        }
                    })
                    .show();

//            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//            builder1.setMessage("Do you want to remove ?");
//            builder1.setCancelable(true);
//
//            builder1.setPositiveButton(
//                    "Yes",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id)
//                        {
//                            Log.d("AleartDialog", "Berhasil");
//                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pengujian");
//                            databaseReference.child(pengujianModelList.get(position).getPengujianId()).removeValue();
//                        }
//                    });
//
//            builder1.setNegativeButton(
//                    "No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog alert11 = builder1.create();
//            alert11.show();
//

//            dialog();

        });
    }

    @Override
    public int getItemCount() {
        return pengujianModelList.size();
    }

    private void dialog() {
//            new  SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText("Are you sure?")
//                    .setContentText("Won't be able to recover this file!")
//                    .setConfirmText("Yes,delete it!")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//
//                            sDialog
//                                    .setTitleText("Deleted!")
//                                    .setContentText("Your imaginary file has been deleted!")
//                                    .setConfirmText("OK")
//                                    .setConfirmClickListener(null)
//                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//                        }
//                    })
//                    .show();
    }

    public static class PengujianViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rowLinearLayout;
        TextView noTextView, tglTextView, wktTextView, kematanganTextView,kadarTextView ;
        Button button;

        public PengujianViewHolder(@NonNull View itemView) {
            super(itemView);

            rowLinearLayout = itemView.findViewById(R.id.row_linear_layout);
            noTextView = itemView.findViewById(R.id.no);
            tglTextView = itemView.findViewById(R.id.tgl);
            wktTextView = itemView.findViewById(R.id.wkt);
            kadarTextView = itemView.findViewById(R.id.kadar);
            kematanganTextView = itemView.findViewById(R.id.kematangan);
//            (con).findViewById(R.id.yourButtoId)
//            button = ((Activity)itemView.getContext()).findViewById(R.id.hapusdata);
        }
    }
}
