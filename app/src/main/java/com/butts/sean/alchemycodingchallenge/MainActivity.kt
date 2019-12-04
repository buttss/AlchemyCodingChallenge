package com.butts.sean.alchemycodingchallenge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentManager
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.views.ItemViewHolder
import kotlinx.android.synthetic.main.activity_main.*


const val DEFAULT_TITLE = "Hacker News Top Stories"
class MainActivity: AppCompatActivity(), ItemListFragment.Listener, ItemDetailFragment.Listener {
    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        if (supportFragmentManager.backStackEntryCount == 0) {
            resetToolbar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = DEFAULT_TITLE
        supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                popBackstackAndResetToolbar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onStoryClicked(item: Item, itemViewHolder: ItemViewHolder) {
        if (item.hasText()) {
            // open in detail fragment
            openDetailsForItem(item)
        } else {
            openUrl(item.url)
        }
    }

    private fun openDetailsForItem(item: Item) {
        val id = item.id
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
        transaction.add(R.id.mainContainer, ItemDetailFragment.create(id))
        transaction.commit()
    }

    private fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onStoryFetchError() {
        Toast.makeText(this, "There was an error opening the story", Toast.LENGTH_SHORT).show()
        popBackstackAndResetToolbar()
    }

    override fun onOpenedStory(item: Item) {
        val title = item.title
        showBackButtonAndSetTitle(title)
    }

    private fun showBackButtonAndSetTitle(title: String) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onClosedStory(item: Item) {
        // pop back stack
        popBackstackAndResetToolbar()
    }

    private fun popBackstackAndResetToolbar() {
        supportFragmentManager.popBackStack()
        resetToolbar()
    }

    private fun resetToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = DEFAULT_TITLE
    }
}