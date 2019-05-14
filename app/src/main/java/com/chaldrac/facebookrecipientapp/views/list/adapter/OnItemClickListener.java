package com.chaldrac.facebookrecipientapp.views.list.adapter;

import com.chaldrac.facebookrecipientapp.entities.Recipe;

public interface OnItemClickListener {
    void onFavClick(Recipe recipe);
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
