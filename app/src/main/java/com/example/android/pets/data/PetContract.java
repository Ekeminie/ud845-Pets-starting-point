package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Delight on 11/04/2018.
 */

public final class PetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /*Possible path (appended to base content URI for possible URI's)
    * For instance, content://com.android.pets/pets/ is a valid path for
    * looking at pet data. content://com.android.pets/staff/ will fail.
    * as the ContentProvider hasn't been given any information on waht to do with staff
    * */
    public static final String PATH_PETS = "pets";


    private PetContract() {}


    public static final class PetEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         + * The MIME type of the {@link #CONTENT_URI} for a single pet.
         + */


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;


        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        public static final String TABLE_NAME= "pets";

    public  static final String _ID= BaseColumns._ID;
    public static final String COLUMN_PET_NAME = "name";
    public static final String COLUMN_PET_BREED= "breed";
    public static final String COLUMN_PET_GENDER= "gender";
    public static final String COLUMN_PET_WEIGHT= "weight";

        public static final int GENDER_UNKNOWN= 0;
        public static final int GENDER_MALE= 1;
        public static final int GENDER_FEMALE= 2;

}

}
