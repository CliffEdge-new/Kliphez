package com.aurovarat.kliphez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirestoreRecyclerAdapter adapter;
    private ImageButton menu;

// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        menu =findViewById(R.id.menu_button);
// normal
        fab = findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchAct.class);
                startActivity(intent);
            }
        });
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            fab.setEnabled(true);


        }
        recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("posts").orderBy("key", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Model,mViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull mViewHolder mViewHolder, int i, @NonNull Model model) {

                mViewHolder.setScream(getApplicationContext(),model.getTitle(),model.getScream(),model.getLocation(),model.getTime());
            }


            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
                return new mViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

    }
    private class mViewHolder extends RecyclerView.ViewHolder {
        private TextView title_text,scream_text,location_text,time_text;

        public mViewHolder(@NonNull View view) {
            super(view);
            title_text = view.findViewById(R.id.card_title);
            scream_text = view.findViewById(R.id.card_scream);
            location_text = view.findViewById(R.id.location);
            time_text = view.findViewById(R.id.datetime);
        }

        void setScream(Context context,String title,String scream,String location,String time) {
            this.title_text.setText(title);
            this.scream_text.setText(scream);
            this.location_text.setText(location);
            this.time_text.setText(time);
        }
        private FirestoreRecyclerAdapter<Model,mViewHolder> adapter;

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}