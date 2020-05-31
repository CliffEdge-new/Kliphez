package com.aurovarat.kliphez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.provider.Settings.NameValueTable.VALUE;
import static java.sql.Types.TIMESTAMP;

public class PostActivity extends AppCompatActivity {
    public static final String TITLE = "title";
    public static final String SCREAM = "tag";
    public static final String TIME = "time";
    public static final String LOCATION = "location";

    private DocumentReference docRef;
    private Button post, cancel;
    public Snackbar mySnackbar, error,click;
    public TextInputEditText title,scream;
    private String dateString;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mySnackbar = Snackbar.make(findViewById(R.id.main),
                "Screaming", Snackbar.LENGTH_SHORT);
        error = Snackbar.make(findViewById(R.id.main),
                "muffled error", Snackbar.LENGTH_SHORT);
        error.setAction("RETRY", new MyRetryListener());
        mySnackbar.setAction("UNDO", new MyUndoListener());
        // Access a Cloud Firestore instance from your Activity
        post = findViewById(R.id.post);
        cancel = findViewById(R.id.cancel);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postScream();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void postScream() {
        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();

        long millisecond = Long.parseLong(ts);
        String datetimeString = DateFormat.format("EEE, MMM d", new Date(millisecond)).toString();
        String power = DateFormat.format("yyyyMMddHHmmss", new Date(millisecond)).toString();
        dateString = datetimeString.substring(0,10);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        title = findViewById(R.id.title);
        scream = findViewById(R.id.scream);
        String titletext = title.getText().toString();
        String screamtext = scream.getText().toString();
        TimeZone tz = TimeZone.getDefault();
        String location = tz.getID();

        if(screamtext.isEmpty() || titletext.isEmpty()){Toast.makeText(PostActivity.this, "You cannot scream without words",
                Toast.LENGTH_SHORT).show(); return;}
        Map<String, Object> post = new HashMap<String, Object>();
        post.put(TITLE,titletext );
        post.put(SCREAM, screamtext);
        post.put(TIME,datetimeString);
        post.put(LOCATION,location);
        post.put("key", power);

        docRef =  FirebaseFirestore.getInstance().document("posts/"+ currentUser.getUid() + power);
        docRef.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostActivity.this, "Screaming",
                        Toast.LENGTH_SHORT).show();
                Log.d("Firebase", "received");
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.show();
                Log.d("Firebase", "not received");
            }
        });
    }
    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Code to undo the user's last action
        }
    }
    public class MyRetryListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Code to undo the user's last action
        }
    }

}

