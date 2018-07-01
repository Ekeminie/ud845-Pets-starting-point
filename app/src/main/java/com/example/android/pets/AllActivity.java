package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetCursorAdapter;
import com.example.android.pets.data.PetDbHelper;

import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_GENDER;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.example.android.pets.data.PetContract.PetEntry.CONTENT_URI;
import static com.example.android.pets.data.PetContract.PetEntry._ID;

public class AllActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{



        // Adapter for the ListView
        PetCursorAdapter mCursorAdapter;

        private static final int PET_LOADER = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all);

            // Setup FAB to open EditorActivity
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllActivity.this, EditorActivity.class);
                    startActivity(intent);
                }
            });


            //Find the ListView which will be populated with the pet data
            ListView petListView = findViewById(R.id.list);

            //Find and set empty view on the ListView, so that it only shows when the list has 0 items.
            View emptyView = findViewById(R.id.empty_view);
            petListView.setEmptyView(emptyView);

            //Set up an Adapter to create a list item for each row of pet data in the Cursor.
            // There is no pet data yet (until the loader finishes ) so pass in null for the Cursor.
            mCursorAdapter = new PetCursorAdapter(this, null);
            petListView.setAdapter(mCursorAdapter);


            // Setup the item click listener
            petListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                    // Create new intent to go to {@link EditorActivity}
                    Intent intent = new Intent(com.example.android.pets.AllActivity.this, EditorActivity.class);

                    // Form the content URI that represents the specific pet that was clicked on,
                    // by appending the "id" ( passed as input to this method) onto the
                    // {@link PetEntry#CONTENT_URI}
                    // For example, the URI would be "content://com.example.android.pets/pets/2"
                    // if the pet with ID 2 was clicked on.
                    Uri currentPetUri =  ContentUris.withAppendedId(PetContract.PetEntry.CONTENT_URI, id);

                    //Set the URI on the data field of the intent
                    intent.setData(currentPetUri);

                    //Launch the {@link EditorActivity} to display the data for the current pet.
                    startActivity(intent);
                }
            });

            // Kick off the Loader
            getLoaderManager().initLoader(PET_LOADER, null, this);
//
//        getLoaderManager().restartLoader(0, null, null);
        }
//       mDbHelper = new PetDbHelper(this);
//        displayDatabaseInfo();


        public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
            // Define a projection that specifies the columns from the table we care about.
            String[] projection = {_ID,
                    COLUMN_PET_NAME,
                    COLUMN_PET_BREED,
                    COLUMN_PET_GENDER,
                    COLUMN_PET_WEIGHT};

            // This loader will execute the ContentProvider's query method on a background thread
            return new CursorLoader(this,   // Parent activity context
                    CONTENT_URI,    // Provider content URI to query
                    projection,     // Columns to include in the resulting Cursor
                    null,           // No selection clause
                    null,           // No selection arguments
                    null);           // Default sort order
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader,Cursor data) {
            // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
            mCursorAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // Callback called when the data needs to be deleted
            mCursorAdapter.swapCursor(null);
        }



        public void displayDatabaseInfo(){
            /**To get access to our database we instantiate the petdbhelper class and
             *pass in this as the context
             */
//        PetDbHelper mDbHelpeer = new PetDbHelper(this);

            //Create and/or open a database to read from it
//        SQLiteDatabase db = mDbHelpeer.getReadableDatabase();

            //Perform a raw-query "Select from pets " to get the total rows in our current database
            //to get a cursor that returns the total number of rows in our database

            //Cursor cursor = db.rawQuery("SELECT * FROM " + PetContract.PetEntry.TABLE_NAME, null);
            String [] projection = {
                    _ID,
                    COLUMN_PET_NAME,
                    COLUMN_PET_BREED,
                    COLUMN_PET_GENDER,
                    COLUMN_PET_WEIGHT
            };

//        Cursor cursor = db.query(
//                PetEntry.TABLE_NAME,
//                 projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );

            Cursor cursor = getContentResolver().query(
                    CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
            TextView displayView =  findViewById(R.id.text_view_pet);

            try {
                // Create a header in the Text View that looks like this:
                //
                // The pets table contains <number of rows in Cursor> pets.
                // _id - name - breed - gender - weight
                //
                // In the while loop below, iterate through the rows of the cursor and display
                // the information from each column in this order.
//            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
//            displayView.append(_ID + " - " +
//                    COLUMN_PET_NAME + " - " +
//                    COLUMN_PET_BREED + " - " +
//                    COLUMN_PET_GENDER + " - " +
//                    COLUMN_PET_WEIGHT + "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(_ID);
                int nameColumnIndex = cursor.getColumnIndex(COLUMN_PET_NAME);
                int breedColumnIndex = cursor.getColumnIndex(COLUMN_PET_BREED);
                int genderColumnIndex = cursor.getColumnIndex(COLUMN_PET_GENDER);
                int weightColumnIndex = cursor.getColumnIndex(COLUMN_PET_WEIGHT);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    String currentBreed = cursor.getString(breedColumnIndex);
                    int currentGender = cursor.getInt(genderColumnIndex);
                    int currentWeight = cursor.getInt(weightColumnIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - " +
                            currentName + " - " +
                            currentBreed + " - " +
                            currentGender + " - " +
                            currentWeight));
                }
            } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }
//
//        try {
//            TextView displayTextview = findViewById(R.id.text_view_pet);
//            displayTextview.setText("Number of rows in pets database table is " + cursor.getCount());
//            }finally {
//            cursor.close();
//        }




        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu options from the res/menu/menu_catalog.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_catalog, menu);
            return true;
        }



        private void insertPet(){

//        SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_PET_NAME, "State");
            values.put(COLUMN_PET_BREED, "Terrier");
            values.put(COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_FEMALE);
            values.put(COLUMN_PET_WEIGHT, 7);

//        long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);
//        Log.v("CatalogActivity", "New Row id: " + newRowId);
            Uri newUri = getContentResolver().insert(CONTENT_URI, values);

        }

        // Helper method to delete all pets in the database
        private void deleteAllPets(){
            int rowsDeleted = getContentResolver().delete(CONTENT_URI, null, null);
            Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // User clicked on a menu option in the app bar overflow menu
            switch (item.getItemId()) {
                // Respond to a click on the "Insert dummy data" menu option
                case R.id.action_insert_dummy_data:
                    insertPet();
//                displayDatabaseInfo();
                    //                displayDatabaseInfo();
//                // Do nothing for now
                    return true;
                // Respond to a click on the "Delete all entries" menu option
                case R.id.action_delete_all_entrie:
                    deleteAllPets();
//                displayDatabaseInfo();
//                displayDatabaseInfo();
                    // Do nothing for now
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

