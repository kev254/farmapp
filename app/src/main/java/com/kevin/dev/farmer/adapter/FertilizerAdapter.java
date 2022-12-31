package com.kevin.dev.farmer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.dev.farmer.R;
import com.kevin.dev.farmer.Resources.ClickListener;
import com.kevin.dev.farmer.authentication.SharedPrefManager;
import com.kevin.dev.farmer.model.Farmer;
import com.kevin.dev.farmer.model.Fertilizer;

import java.util.List;

public class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.SubjectViewHolder> {
    private Context mCtx;
    private List<Fertilizer> SubjectsList;
    private ClickListener listener;
    private Farmer farmer;

    public FertilizerAdapter(Context mCtx, List<Fertilizer> fertilizerList, ClickListener listener) {
        this.mCtx = mCtx;
        this.SubjectsList = fertilizerList;
        this.listener = listener;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.usage_item, null);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder,  int position) {
        Fertilizer subjects = SubjectsList.get(position);
        farmer = SharedPrefManager.getInstance(mCtx).getUser();
        //loading the image
        holder.name.setText(subjects.getName());
        holder.used.setText(subjects.getQuantity_used());
        holder.rem.setText(subjects.getQuantity_rem());




    }

    @Override
    public int getItemCount() {
        return SubjectsList.size();
    }



    class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,used,rem;



        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            used = itemView.findViewById(R.id.used);
            rem=itemView.findViewById(R.id.rem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


        }
    }


}
