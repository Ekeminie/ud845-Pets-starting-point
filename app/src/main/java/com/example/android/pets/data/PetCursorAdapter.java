package com.example.android.pets.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.R;

/**
 * Created by Delight on 12/04/2018.
 */

public class PetCursorAdapter extends CursorAdapter {

    // Construct a new @link PetCursorAdapter
    // @param context The context
    // @param c The cursor from which to get the data

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    // Makes a new blank list item view. No data is set (or bound) to the views yet
    // @param context app context
    // @param cursor The cursor from which to get the data. The cursor is already
    //  moved to the correct position
    // @param parent The parent to which the new view is attached to
    // @return the newly created list item view

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // The newView method is used to inflate a new view and return it
        // you don't bind any data to the view at this point

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // This method binds the pet data ( in the current row pointed to by cursor) to the given
    // list item layout. For example, the name for the current pet can be set on the name TextView
    // in the list item layout
    // @param view Existing view, returned earlier by newView() method.
    // @param context app context
    // @param cursor The cursor from which to get the data. The cursor is already moved to the
    // correct row
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView

        /* Find fields to populate in inflated template*/
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of pet attributes we are interested in
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);

        // Extract pet attributes from the cursor for the current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        // Populate fields with extracted properties for the current pet
        nameTextView.setText(petName);
        summaryTextView.setText(petBreed);
    }
}
