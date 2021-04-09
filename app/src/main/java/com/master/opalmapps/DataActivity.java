package com.master.opalmapps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.master.opalmapps.adapter.PengujianAdapter;
import com.master.opalmapps.model.PengujianModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    TextView action_infoTextView, action_readTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        databaseReference = FirebaseDatabase.getInstance().getReference("Pengujian");

        recyclerView = findViewById(R.id.recyler_view);
        action_infoTextView = findViewById(R.id.action_info);
        action_readTextView = findViewById(R.id.action_read);

        action_infoTextView.setOnClickListener(this);
        action_readTextView.setOnClickListener(this);

        getData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_info) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (v.getId() == R.id.action_read) {
            startActivity(new Intent(getApplicationContext(), ReadActivity.class));
            finish();
        }
    }

    private void getData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                List<PengujianModel> pengujianModelList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    pengujianModelList.add(ds.getValue(PengujianModel.class));
                }
//                PengujianModel pengujianModel = dataSnapshot.getValue(PengujianModel.class);
//                // ...
//                GenericTypeIndicator<ArrayList<PengujianModel>> t = new GenericTypeIndicator<ArrayList<PengujianModel>>() {};
//                ArrayList<PengujianModel> pengujianModelList = dataSnapshot.getValue(t);
//                String TAG = "DataChange";
////                assert pengujianModel != null;
//                Log.d(TAG, "onDataChange:DataChange" + pengujianModelList.get(1));


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new PengujianAdapter(DataActivity.this, pengujianModelList));

//                new SweetAlertDialog(DataActivity.this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Yahkin Ingin menghapus Data yang dipilih?")
//                        .setConfirmText("HAPUS!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//
//                                sDialog
//                                        .setTitleText("Hapus!")
//                                        .setContentText("Data yang dipilih sudah dihapus !")
//                                        .setConfirmText("OK")
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//                            }
//                        })
//                        .show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                String TAG = "Cancelled";
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }



    public void setData(int position){
        String TAG = "position";
        Log.d(TAG, "Position : " + position);
        Toast.makeText(getApplicationContext(), "Position : " + position, Toast.LENGTH_SHORT).show();
    }
}