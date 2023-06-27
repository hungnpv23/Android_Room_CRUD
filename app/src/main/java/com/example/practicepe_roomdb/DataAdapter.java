package com.example.practicepe_roomdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<dataEntity> mListData;
    private IClickItem  iClickItem;

    public interface IClickItem { //anh xa su kien ng dung nhan nut update vao main acivity
        void updateData(dataEntity data);
        void deleteData(dataEntity data);
    }

    public DataAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void setData(List<dataEntity> mListData) {
        this.mListData = mListData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        final dataEntity data = mListData.get(position);
        if (data == null) {
            return;
        }

        holder.tvID.setText(data.getId());
        holder.tvName.setText(data.getName());
        holder.tvAdsress.setText(data.getAddress());
        holder.tvAvg.setText(data.getAvgScore());

        //bat su kien ng dung click update
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.updateData(data);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.deleteData(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tvID;
        TextView tvName;
        TextView tvAdsress;
        TextView tvAvg;

        Button btnUpdate;
        Button btnDelete;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvName = itemView.findViewById(R.id.tvName);
            tvAdsress = itemView.findViewById(R.id.tvAddress);
            tvAvg = itemView.findViewById(R.id.tvAvgScore);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
