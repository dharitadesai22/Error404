package com.example.inventory.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.AllProducts;
import com.example.inventory.R;

import java.util.ArrayList;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.viewHolder> {

    ArrayList<String> arrayList;
    Context ctx;

    public homeAdapter(ArrayList<String> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_view, parent, false);
        return new homeAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String brand = arrayList.get(position);
        holder.tvBrandName.setText(brand);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, brand, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ctx, AllProducts.class);
                i.putExtra("brand", brand);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvBrandName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
        }
    }
}
