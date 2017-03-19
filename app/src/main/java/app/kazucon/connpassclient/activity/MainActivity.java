package app.kazucon.connpassclient.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.adapter.MainContentsPagerAdapter;
import app.kazucon.connpassclient.databinding.ActivityMainBinding;
import app.kazucon.connpassclient.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBind;
    private MainContentsPagerAdapter mainContentsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.mainBind.setMainViewModel(new MainViewModel());

        this.mainContentsPagerAdapter = new MainContentsPagerAdapter(this.getSupportFragmentManager());
        this.mainBind.pager.setAdapter(mainContentsPagerAdapter);
        this.mainBind.getMainViewModel().setTitle(getString(mainContentsPagerAdapter.titleId[0]));

        this.mainBind.tab.setupWithViewPager(this.mainBind.pager);
        for (int i = 0; i < mainContentsPagerAdapter.iconId.length; i++) {
            this.mainBind.tab.getTabAt(i).setIcon(mainContentsPagerAdapter.iconId[i]);
        }

        this.mainBind.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mainBind.getMainViewModel()
                        .setTitle(getString(mainContentsPagerAdapter.titleId[position]));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}
