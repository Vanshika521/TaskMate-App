package com.codsoft.todolistapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
               // logOut();
                PopupMenu popupmenu  = new PopupMenu(MainActivity.this,menuBtn);
                popupmenu.getMenu().add("Logout");
                popupmenu.show();
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle() == "Logout") {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, login.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        return false;
                    }
                });
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