package com.example.practicepe_roomdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicepe_roomdb.database.Database;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private EditText edtID, edtName, edtAddress, edtAvg;
    private Button btnAddnew;
    private RecyclerView rcvData;
    private List<dataEntity> mListData;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        dataAdapter = new DataAdapter(new DataAdapter.IClickItem() {
            @Override
            public void updateData(dataEntity data) {
                clickUpdateData(data);
            }

            @Override
            public void deleteData(dataEntity data) {
                clickDeleteData(data);
            }
        });

        mListData = new ArrayList<>();
        //set data cho data adapter
        dataAdapter.setData(mListData);

        //dinh dang layout cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        //dua data tu data adapter vao recycleview
        rcvData.setAdapter(dataAdapter);

        //xu li add new
        btnAddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        //load data table
        loadData();
    }

    private void initUI() { // anh xa UI
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtAvg = findViewById(R.id.edtAvg);
        btnAddnew = findViewById(R.id.btnAddnew);
        rcvData = findViewById(R.id.rcvData);
    }
    private void addData() {//add data function
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

        //tao object data
        dataEntity dataEntity = new dataEntity(strID, strName, strAddress, strAvg);
        //check dublicate data
        if (isDataExist(dataEntity)) {
            hideSoftKeyBoard();
            Toast.makeText(this, "Data already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        //them data vao database
        Database.getInstance(this).dataDao().insertData(dataEntity);

        //hien thong bao, xoa input, an ban phim
        Toast.makeText(this, "Add Successfully", Toast.LENGTH_SHORT).show();
        clearInput();
        hideSoftKeyBoard();

        //load lai data table
        loadData();
    }

    private void clickUpdateData(dataEntity data) { //xu li khi nhan update
        //intent de chuyen trang
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        //bundle de truyen data
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectData", data);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    private void clickDeleteData(dataEntity data) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete data")
                .setMessage("Are you sure to delete " + data.getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database.getInstance(MainActivity.this).dataDao().deleteData(data);
                        Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadData();
        }
    }

    private void loadData() { //cap nhat data len recycleview
        mListData = Database.getInstance(this).dataDao().getDataList();
        dataAdapter.setData(mListData);
    }

    public boolean isDataExist(@NonNull dataEntity dataEntity) { //check dublicate data
        List<dataEntity> list = Database.getInstance(this).dataDao().checkData(dataEntity.getId());
        return list != null && !list.isEmpty();
    }

    public void clearInput() { //xoa input da nhap
        edtID.setText("");
        edtName.setText("");
        edtAddress.setText("");
        edtAvg.setText("");
    }

    public void hideSoftKeyBoard() { //an ban phim
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (NullPointerException ex)  {
            ex.printStackTrace();
        }
    }
}