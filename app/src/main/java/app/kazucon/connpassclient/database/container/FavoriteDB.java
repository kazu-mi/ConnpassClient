package app.kazucon.connpassclient.database.container;

import android.content.Context;

import app.kazucon.connpassclient.database.entity.Favorite;
import io.realm.Realm;

/**
 * Created by kazuumi on 17/03/20.
 */

public class FavoriteDB {

    public FavoriteDB(Context context) {
        Database.init(context);
    }

    public void insert(final int eventId) {
        Database.getRealmInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Favorite fav    = new Favorite();
                fav.eventId     = eventId;
                realm.insertOrUpdate(fav);
            }
        });
    }

    public void delete(final int eventId) {
        Database.getRealmInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Favorite.class).equalTo("eventId", eventId).findAll().deleteAllFromRealm();
            }
        });
    }

    public boolean exists(final int eventId) {
        return (Database.getRealmInstance().where(Favorite.class).equalTo("eventId", eventId).findAll().size() > 0);
    }

}
