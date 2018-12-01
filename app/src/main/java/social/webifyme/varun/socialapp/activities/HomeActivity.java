package social.webifyme.varun.socialapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import social.webifyme.varun.socialapp.R;
import social.webifyme.varun.socialapp.fragments.HomeFragment;
import social.webifyme.varun.socialapp.fragments.ProfileFragment;
import social.webifyme.varun.socialapp.fragments.SwipeDataFragment;

public class HomeActivity extends AppCompatActivity {
    Fragment fragment;
    Toolbar toolbar;
    ImageButton createPoll;
    FloatingActionButton floatingActionButton;
    private Context context;
    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient mGoogleSignInClient;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setVisibility(View.VISIBLE);
                    fragment=new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment=new SwipeDataFragment();
                    break;
                case R.id.navigation_profile:
                    toolbar.setVisibility(View.GONE);
                    fragment=new ProfileFragment();
                    break;
            }
            if (fragment != null) {
//                Fragment test= getSupportFragmentManager().findFragmentById(R.id.content);
                if(fragment instanceof SwipeDataFragment){
                    floatingActionButton.hide();
                }
                else {
                    floatingActionButton.show();
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
            return true;
        }
    };
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Fragment test= getSupportFragmentManager().findFragmentById(R.id.content);
//        if(test instanceof HomeFragment){
////            floatingActionButton.setVisibility(View.GONE);
//            floatingActionButton.hide();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        toolbar = findViewById(R.id.toolbar);
        context=this;
        firebaseAuth=FirebaseAuth.getInstance();
//        createPoll = findViewById(R.id.create_poll);
        floatingActionButton =findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,CreatePost.class));
            }
        });
//        createPoll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,CreatePost.class));
//            }
//        });
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        fragment=new HomeFragment();
        floatingActionButton.hide();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
//        Fragment test= getSupportFragmentManager().findFragmentById(R.id.content);
//        if(test instanceof SwipeDataFragment){
////            floatingActionButton.setVisibility(View.GONE);
//            floatingActionButton.hide();
//        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.create_poll_menu:
                startActivity(new Intent(HomeActivity.this,CreatePost.class));
                return true;
            case R.id.create_post:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void Logout() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                    firebaseAuth.signOut();
                    mGoogleSignInClient.signOut();
                    //closing activity
                    finish();
                    //starting login activity
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    break;
            case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                    break;
        } } };
        AlertDialog.Builder builder = new AlertDialog.Builder(context); builder.setMessage("Logout?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show(); }

}
