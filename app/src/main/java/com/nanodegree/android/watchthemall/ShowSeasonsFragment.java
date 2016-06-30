package com.nanodegree.android.watchthemall;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.nanodegree.android.watchthemall.adapters.SeasonsAdapter;
import com.nanodegree.android.watchthemall.data.WtaContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowSeasonsFragment extends Fragment
        implements WtaTabFragment, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = ShowSeasonsFragment.class.getSimpleName();

    private static final int DETAIL_SHOW_SEASONS_LOADER_ID = 4;

    private static final String[] SEASON_COLUMNS = {
            WtaContract.SeasonEntry._ID,
            WtaContract.SeasonEntry.COLUMN_NUMBER,
            WtaContract.SeasonEntry.COLUMN_AIRED_EPISODES,
            WtaContract.SeasonEntry.COLUMN_EPISODE_COUNT,
            WtaContract.SeasonEntry.COLUMN_SHOW_ID
    };
    // These indices are tied to SEASON_COLUMNS. If SEASON_COLUMNS changes, these must change too.
    public static final int COL_ID = 0;
    public static final int COL_SEASON_NUMBER = 1;
    public static final int COL_SEASON_AIRED_EPISODES = 2;
    public static final int COL_SEASON_EPISODE_COUNT = 3;
    public static final int COL_SHOW_ID = 4;

    @BindView(R.id.showSeasonsList)
    ExpandableListView mShowSeasonsListView;

    private Unbinder mButterKnifeUnbinder;
    private Uri mUri;
    private SeasonsAdapter mSeasonsAdapter;

    public ShowSeasonsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(ShowDetailFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_show_seasons, container, false);
        mButterKnifeUnbinder = ButterKnife.bind(this, rootView);

//        mRootView.setVisibility(View.INVISIBLE);

        getLoaderManager().initLoader(DETAIL_SHOW_SEASONS_LOADER_ID, null, this);

        mSeasonsAdapter = new SeasonsAdapter(null, getActivity());
        mShowSeasonsListView.setAdapter(mSeasonsAdapter);
        mShowSeasonsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Cursor cursor = (Cursor) expandableListView.getExpandableListAdapter().getChild(i, i1);
                if ((cursor != null) && (cursor.getCount()>0)) {
                    Uri uri = WtaContract.EpisodeEntry
                            .buildEpisodeUri(cursor.getInt(SeasonsAdapter.COL_EPISODE_ID));

                    Intent intent =
                            new Intent(ShowSeasonsFragment.this.getActivity(), EpisodeDetailActivity.class)
                                    .setData(uri);
                    startActivity(intent);

                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mButterKnifeUnbinder!=null) {
            mButterKnifeUnbinder.unbind();
        }
    }

    @Override
    public void hideDetailLayout() {
//        mRootView.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            switch (id) {
                case DETAIL_SHOW_SEASONS_LOADER_ID: {
                    return new CursorLoader(getActivity(),
                            WtaContract.SeasonEntry
                                    .buildShowSeasonsUri(Long.valueOf(WtaContract.SeasonEntry
                                            .getShowIdFromUri(mUri))), SEASON_COLUMNS,
                            null, null, WtaContract.SeasonEntry.COLUMN_NUMBER);
                }
            }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        long loaderId = loader.getId();
        if (loaderId == DETAIL_SHOW_SEASONS_LOADER_ID) {
            onDetailShowSeasonsLoadFinished(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        long loaderId = loader.getId();
        if (loaderId == DETAIL_SHOW_SEASONS_LOADER_ID) {
            mSeasonsAdapter.changeCursor(null);
        }
    }

    private void onDetailShowSeasonsLoadFinished(Cursor data) {
        mSeasonsAdapter.changeCursor(data);
    }

}
