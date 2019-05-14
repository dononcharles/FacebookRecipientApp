package com.chaldrac.facebookrecipientapp.views.list.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chaldrac.facebookrecipientapp.R;
import com.chaldrac.facebookrecipientapp.entities.Recipe;
import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    List<Recipe> dataSet;
    ImageLoader imageLoader;
    OnItemClickListener onItemClickListener;

    public ListAdapter(List<Recipe> dataSet, ImageLoader imageLoader, OnItemClickListener listener) {
        this.dataSet = dataSet;
        this.imageLoader = imageLoader;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_liste, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Recipe currentRecipe = dataSet.get(position);

        imageLoader.load(holder.imgRecipe, currentRecipe.getImageUrl());
        holder.txtRecipeName.setText(currentRecipe.getTitle());
        holder.imgFav.setTag(currentRecipe.isFavorie());
        if (currentRecipe.isFavorie()){
            holder.imgFav.setImageResource(android.R.drawable.btn_star_big_on);
        }else{
            holder.imgFav.setImageResource(android.R.drawable.btn_star_big_off);
        }
        holder.setOnItemClickListener(currentRecipe, onItemClickListener);
    }

    public void setDataSet(List<Recipe> dataSetNew) {
        this.dataSet = dataSetNew;
        notifyDataSetChanged();
    }

    public void removeRecipe(Recipe recipe){
        dataSet.remove(recipe);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgRecipe)
        ImageView imgRecipe;

        @BindView(R.id.imgFav)
        ImageView imgFav;

        @BindView(R.id.imgDelete)
        ImageView imgDelete;

        @BindView(R.id.txtRecipeName)
        TextView txtRecipeName;

        @BindView(R.id.fbShare)
        ShareButton fbShare;

        @BindView(R.id.fbSend)
        SendButton fbSend;

        private View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void setOnItemClickListener(Recipe current, OnItemClickListener listener) {

            view.setOnClickListener(v -> listener.onItemClick(current));

            imgFav.setOnClickListener(v -> listener.onFavClick(current));

            imgDelete.setOnClickListener(v -> listener.onDeleteClick(current));

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(current.getSourceUrl()))
                    .build();
            fbShare.setShareContent(content);
            fbSend.setShareContent(content);
        }


    }
}
