package com.codsoft.todolistapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    adapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = findViewById(R.id.menuBtn);
        recyclerView = findViewById(R.id.recycleView);

        fBtn = findViewById(R.id.add_notes);


        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Notes_Details.class);
                startActivity(intent);
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setupRecyclerView();
    }
    void setupRecyclerView() {
        Query query = utility.getCollectionReference().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<note> options = new FirestoreRecyclerOptions.Builder<note>().setQuery(query, note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter = new adapter(options, this);
        recyclerView.setAdapter(Adapter);
    }
    @Override
        protected void onStart()
        {
            super.onStart();
            Adapter.startListening();
        }

    @Override
    protected void onStop()
    {
        super.onStop();
        Adapter.stopListening();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Adapter.notifyDataSetChanged();
    }

}