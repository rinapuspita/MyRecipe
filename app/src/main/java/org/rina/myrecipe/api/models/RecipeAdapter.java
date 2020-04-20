package org.rina.myrecipe.api.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import org.rina.myrecipe.R;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ListViewHolder> {
    private Context context;
    private Envelope<List<RecipeResponse>> items;

    public RecipeAdapter(Context context, Envelope<List<RecipeResponse>> items) {
        this.context = context;
        this.items = items;
    }


    public Envelope<List<RecipeResponse>> getItems() {
        return items;
    }

    public static void setItems(Envelope<List<RecipeResponse>> items) {
        Envelope<List<RecipeResponse>> Items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row_food, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        RecipeResponse item = items.getData().get(position);
        String url = "https://mobile.putraprima.id/uploads/";
        // Atur image di baris ini, dapat menggunakan Picasso atau Glide
        holder.tvName.setText(item.getNama_resep());
        holder.tvDetail.setText(item.getDeskripsi());
        holder.tvBahan.setText(item.getBahan());
        holder.tvLangkah.setText(item.getLangkah_pembuatan());
//        Glide.with(holder.itemView.getContext())
//                .load(item.getFoto())
//                .apply(new RequestOptions().override(55, 55))
//                .into(holder.imgPhoto);
        Picasso.get().load("https://mobile.putraprima.id/uploads/"+item.getFoto()).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.getData().size() : 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvDetail, tvBahan, tvLangkah;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDetail = itemView.findViewById(R.id.tv_item_detail);
            tvBahan = itemView.findViewById(R.id.tv_item_bahan);
            tvLangkah = itemView.findViewById(R.id.tv_item_langkah);
        }
    }
}
