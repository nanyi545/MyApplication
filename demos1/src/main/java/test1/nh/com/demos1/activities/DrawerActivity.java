package test1.nh.com.demos1.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import test1.nh.com.demos1.R;
import test1.nh.com.demos1.fragments.AnimationFragment;
import test1.nh.com.demos1.fragments.AnimationFragment2;
import test1.nh.com.demos1.fragments.EventBusDemoFragment;
import test1.nh.com.demos1.fragments.GravityFragment;
import test1.nh.com.demos1.fragments.MPchartDemoFragment;
import test1.nh.com.demos1.fragments.MultiThreadFrag;
import test1.nh.com.demos1.fragments.NavigationDrawerFragment;
import test1.nh.com.demos1.fragments.Section4Fragment;
import test1.nh.com.demos1.fragments.SectionFragment;
import test1.nh.com.demos1.fragments.SocketIOFragment;

public class DrawerActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position){
            case 8:  //position+1=9    section 9 -->animation fragment 2
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AnimationFragment2.newInstance(position + 1))
                        .commit();
                break;

            case 7:  //position+1=8    section 8 -->multiThread fragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MultiThreadFrag.newInstance(position + 1))
                        .commit();
                break;
            case 6:  //position+1=7    section 7 -->GravityFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GravityFragment.newInstance(position + 1))
                        .commit();
                break;
            case 5:  //position+1=6    section 6 -->SocketIOFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SocketIOFragment.newInstance(position + 1))
                        .commit();
                break;
            case 4:  //position+1=5    section 5 -->EventBusDemoFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, EventBusDemoFragment.newInstance(position + 1))
                        .commit();
                break;
            case 3:  //position+1=4    section 4 采用Section4Frag  notification demo + ripple ViewDemo
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Section4Fragment.newInstance(position + 1))
                        .commit();
                break;
            case 2:  //position+1=3    section 3 MPchartDemoFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MPchartDemoFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:  //position+1=2    section 2 AnimationFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AnimationFragment.newInstance(position + 1))
                        .commit();
                break;
            default:  //其它情况
                fragmentManager.beginTransaction()
                    .replace(R.id.container, SectionFragment.newInstance(position + 1))
                    .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
