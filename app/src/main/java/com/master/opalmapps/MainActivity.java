package com.master.opalmapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView action_dataTextView, action_readTextView, ipAddressTextView, ssidTextView, statusMqttTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Koneksi");

//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        action_dataTextView = findViewById(R.id.action_data);
        action_readTextView = findViewById(R.id.action_read);
        ssidTextView = findViewById(R.id.ssid_text_view);
        ipAddressTextView = findViewById(R.id.ip_address_text_view);
        statusMqttTextView = findViewById(R.id.status_mqtt_text_view);

        action_dataTextView.setOnClickListener(this);
        action_readTextView.setOnClickListener(this);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

//                List<PengujianModel> pengujianModelList = new ArrayList<>();

                String TAG = "DataChange";
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                    pengujianModelList.add(ds.getValue(PengujianModel.class));
//                    Log.d(TAG, "onDataChange:DataChange = " + ds.getValue());
//                }


                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot);
                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot.child("Konek"));
                Log.d(TAG, "onDataChange:DataChange = " + dataSnapshot.child("Konek").getValue().toString());
                Map map = (Map) dataSnapshot.child("Konek").getValue();
                Log.d(TAG, "onDataChange:DataChange = " + map.get("ip_address"));


                ssidTextView.setText("Terkoneksi Ke : " + map.get("nama_wifi").toString());
                ipAddressTextView.setText("IP Address : " + map.get("ip_address").toString());
                statusMqttTextView.setText("Status MQTT : Online");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                String TAG = "Cancelled";
                ssidTextView.setText("Terkoneksi Ke : -");
                ipAddressTextView.setText("IP Address : -");
                statusMqttTextView.setText("Status MQTT : Offline");
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

//        db.collection("koneksi")
//                .get()
//                .addOnCompleteListener(task -> {
//                    String TAG = "Read";
//                    if (task.isSuccessful()) {
//                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Berhasil terkoneksi")
//                                .show();
//                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                            ssidTextView.setText("Terkoneksi Ke : " + document.getData().get("nama_wifi").toString());
//                            ipAddressTextView.setText("IP Address : " + document.getData().get("ip_address").toString());
//                            statusMqttTextView.setText("Status MQTT : Online");
//                            Log.d(TAG, document.getId() + " => " + document.getData().get("nama_wifi").toString());
//                        }
//                    } else {
//                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText("Gagal terkoneksi")
//                                .show();
//                        ssidTextView.setText("Terkoneksi Ke : -");
//                        ipAddressTextView.setText("IP Address : -");
//                        statusMqttTextView.setText("Status MQTT : Offline");
//                        Log.w(TAG, "Error getting documents.", task.getException());
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_read) {
            startActivity(new Intent(getApplicationContext(), ReadActivity.class));
            finish();
        } else if (v.getId() == R.id.action_data) {
            startActivity(new Intent(getApplicationContext(), DataActivity.class));
            finish();
        }
    }
}