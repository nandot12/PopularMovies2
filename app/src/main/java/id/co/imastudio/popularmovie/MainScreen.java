package id.co.imastudio.popularmovie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainScreen extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new MostPopularFragment())
                            .commit();
                    getSupportActionBar().setTitle("Most Popular");
                    return true;
                case R.id.navigation_top_rated:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new TopRatedFragment())
                            .commit();
                    getSupportActionBar().setTitle("Top Rated");
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new FavoriteFragment())
                            .commit();
                    getSupportActionBar().setTitle("Favorite");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new MostPopularFragment())
                .commit();
    }
//hallo
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
