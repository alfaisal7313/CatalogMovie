package com.android.arsa.favorite;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import static com.android.arsa.favorite.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {


    private static final int LOAD_ID = 10;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
    }

    private void initComponent() {

        adapter = new CustomAdapter(this, null, true);
        ListView listView = findViewById(R.id.list_item_fav);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        getSupportLoaderManager().initLoader(LOAD_ID, null, this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursorAdapter) {
        adapter.swapCursor(cursorAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_ID);
    }


}
