package com.aliumujib.tabbarseed.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseActivity
import com.aliumujib.tabbarseed.utils.Utils
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weird_toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
        HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


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


        switchTab(0)
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


    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {
        super.onStop()
    }


    private fun switchTab(position: Int) {
        mainFragmentNavigation.switchTab(position)
        updateTabSelection(position)
        //        updateToolbarTitle(position);
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
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


    companion object {
        fun start(context: Context) {
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


}
