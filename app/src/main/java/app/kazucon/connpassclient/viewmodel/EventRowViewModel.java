package app.kazucon.connpassclient.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.kazucon.connpassclient.BR;
import app.kazucon.connpassclient.model.EventResponse;

/**
 * Created by kazuumi on 17/03/18.
 */

public class EventRowViewModel extends BaseObservable {

    private String groupName;

    private String eventTitle;

    private String eventCatch;

    private String ownerName;

    private String address;

    private int accepted;

    private int limit;

    private String started_at;

    private final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private final String[] DAY_OF_WEEKS = {
            "", "日", "月", "火", "水", "木", "金", "土"
    };

    public EventRowViewModel() {}

    public EventRowViewModel(EventResponse.Event event) {
        setEvent(event);
    }

    public void setEvent(EventResponse.Event event) {
        if (event.series == null) {
            this.groupName  = "";
        } else {
            this.groupName  = event.series.title;
        }
        this.eventTitle     = event.title;
        this.eventCatch     = event.catch_text;
        this.ownerName      = event.owner_nickname;
        if (TextUtils.isEmpty(event.address)) {
            this.address    = "（場所未定）";
        } else {
            this.address    = event.address;
        }
        this.accepted       = event.accepted;
        this.limit          = event.limit;
        this.started_at     = event.started_at;
    }

    @Bindable
    public String getGroupName() {
        return groupName;
    }

    @Bindable
    public String getEventTitle() {
        return eventTitle;
    }

    @Bindable
    public String getEventCatch() {
        return eventCatch;
    }

    @Bindable
    public String getOwnerName() {
        return ownerName;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    @Bindable
    public int getAccepted() {
        return accepted;
    }

    @Bindable
    public int getLimit() {
        return limit;
    }

    @Bindable
    public String getStartedYear() {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(this.started_at));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    @Bindable
    public String getStartedDate() {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(this.started_at));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        return String.format("%02d/%02d",
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Bindable
    public String getStartedTime() {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(this.started_at));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        return String.format("(%s)%02d:%02d~",
                DAY_OF_WEEKS[calendar.get(Calendar.DAY_OF_WEEK)],
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
    }

    @Bindable
    public String getJoinCount() {
        if (this.limit == 0) {
            return String.valueOf(accepted);
        }

        return accepted + "/" + limit;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        notifyPropertyChanged(BR.groupName);
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
        notifyPropertyChanged(BR.eventTitle);
    }

    public void setEventCatch(String eventCatch) {
        this.eventCatch = eventCatch;
        notifyPropertyChanged(BR.eventCatch);
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        notifyPropertyChanged(BR.ownerName);
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
        notifyPropertyChanged(BR.accepted);
    }

    public void setLimit(int limit) {
        this.limit = limit;
        notifyPropertyChanged(BR.limit);
    }
}
