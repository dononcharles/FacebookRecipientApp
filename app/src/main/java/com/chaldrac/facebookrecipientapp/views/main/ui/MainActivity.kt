package com.chaldrac.facebookrecipientapp.views.main.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaldrac.facebookrecipientapp.R
import com.chaldrac.facebookrecipientapp.lib.base.ImageLoader
import com.chaldrac.facebookrecipientapp.entities.Recipe
import butterknife.BindView
import android.widget.ProgressBar
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.ButterKnife
import com.chaldrac.facebookrecipientapp.views.main.MainPresenter
import com.chaldrac.facebookrecipientapp.views.main.di.MainComponent
import com.google.android.material.snackbar.Snackbar
import butterknife.OnClick
import android.view.animation.Animation
import com.bumptech.glide.request.RequestListener
import android.view.*
import android.view.animation.AnimationUtils
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.chaldrac.facebookrecipientapp.FacebookRecipientApplication


open class MainActivity : AppCompatActivity(), MainView, SwipeGestureListener {

    @BindView(R.id.imgRecipe)
    lateinit var imgRecipe: ImageView
    @BindView(R.id.imgDismiss)
    lateinit var imgDismiss: ImageButton
    @BindView(R.id.imgKeep)
    lateinit var imgKeep: ImageButton
    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.layoutContainer)
    lateinit var layoutContainer: ConstraintLayout

    private lateinit var currentRecipe: Recipe
    private lateinit var imageLoader: ImageLoader
    private lateinit var presenter: MainPresenter
    private lateinit var component: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setupInjection()
        setupImageLoader()
        setupGestureDetection()
        setupPresenter()
    }

    open fun setupPresenter() {
        presenter.onCreate()
        presenter.getNextRecipe()
    }

    @SuppressLint("ClickableViewAccessibility")
    open fun setupGestureDetection() {
        val gestureDetector = GestureDetector(this, SwipeGestureDetector(this))
        val gestureOnTouchListener = object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(motionEvent)
            }
        }

        imgRecipe.setOnTouchListener(gestureOnTouchListener)
    }

    open fun setupImageLoader() {
        val glideRequestListener = object : RequestListener<Any> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Any>?,
                isFirstResource: Boolean
            ): Boolean {
                presenter.imageError(e?.localizedMessage!!)
                return false
            }

            override fun onResourceReady(
                resource: Any?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Any>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                presenter.imageReady()
                return false
            }

        }

        imageLoader.setOnFinishedImageLoadinListener(glideRequestListener)
    }

    protected override fun onDestroy() {
        destroyPresenter()
        super.onDestroy()
    }

    open fun destroyPresenter() {
        presenter.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_list) {
            navigateToListScreen()
        } else if (id == R.id.action_logout) {
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val app = application as FacebookRecipientApplication
        // app.logout()
    }

    private fun navigateToListScreen() {
        startActivity(Intent(this, ListActivity::class.java))
    }

    private fun setupInjection() {
        val app = application as FacebookRecipientApplication
        component = app.getRecipeMainComponent(this, this)
        imageLoader = getImageLoader()
        presenter = getPresenter()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showUIElements() {
        imgKeep.visibility = View.VISIBLE
        imgDismiss.visibility = View.VISIBLE
    }

    override fun hideUIElements() {
        imgKeep.visibility = View.GONE
        imgDismiss.visibility = View.GONE
    }

    open fun clearImage() {
        imgRecipe.setImageResource(0)
    }

    override fun saveAnimation() {
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.save_animation)
        anim.setAnimationListener(getAnimationListener())
        imgRecipe.startAnimation(anim)
    }

    override fun dismissAnimation() {
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.dismiss_animation)
        anim.setAnimationListener(getAnimationListener())
        imgRecipe.startAnimation(anim)
    }


    open fun getAnimationListener(): Animation.AnimationListener {
        return object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                clearImage()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        }
    }

    @OnClick(R.id.imgKeep)
    override fun onKeep() {
        presenter.saveRecipe(currentRecipe)
    }

    @OnClick(R.id.imgDismiss)
    override fun onDismiss() {
        presenter.dismissRecipe()
    }

    override fun onRecipeSaved() {
        Snackbar.make(layoutContainer, R.string.recipemain_notice_saved, Snackbar.LENGTH_SHORT).show()
    }

    override fun setRecipe(recipe: Recipe) {
        this.currentRecipe = recipe
        imageLoader.load(imgRecipe, recipe.imageUrl!!)
    }

    override fun onGetRecipeError(error: String) {
        val msgError = String.format(getString(R.string.recipemain_error), error)
        Snackbar.make(layoutContainer, msgError, Snackbar.LENGTH_SHORT).show()
    }

    open fun getImageLoader(): ImageLoader {
        return component.geImageLoader()
    }

    open fun getPresenter(): MainPresenter {
        return component.gePresenter()
    }

}
