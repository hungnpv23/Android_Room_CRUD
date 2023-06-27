package com.example.practicepe_roomdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicepe_roomdb.database.Database;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private EditText edtID, edtName, edtAddress, edtAvg;
    private Button btnUpdate;
    private dataEntity mDataEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initUI();

        //nhan data tu main activity
        mDataEntity = (dataEntity) getIntent().getExtras().get("objectData");
        //set data vao cac editText
        if (mDataEntity != null) {
            edtID.setText(mDataEntity.getId());
            edtName.setText(mDataEntity.getName());
            edtAddress.setText(mDataEntity.getAddress());
            edtAvg.setText(mDataEntity.getAvgScore());
        }
        //xu li su kien click update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(mDataEntity);
            }
        });
    }

    private void updateData(dataEntity data) {
        //lay data tu form
        String strID = edtID.getText().toString().trim();
        String strName = edtName.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();
        String strAvg = edtAvg.getText().toString().trim();

        //check null
        if (TextUtils.isEmpty(strID) || TextUtils.isEmpty(strName) ||
                TextUtils.isEmpty(strAddress) || TextUtils.isEmpty(strAvg)) {
            return;
        }

        //set du lieu
        mDataEntity.setId(strID);
        mDataEntity.setName(strName);
        mDataEntity.setAddress(strAddress);
        mDataEntity.setAvgScore(strAvg);

        //update data tren db
        Database.getInstance(this).dataDao().updateData(mDataEntity);
        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();

        //chuyen trang ve main activity
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void initUI() { // anh xa UI
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtAvg = findViewById(R.id.edtAvg);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}