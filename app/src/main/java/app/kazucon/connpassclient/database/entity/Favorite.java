package app.kazucon.connpassclient.database.entity;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by kazuumi on 17/03/20.
 */

@RealmClass
public class Favorite implements RealmModel {

    public int eventId;

}
