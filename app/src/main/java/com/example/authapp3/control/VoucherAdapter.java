package com.example.authapp3.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp3.R;
import com.example.authapp3.entity.Voucher;

import java.util.ArrayList;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.MyViewHolder> {
    private ArrayList<Voucher> vouchersList;

    public VoucherAdapter(ArrayList<Voucher> VouchersList){
        this.vouchersList= vouchersList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView voucher_name,cost;
        private Button claim;
        public MyViewHolder (final View view){
            super(view);
            voucher_name= view.findViewById(R.id.voucher);
            cost = view.findViewById(R.id.points_cost);
            claim= view.findViewById(R.id.claim_voucher);
        }
    }
    @NonNull
    @Override
    public VoucherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View voucherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vouchers,parent, false);
        return new MyViewHolder(voucherView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.MyViewHolder holder, int position) {
        String voucher = vouchersList.get(position).getDetails();
        Integer cost = vouchersList.get(position).getCost();
        holder.voucher_name.setText(voucher);
        holder.cost.setText(cost);
    }

    @Override
    public int getItemCount() {
        return vouchersList.size();
    }
}
