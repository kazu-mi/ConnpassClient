package app.kazucon.connpassclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kazuumi on 17/03/18.
 */

public class EventResponse implements Serializable {

    public int results_returned;

    public int results_available;

    public int results_start;

    public List<Event> events;

    public class Event implements Serializable {

        public int event_id;

        public String title;

        @SerializedName("catch")
        public String catch_text;

        public String description;

        public String event_url;

        public String hash_tag;

        public String started_at;

        public String ended_at;

        public int limit;

        public String event_type;

        public Series series;

        public String address;

        public String place;

        public double lat;

        public double lon;

        public int owner_id;

        public String owner_nickname;

        public String owner_display_name;

        public int accepted;

        public int wating;

        public String updated_at;

    }

    public class Series implements Serializable {

        public int id;

        public String title;

        public String url;

    }

}
