package social.webifyme.varun.socialapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import social.webifyme.varun.socialapp.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG ="Register" ;
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    String name,email,password,contact,uid;
    Context context;
    FirebaseUser user;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FirebaseApp.initializeApp(this);
        context=this;
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.sign_up_email);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

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


    private void registerUser(){

        //getting email and password from edit texts
        email = editTextEmail.getText().toString().trim();
        password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length()<=6)
        {
            Toast.makeText(this,"Please enter password longer than 6 letters", Toast.LENGTH_LONG).show();
            return;
        }



        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();



        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            user = firebaseAuth.getCurrentUser();
                            if(user!=null) {

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                            }
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }else
//                            {
//                            //display some message here
//                            Toast.makeText(RegisterActivity.this,"Registration Error", Toast.LENGTH_LONG).show();
//                            Log.e("Error", "onComplete: ",task.getException() );
//                        }
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                // show error toast ot user ,user already exist
                                Toast.makeText(RegisterActivity.this,"User already exist", Toast.LENGTH_LONG).show();
                            } catch (FirebaseNetworkException e) {
                                //show error tost network exception
                                Toast.makeText(RegisterActivity.this,"Check your internet connection", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage());
                            }

                    }
                });

    }

}
