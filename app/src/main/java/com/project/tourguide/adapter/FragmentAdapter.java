package com.project.tourguide.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.project.tourguide.R;
import com.project.tourguide.activity.DetailActivity;
import com.project.tourguide.model.MainModel;
import java.util.ArrayList;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {

    private final ArrayList<MainModel> listKonten = new ArrayList<>();
    public void setData(ArrayList<MainModel> items) {
        listKonten.clear();
        listKonten.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_konten, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter.ViewHolder holder, int position) {
        holder.bind(listKonten.get(position));
    }

    @Override
    public int getItemCount() {
        return listKonten.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private ImageView dp;
        private TextView name, deskripsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            dp = itemView.findViewById(R.id.dp);
            name = itemView.findViewById(R.id.name);
            deskripsi = itemView.findViewById(R.id.desk);
        }

        public void bind(MainModel mainModel) {
            Glide.with(itemView.getContext())
                    .load(mainModel.getImage())
                    .into(dp);

            name.setText(mainModel.getJudul());
            deskripsi.setText(mainModel.getDeskripsi());

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_KONTEN, mainModel);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
