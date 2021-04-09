package com.master.opalmapps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.master.opalmapps.adapter.PengujianAdapter;
import com.master.opalmapps.model.PengujianModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference, databaseReference1;

    Button simpandataButton;
    EditText satuanTextView;
    TextView kadarTextView, klasifikasiTextView, waktuTextView, tanggalTextView;
    TextView action_infoTextView, action_dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Pengujian");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("ReadPengujian");

        action_infoTextView = findViewById(R.id.action_info);
        action_dataTextView = findViewById(R.id.action_data);
        satuanTextView = findViewById(R.id.satuan);
        kadarTextView = findViewById(R.id.kadar);
        klasifikasiTextView = findViewById(R.id.klasifikasi);
        waktuTextView = findViewById(R.id.waktu);
        tanggalTextView = findViewById(R.id.tanggal);
        simpandataButton = findViewById(R.id.simpandata);

        action_infoTextView.setOnClickListener(this);
        action_dataTextView.setOnClickListener(this);
        simpandataButton.setOnClickListener(this);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

//                List<PengujianModel> pengujianModelList = new ArrayList<>();

//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                    pengujianModelList.add(ds.getValue(PengujianModel.class));
//                    Log.d(TAG, "onDataChange:DataChange = " + ds.getValue());
//                }

                String TAG = "DataChange";

                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot);
                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot.child("data_uji"));
                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot.child("data_uji").getValue().toString());
                Map map = (Map) dataSnapshot.child("data_uji").getValue();
                Log.d(TAG, "onDataChange:DataChange = " + map.get("tanggal"));
                satuanTextView.setText("1");
                kadarTextView.setText(map.get("kadar_minyak").toString());
                klasifikasiTextView.setText(map.get("klasifikasi").toString());
                waktuTextView.setText(map.get("waktu").toString());
                tanggalTextView.setText(map.get("tanggal").toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                String TAG = "Cancelled";
                satuanTextView.setText(" -");
                kadarTextView.setText(" -");
                klasifikasiTextView.setText(" -");
                waktuTextView.setText(" -");
                tanggalTextView.setText(" -");
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

//        db.collection("Pengujian")
//                .get()
//                .addOnCompleteListener(task -> {
//                    String TAG = "Read";
//                    if (task.isSuccessful()) {
//                        new SweetAlertDialog(ReadActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Data berhasil terbaca")
//                                .show();
//                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                            satuanTextView.setText(document.getData().get("tbs").toString());
//                            kadarTextView.setText(document.getData().get("kadar_minyak").toString());
//                            klasifikasiTextView.setText(document.getData().get("klasifikasi").toString());
//                            waktuTextView.setText(document.getData().get("tanggal").toString());
//                            tanggalTextView.setText(document.getData().get("waktu").toString());
//                        }
//                    } else {
//                        new SweetAlertDialog(ReadActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText("Data Gagal terbaca")
//                                .show();
//                        satuanTextView.setText(" -");
//                        kadarTextView.setText(" -");
//                        klasifikasiTextView.setText(" -");
//                        waktuTextView.setText(" -");
//                        tanggalTextView.setText(" -");
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_info) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (v.getId() == R.id.action_data) {
            startActivity(new Intent(getApplicationContext(), DataActivity.class));
            finish();
        } else if (v.getId() == R.id.simpandata) {
//            Toast.makeText(getApplicationContext(), "simpandata clicked.", Toast.LENGTH_SHORT).show();
            addData();
        }
    }

    private void addData() {

        String id = databaseReference.push().getKey();
        Map<String, Object> pengujian = new HashMap<>();
        pengujian.put("pengujianId", id);
        pengujian.put("beratTbs", satuanTextView.getText().toString().trim());
        pengujian.put("kadarM", kadarTextView.getText().toString().trim());
        pengujian.put("klasifikasi", klasifikasiTextView.getText().toString().trim());
        pengujian.put("waktuUji", waktuTextView.getText().toString().trim());
        pengujian.put("tanggalUji", tanggalTextView.getText().toString().trim());

        assert id != null;
        databaseReference.child(id).setValue(pengujian);
        new SweetAlertDialog(ReadActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Data berhasil tersimpan")
                .show();

//        Toast.makeText(getApplicationContext(), "Pengujian Telah disimpan", Toast.LENGTH_LONG).show();
    }
}