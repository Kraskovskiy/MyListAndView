package com.kab.mylistandview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class ListFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    public static Images sImage;
    private CursorObserver mObserver;
    private ListView mListViewChat;
    private DB mDb;
    private CustomListAdapter mCustomCursorListAdapter;
    private Fragment mFragFullView;
    private FragmentTransaction mFTrans;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        mFragFullView = new FullViewFragment();
        dbConnection();
        createListViewAsync(view);
        return view;
    }

    public void dbConnection() {
        mDb = new DB(getActivity().getApplicationContext());
        mDb.open();
        getLoaderManager().initLoader(0, null, this);
    }

    public void createListViewAsync(View view)  {
        String[] from = new String[]{DBHelper.COLUMN_NAME};
        int[] to = new int[]{R.id.text_item_name};

        mCustomCursorListAdapter = new CustomListAdapter(getActivity().getApplicationContext(), R.layout.item_list, null, from, to, 0);
        mListViewChat = (ListView) view.findViewById(R.id.list_view);
        assert mListViewChat != null;
        mListViewChat.setAdapter(mCustomCursorListAdapter);
        mListViewChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

        mListViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                sImage = mDb.readItem(position);
                FullViewFragment.sBitmap = null;
                createFragmentFullCard();
            }
        });
    }

    public void createFragmentFullCard() {
        if (!Utils.isTablet(getActivity().getApplicationContext())) {
            mFTrans = getFragmentManager().beginTransaction();
            mFTrans.replace(R.id.frame_layout1, mFragFullView, "MY_FRAGMENT_FULL");
            mFTrans.addToBackStack(null);
            mFTrans.commit();
        } else {
            if (Utils.isLandscapeOrientation(getActivity().getApplicationContext())) {
                mFTrans = getFragmentManager().beginTransaction();
                mFTrans.remove(mFragFullView);
                mFTrans.addToBackStack(null);
                mFTrans.commit();

                mFTrans = getFragmentManager().beginTransaction();
                mFTrans.replace(R.id.frame_layout2, mFragFullView, "MY_FRAGMENT_FULL");
                mFTrans.addToBackStack(null);
                mFTrans.commit();
                //Log.e("TAG", "createFragmentFullCard: " );
            } else {
                mFTrans = getFragmentManager().beginTransaction();
                mFTrans.replace(R.id.frame_layout1, mFragFullView, "MY_FRAGMENT_FULL");
                mFTrans.addToBackStack(null);
                mFTrans.commit();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity().getApplicationContext(), mDb);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCustomCursorListAdapter.swapCursor(cursor);
        mObserver = new CursorObserver(new Handler(), loader);
        cursor.registerContentObserver(mObserver);
        cursor.setNotificationUri(getActivity().getApplicationContext().getContentResolver(), DBHelper.URI_TABLE_NAME);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCustomCursorListAdapter.swapCursor(null);
    }
}
