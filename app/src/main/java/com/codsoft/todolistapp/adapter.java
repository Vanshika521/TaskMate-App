package com.codsoft.todolistapp;




import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
//import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class adapter extends FirestoreRecyclerAdapter<note,adapter.NoteViewHolder> {

    Context context;

    public adapter(@NonNull FirestoreRecyclerOptions<note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull note model) {

        holder.titleView.setText(model.title);
        holder.contentView.setText(model.content);
        holder.timestampView.setText(utility.timestampToString(model.timestamp));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getBindingAdapterPosition();
                Intent intent = new Intent(context,Notes_Details.class);
                intent.putExtra("title",model.title);
                intent.putExtra("content",model.content);
                String docId = getSnapshots().getSnapshot(currentPosition).getId();
                intent.putExtra("docId", docId);
                context.startActivity(intent);
            }
        });

    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView titleView,contentView,timestampView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.title);
            contentView = itemView.findViewById(R.id.content);
            timestampView = itemView.findViewById(R.id.timestamp);
        }
    }
}
