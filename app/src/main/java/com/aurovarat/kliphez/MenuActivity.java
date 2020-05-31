package com.aurovarat.kliphez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
    private Button void_mark,creator,feedback;
    public String about;
    public MaterialAlertDialogBuilder materialAlertDialogBuilder,creatordialog;
    public DialogInterface.OnClickListener dialogClickListener;
    public TextView message;
    private Uri selectedImage;
    private ImageView image;
    private CardView card;
    private static final int TAKE_PIC_REQUEST = 0 ;
    private static final int PICK_IMAGE_REQUEST = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        image = findViewById(R.id.image_viewer);
        void_mark = findViewById(R.id.void_mark);
        card = findViewById(R.id.card);
        card.setVisibility(View.GONE);
        creator = findViewById(R.id.creators);

        void_mark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image.setBackgroundResource(R.drawable.potr);
            }
        });
        creator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Screaming",
                        Toast.LENGTH_SHORT).show();
                card.setVisibility(View.VISIBLE);
            }
        });



//        ImageView image = findViewById(R.id.image);
//
//        dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch(which){
//                    case DialogInterface.BUTTON_POSITIVE:
//                        // User clicked the Yes button
//                        selectImage(MenuActivity.this);
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/roksana-goworek-0b6072154/")));
//                        break;
//                    case DialogInterface.BUTTON_NEUTRAL:
//
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/aurovarat")));
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        // User clicked the No button
//
//                        break;
//                }
//            }
//        };
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final DocumentReference docRef = db.collection("void").document("void");
//        final Source source = Source.CACHE;
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                // Document found in the offline cache
//
//                                DocumentSnapshot document = task.getResult();
//                                about = document.getString("about");
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    materialAlertDialogBuilder.setMessage(about + Html.fromHtml("<a href=\"http://www.google.com\">Check this link out</a>", Html.FROM_HTML_MODE_LEGACY));
//                                }
//
//                                Log.d("firestore", "Cached document data: " + document.getData());
//
//                            } else {
//                                Log.d("firestore", "Cached get failed: ", task.getException());
//
//                            }
//                        }
//                    });
//
//                }
//            }
//        });
//        message = new TextView(this);
//        // i.e.: R.string.dialog_message =>
//        // "Test this dialog following the link to dtmilano.blogspot.com"
//        final SpannableString s =
//                new SpannableString("instagra.com/aurovarat");
//        Linkify.addLinks(s, Linkify.WEB_URLS);
//        message.setText(s);
//        message.setMovementMethod(LinkMovementMethod.getInstance());
//        void_mark = findViewById(R.id.void_mark);
//        creator = findViewById(R.id.creators);
//        feedback = findViewById(R.id.feedback);
//        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
//        materialAlertDialogBuilder.setTitle(getString(R.string.app_name));
//
//
//        materialAlertDialogBuilder.setNeutralButton("Okay", null);
//
//
//
//        creatordialog= new MaterialAlertDialogBuilder(this);
//        creatordialog.setTitle("Connect");
//        creatordialog.setMessage("Click the name to get to the respective social media Handles.");
//        creatordialog.setNeutralButton("Auro", dialogClickListener);
//        creatordialog.setPositiveButton("Roksana", dialogClickListener);
//
//
//        // Respond to neutral button press
//
//
//        void_mark.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                materialAlertDialogBuilder.show();
//            }
//        });
//        creator.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stackoverflow.com")));
//                creatordialog.show();
//
//            }
//        });
//        feedback.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                sendEmail();
//
//            }
//        });
//    }
//    protected void sendEmail() {
//        Log.i("Send email", "");
//        String[] TO = {"aurovarat@gmail.com"};
//
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setData(Uri.fromParts("mailto","abc@gmail.com", null));
////        emailIntent.setData(Uri.parse("mailto","abc@gmail.com", null));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback | Void Community");
//
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
//            Log.i("Finished email", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MenuActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void selectImage(Context context){
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, TAKE_PIC_REQUEST);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    chooseImage();
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void chooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_CANCELED) {
//            ImageView imageView = findViewById(R.id.image);
//            switch (requestCode) {
//                case TAKE_PIC_REQUEST:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
//                        selectedImage = getImageUri(getApplicationContext(), Objects.requireNonNull(image));
//                        imageView.setImageBitmap(image);
//                    }
//                    break;
//                case PICK_IMAGE_REQUEST:
//                    if (resultCode == RESULT_OK && data != null) {
//                        selectedImage = data.getData();
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                            imageView.setImageBitmap(bitmap);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//            }
//        }
//    }
    }
}
