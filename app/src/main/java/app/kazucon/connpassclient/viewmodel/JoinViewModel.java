package app.kazucon.connpassclient.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import app.kazucon.connpassclient.BR;

/**
 * Created by kazuumi on 17/03/19.
 */

public class JoinViewModel extends BaseObservable {

    private String nickname;

    public JoinViewModel(String nickname) {
        this.nickname = nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.emptyNickname);
    }

    @Bindable
    public boolean isEmptyNickname() {
        return TextUtils.isEmpty(this.nickname);
    }

}
