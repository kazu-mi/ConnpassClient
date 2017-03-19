package app.kazucon.connpassclient.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import app.kazucon.connpassclient.BR;

/**
 * Created by kazuumi on 17/03/18.
 */

public class MainViewModel extends BaseObservable {

    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

}
