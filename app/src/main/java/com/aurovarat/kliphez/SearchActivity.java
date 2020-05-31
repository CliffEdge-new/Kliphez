package com.aurovarat.kliphez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.lang.reflect.Array;

public class Search_Activity extends AppCompatActivity {
    private Button void_mark,creator,feedback;
    public String about;
    public MaterialAlertDialogBuilder materialAlertDialogBuilder,creatordialog;
    public DialogInterface.OnClickListener dialogClickListener;
    public TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // User clicked the Yes button
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/roksana-goworek-0b6072154/")));
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/aurovarat")));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // User clicked the No button

                        break;
                }
            }
        };
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("void").document("void");
        final Source source = Source.CACHE;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                // Document found in the offline cache

                                DocumentSnapshot document = task.getResult();
                                about = document.getString("about");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    materialAlertDialogBuilder.setMessage(about + Html.fromHtml("<a href=\"http://www.google.com\">Check this link out</a>", Html.FROM_HTML_MODE_LEGACY));
                                }

                                Log.d("firestore", "Cached document data: " + document.getData());

                            } else {
                                Log.d("firestore", "Cached get failed: ", task.getException());

                            }
                        }
                    });

                }
            }
        });
        message = new TextView(this);
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"
        final SpannableString s =
                new SpannableString("instagra.com/aurovarat");
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        void_mark = findViewById(R.id.void_mark);
        creator = findViewById(R.id.creators);
        feedback = findViewById(R.id.feedback);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle(getString(R.string.app_name));


        materialAlertDialogBuilder.setNeutralButton("Okay", null);



        creatordialog= new MaterialAlertDialogBuilder(this);
        creatordialog.setTitle("Connect");
        creatordialog.setMessage("Click the name to get to the respective social media Handles.");
        creatordialog.setNeutralButton("Auro", dialogClickListener);
        creatordialog.setPositiveButton("Roksana", dialogClickListener);


        // Respond to neutral button press


        void_mark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                materialAlertDialogBuilder.show();
            }
        });
        creator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stackoverflow.com")));
                creatordialog.show();

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail();

            }
        });
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"aurovarat@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.fromParts("mailto","abc@gmail.com", null));
//        emailIntent.setData(Uri.parse("mailto","abc@gmail.com", null));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback | Void Community");


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Search_Activity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}


