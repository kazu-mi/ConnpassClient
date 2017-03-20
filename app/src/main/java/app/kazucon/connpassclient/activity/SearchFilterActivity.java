package app.kazucon.connpassclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.databinding.ActivitySearchFilterBinding;

public class SearchFilterActivity extends AppCompatActivity {

    public static void start(Activity from) {
        Intent intent = new Intent(from, SearchFilterActivity.class);
        from.startActivity(intent);
    }

    private ActivitySearchFilterBinding searchFilterBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.searchFilterBind = DataBindingUtil.setContentView(this, R.layout.activity_search_filter);

        this.searchFilterBind.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.start(SearchFilterActivity.this, searchFilterBind.keyword.getText().toString());
            }
        });
    }
}
