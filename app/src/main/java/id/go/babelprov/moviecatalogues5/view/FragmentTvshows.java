package id.go.babelprov.moviecatalogues5.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.go.babelprov.moviecatalogues5.R;


public class FragmentTvshows extends Fragment {

    TabLayout mTab;
    ViewPager mPager;
    ViewPagerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        View view =  inflater.inflate(R.layout.fragment_tvshows, container, false);

        mTab =  view.findViewById(R.id.tab);
        mPager = view.findViewById(R.id.pager);

        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);

        return view;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        String[] mTitle = {
                getString(R.string.ontheair),
                getString(R.string.toprated),
                getString(R.string.favorite)
        };

        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment;

            switch (position) {
                case 1:
                    fragment = new FragmentTvshowsTopRated();
                    break;
                case 2:
                    fragment = new FragmentTvshowsFavorite();
                    break;
                default:
                    fragment = new FragmentTvshowsOnTheAir();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_tvshows, menu);
    }

}
