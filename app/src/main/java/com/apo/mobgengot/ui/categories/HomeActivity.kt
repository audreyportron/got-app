package com.apo.mobgengot.ui.categories

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.apo.mobgengot.R
import com.apo.mobgengot.databinding.HomeActivityBinding
import com.apo.mobgengot.domain.categories.Category
import com.apo.mobgengot.domain.categories.CategoryType
import com.apo.mobgengot.ui.book.BooksActivity
import com.apo.mobgengot.ui.houses.HousesActivity
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity(), CategoriesViewModel.Listener {


    private val catViewModel: CategoriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val bindingActivity: HomeActivityBinding = DataBindingUtil.setContentView(
            this,
            R.layout.home_activity
        )

        setProperty(CategoriesViewModel.LISTENER_ID, this)
        this.lifecycle.addObserver(catViewModel)
        bindingActivity.model = catViewModel
    }

    /** **********************************
     *          Categories Listener
     *********************************** **/
    override fun onItemClick(category: Category, sharedView: TextView) {
        when (category.type) {
            CategoryType.BOOKS -> startActivity(BooksActivity.getIntent(this, category.apiLink, category.title))
            CategoryType.HOUSES -> {

               val options:ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                   this,
                   sharedView,
                   ViewCompat.getTransitionName(sharedView)!! // "!!" added because minSDK is superieur at 21
               )
                startActivity(HousesActivity.getIntent(this, category.apiLink, category.title, sharedView ), options.toBundle())
            }
            else -> {
                AlertDialog.Builder(this)
                    .create().apply {
                        setTitle(R.string.soon)
                        setMessage(getString(R.string.soon_message))

                    }.show()
            }
        }
    }

    /** **********************************
     *          Companion
     *********************************** **/
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

}
