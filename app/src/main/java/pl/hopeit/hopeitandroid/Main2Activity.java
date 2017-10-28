package pl.hopeit.hopeitandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.hopeit.hopeitandroid.messages.MessagesFragment;
import pl.hopeit.hopeitandroid.model.LoginResponse;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView nameView  = (TextView)header.findViewById(R.id.nameView);
        LoginResponse loginResponse = HopeItApplication.loginResponse;
        nameView.setText(loginResponse.getName());

        ImageView thumbnailView = header.findViewById(R.id.thumbnailView);
        Picasso.with(this)
                .load(loginResponse.getImgUrl())
                .resize(200, 200)
                .centerCrop()
                .into(thumbnailView);

        setFragment(new ChallengesAcceptedFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.messages) {
            setFragment(new MessagesFragment());
        }
        if (id == R.id.actual_challenge) {
            setFragment(new ChallengesAcceptedFragment());
        }
        if (id == R.id.challenge_to_accept) {
            setFragment(new ChallengesToAcceptFragment());
        }
        if (id == R.id.logout) {
            finish();
        }
        if (id == R.id.history) {
            setFragment(new PaymentHistoryFragment());
        }
        if (id == R.id.payment) {
            setFragment(new PaymentFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
    }
}
