package app.kazucon.connpassclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.databinding.ActivityEventBinding;
import app.kazucon.connpassclient.model.EventResponse;

public class EventActivity extends AppCompatActivity {

    private ActivityEventBinding eventBind;

    public static void start(Activity from, EventResponse.Event event) {
        Intent intent = new Intent(from, EventActivity.class);
        intent.putExtra("event", event);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.eventBind = DataBindingUtil.setContentView(this, R.layout.activity_event);

        final EventResponse.Event event = (EventResponse.Event) getIntent().getSerializableExtra("event");

        getSupportActionBar().setTitle(event.title);

        this.eventBind.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.event_url));
                startActivity(intent);
            }
        });

        this.eventBind.webview.loadDataWithBaseURL("", event.description, "text/html", "utf-8", "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
