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
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.MovieAdapter;
import id.go.babelprov.moviecatalogues5.viewmodel.MovieSearchViewModel;


public class FragmentMovies extends Fragment {

    TabLayout mTab;
    ViewPager mPager;
    public static MovieAdapter movieListAdapter;
    ViewPagerAdapter mAdapter;
    MovieSearchViewModel movieSearchViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);

        mTab =  view.findViewById(R.id.tab);
        mPager = view.findViewById(R.id.pager);

        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);

        movieSearchViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);

        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        String[] mTitle = {
                getString(R.string.nowplaying),
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
                    fragment = new FragmentMoviesTopRated();
                    break;
                case 2:
                    fragment = new FragmentMoviesFavorite();
                    break;
                default:
                    fragment = new FragmentMoviesNowPlaying();
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
        inflater.inflate(R.menu.menu_action_movies, menu);
    }

}
