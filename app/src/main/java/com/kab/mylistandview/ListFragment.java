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
    private CursorObserver mObserver;
    private ListView mListViewChat;
    private DB mDB;
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
        mDB = new DB(getActivity().getApplicationContext());
        mDB.open();
        getLoaderManager().initLoader(0, null, this);
    }

    public void createListViewAsync(View view)  {
        String[] from = new String[]{DBHelper.COLUMN_NAME};
        int[] to = new int[]{R.id.text_Item_Name};

        mCustomCursorListAdapter = new CustomListAdapter(getActivity().getApplicationContext(), R.layout.item_list, null, from, to, 0);
        mListViewChat = (ListView) view.findViewById(R.id.listView);
        assert mListViewChat != null;
        mListViewChat.setAdapter(mCustomCursorListAdapter);
        mListViewChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

        mListViewChat.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListViewChat.setSelection(mListViewChat.getCount());
            }
        }, 500);

        mListViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                createFragmentFullCard();
            }
        });
    }

    public void createFragmentFullCard() {
        mFTrans = getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.frameLayout1, mFragFullView, "MY_FRAGMENT_FULL");
        mFTrans.addToBackStack(null);
        mFTrans.commit();
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity().getApplicationContext(), mDB);
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
