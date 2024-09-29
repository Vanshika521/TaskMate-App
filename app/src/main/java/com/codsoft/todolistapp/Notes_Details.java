package com.codsoft.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Notes_Details extends AppCompatActivity {

    EditText title,content;
    ImageButton saveBtn;
    TextView heading,dltBtn;
    String title1,content1,docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        saveBtn = findViewById(R.id.saveBtn);

        heading = findViewById(R.id.heading);
        dltBtn = findViewById(R.id.dltBtn);

        //receive data
        title1 = getIntent().getStringExtra("title");
        content1 = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty())
        {
            isEditMode = true ;
        }

        title.setText(title1);
        content.setText(content1);
        if(isEditMode)
        {
            heading.setText("Edit Your Note");
            dltBtn.setVisibility(View.VISIBLE);
        }


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
                if(isEditMode)
                {
                    //Edit Note
                    documentReference = utility.getCollectionReference().document(docId);
                }
                else
                {
                    //Create Note
                    documentReference = utility.getCollectionReference().document();
                }
                documentReference.set(Note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //note is successfully added......
                            finish();
                        }
                        else{
                            utility.showToast(Notes_Details.this,"Error Occurred While Adding Notes ");
                        }

                    }
                });
            }
        });

        dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference;
                documentReference = utility.getCollectionReference().document(docId);
                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //note is successfully deleted......
                            utility.showToast(Notes_Details.this,"Note Deleted Successfully");
                            finish();
                        }
                        else{
                            utility.showToast(Notes_Details.this,"Error Occurred While Deleting Notes ");
                        }

                    }
                });
            }
        });


    }
}
