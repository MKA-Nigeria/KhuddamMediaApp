package com.aliumujib.tabbarseed.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseActivity
import com.aliumujib.tabbarseed.utils.PlayPauseDrawable
import com.aliumujib.tabbarseed.utils.Utils
import com.aliumujib.tabbarseed.utils.extensions.getScreenWidth
import com.aliumujib.tabbarseed.utils.extensions.setWidth
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main_constraints.*
import kotlinx.android.synthetic.main.weird_toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
        HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    private val TAG = javaClass.simpleName

    private val tabIconsNormal = intArrayOf(R.drawable.ic_youtube_black_24dp,
            R.drawable.ic_headphones_black_24dp, R.drawable.ic_magnify_black_24dp,
            R.drawable.ic_account_circle_black_24dp)


    lateinit var tabs: Array<String>


    @Inject
    lateinit var mainFragmentNavigation: IMainFragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_main_constraints)

        tabs = this.resources.getStringArray(R.array.tab_name)

        initToolbar()

        initTab()


        mainFragmentNavigation.setUp()


        bottom_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                mainFragmentNavigation.push(tab.position)

                switchTab(tab.position)

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                mainFragmentNavigation.clearStack()

                switchTab(tab.position)


            }
        })


        val sheetBehavior = BottomSheetBehavior.from(dragView)
        sheetBehavior.setBottomSheetCallback(PanelSlideListener(this))

        play_pause.setImageDrawable(PlayPauseDrawable(this))
        switchTab(0)


        getAllChildren(dragView)

        setViewsAsClickable(false)
    }

    private fun setViewsAsClickable(clickable: Boolean) {
        dragView.isClickable = clickable
        cardView.isClickable = clickable
        scrollView.isClickable = clickable
//        now_playing_constraint_parent.isClickable = clickable
//        image_constraint_parent.isClickable = clickable
//        app_bar_layout.isClickable = clickable
//        imageArt.isClickable = clickable
//        imageCard.isClickable = clickable
//        backgroundView.isClickable = clickable
    }


    private fun getAllChildren(v: View) {
        v.setOnClickListener {
            Log.d(TAG, "$it was clicked and is clickable ${v.isClickable}")
        }
        // v.isEnabled = false


        if (v is ViewGroup) {
            for (i in 0..v.childCount) {

                val child = v.getChildAt(i)

                if (child != null) {
                    Log.d(TAG, "$child is at position $i")
                    child.setOnClickListener {
                        Log.d(TAG, "$it was clicked and is clickable ${child.isClickable}")
                    }
                    // child.isEnabled = false
                    getAllChildren(child)
                }
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initTab() {
        if (bottom_tab_layout != null) {
            for (i in tabs.indices) {
                bottom_tab_layout!!.addTab(bottom_tab_layout!!.newTab())
                val tab = bottom_tab_layout!!.getTabAt(i)
                if (tab != null)
                    tab.customView = getTabView(i)
            }
        }
    }


    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.tab_item_bottom, null)
        val icon = view.findViewById(R.id.tab_icon) as ImageView
        val title = view.findViewById(R.id.tab_title) as TextView
        title.text = tabs[position]
        icon.setImageDrawable(Utils.setDrawableSelector(this@MainActivity, tabIconsNormal[position], tabIconsNormal[position]))
        return view
    }


    private fun switchTab(position: Int) {
        mainFragmentNavigation.switchTab(position)
        updateTabSelection(position)
        //        updateToolbarTitle(position);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            android.R.id.home -> {


                onBackPressed()
                return true
            }
        }


        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        mainFragmentNavigation.handleBackPress()
    }


    private fun updateTabSelection(currentTab: Int) {

        for (i in tabs!!.indices) {
            val selectedTab = bottom_tab_layout!!.getTabAt(i)
            selectedTab!!.customView!!.isSelected = currentTab == i
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainFragmentNavigation.onSaveInstanceState(outState)
    }


    class PanelSlideListener(var activity: MainActivity) : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(p0: View, slideOffset: Float) {
            val cardOpacity = 1 - slideOffset
            val scrollOpacity = slideOffset

            Log.d(this@PanelSlideListener.TAG, "OFSSET: $slideOffset")
            cardView.alpha = cardOpacity
            scrollView.alpha = scrollOpacity
        }

        override fun onStateChanged(p0: View, p1: Int) {
            if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                scrollView.setWidth(0)
                activity.setViewsAsClickable(false)
            } else {
                scrollView.setWidth(activity.getScreenWidth())
                activity.setViewsAsClickable(true)
            }
        }

        private val scrollView = activity.findViewById<ScrollView>(R.id.scrollView)
        private val cardView = activity.findViewById<CardView>(R.id.cardView)
        private val TAG = PanelSlideListener::class.java.simpleName
    }


    companion object {
        fun start(context: Context) {
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


}
