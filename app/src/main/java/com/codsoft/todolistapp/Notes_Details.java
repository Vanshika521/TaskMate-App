package com.codsoft.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Notes_Details extends AppCompatActivity {

    EditText title,content;
    ImageButton saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notes_title = title.getText().toString();
                String notes_content = content.getText().toString();

                if(notes_title == null || notes_title.isEmpty())
                {
                    title.setError("Title Is Required!!!");
                    return;
                }

                note Note = new note();
                Note.setTitle(notes_title);
                Note.setContent(notes_content);
                Note.setTimestamp(Timestamp.now());

                saveNote(Note);
            }

            void saveNote (note Note)
            {
                DocumentReference documentReference;
                documentReference = utility.getCollectionReference().document();
                documentReference.set(Note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //note is successfully added......
                            utility.showToast(Notes_Details.this,"Notes Added Successfully");
                            finish();
                        }
                        else{
                            utility.showToast(Notes_Details.this,"Error Occurred While Adding Notes ");
                        }

                    }
                });
            }
        });
    }
}