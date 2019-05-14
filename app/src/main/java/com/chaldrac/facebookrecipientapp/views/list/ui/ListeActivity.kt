package com.chaldrac.facebookrecipientapp.views.list.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.chaldrac.facebookrecipientapp.FacebookRecipientApplication
import com.chaldrac.facebookrecipientapp.R
import com.chaldrac.facebookrecipientapp.entities.Recipe
import com.chaldrac.facebookrecipientapp.views.list.ListePresenter
import com.chaldrac.facebookrecipientapp.views.list.adapter.ListAdapter
import com.chaldrac.facebookrecipientapp.views.list.adapter.OnItemClickListener
import com.chaldrac.facebookrecipientapp.views.list.di.ListeComponent
import com.chaldrac.facebookrecipientapp.views.main.ui.MainActivity

class ListeActivity : AppCompatActivity(), ListeView, OnItemClickListener {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

    lateinit var adapter : ListAdapter
    lateinit var presenter : ListePresenter
    lateinit var component : ListeComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste)
        ButterKnife.bind(this)
        setupToolbar()
        setupInjection()
        setupRecyclerView()

        presenter.onCreate()
        presenter.getRecipes()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_list) {
            navigateToMainScreen()
        } else if (id == R.id.action_logout) {
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {

    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setupInjection() {
        val app = application as FacebookRecipientApplication
        component = app.getListeComponent(this, this, this)
        presenter = getPresenterC()
        adapter = getAdapterC()
    }

    private fun getAdapterC(): ListAdapter {
        return component.adapter

    }

    fun getPresenterC(): ListePresenter {
        return component.presenter

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    @OnClick(R.id.toolbar)
    fun onToolbarClick(){
        recyclerView.smoothScrollToPosition(0)
    }

    override fun setRecipes(data: List<Recipe>) {
        adapter.setDataSet(data)
    }

    override fun recipeUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun recipeDeleted(recipe: Recipe) {
        adapter.removeRecipe(recipe)
    }

    override fun onFavClick(recipe: Recipe?) {
        presenter.toggleFavorie(recipe!!)
    }

    override fun onItemClick(recipe: Recipe?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe?.sourceUrl))
        startActivity(intent)
    }

    override fun onDeleteClick(recipe: Recipe?) {
        presenter.removeRecipe(recipe!!)
    }
}
