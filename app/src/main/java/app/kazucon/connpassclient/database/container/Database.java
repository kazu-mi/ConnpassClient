package app.kazucon.connpassclient.database.container;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by kazuumi on 17/03/20.
 */

class Database {

    private Database(){}

    private static Realm realm;

    public static void init(Context context) {
        if (Database.realm == null) {
            Realm.init(context);

            Database.realm = Realm.getDefaultInstance();
        }
    }

    public static Realm getRealmInstance() {
        return Database.realm;
    }

}
