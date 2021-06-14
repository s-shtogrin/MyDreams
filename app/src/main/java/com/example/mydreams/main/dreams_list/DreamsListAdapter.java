package com.example.mydreams.main.dreams_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydreams.R;
import com.example.mydreams.databinding.ItemLayoutBinding;
import com.example.mydreams.dream_details.DreamDetailsActivity;
import com.example.mydreams.main.MainActivity;
import com.example.mydreams.main.MainContract;
import com.example.mydreams.main.MainPresenter;
import com.example.mydreams.model.Dreams;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DreamsListAdapter {

    private MainPresenter mainPresenter;

    private FirestoreRecyclerAdapter<Dreams, DreamsListViewHolder> adapter;

    int selectedPosition = -1;
    boolean[] selects;

    private Context context;

    public DreamsListAdapter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public FirestoreRecyclerAdapter<Dreams, DreamsListViewHolder> SetAdapter(int[] colors) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseFirestore.getInstance()
                .collection("dreams")
                .document(firebaseUser.getUid())
                .collection("myDreams")
                .orderBy("date", Query.Direction.DESCENDING);;

        FirestoreRecyclerOptions<Dreams> allUserDreams = new FirestoreRecyclerOptions
                                                            .Builder<Dreams>()
                                                            .setQuery(query, Dreams.class).build();

        adapter = new FirestoreRecyclerAdapter<Dreams, DreamsListViewHolder>(allUserDreams) {
            @NonNull
            @Override
            public DreamsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ItemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_layout, parent, false);

                selects = new boolean[getItemCount()];
                binding.setIsItemSelected(false);

                context = parent.getContext();

                return new DreamsListViewHolder(binding);
            }

            @Override
            protected void onBindViewHolder(@NonNull DreamsListViewHolder dreamsListViewHolder, int i, @NonNull Dreams dreams) {
                final String docId = adapter.getSnapshots().getSnapshot(i).getId();
                final int colorData = colors[dreams.getEvaluation()];

                dreamsListViewHolder.bind(dreams, colorData);
                dreamsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainPresenter.onItemClicked(docId);
                    }
                });

//                dreamsListViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        if (dreamsListViewHolder.binding.getIsItemSelected()) {
//                            selects[position] = false;
//                            dreamsListViewHolder.binding.setIsItemSelected(false);
//
//                            Animation zoomOut = AnimationUtils.loadAnimation(v.getContext(), R.anim.zoom_out);
//                            dreamsListViewHolder.binding.deleteLayout.startAnimation(zoomOut);
//                        }
//                        else {
//                            selects[position] = true;
//                            dreamsListViewHolder.binding.setIsItemSelected(true);
//
//                            Animation zoomIn = AnimationUtils.loadAnimation(v.getContext(), R.anim.zoom_in);
//                            dreamsListViewHolder.binding.deleteLayout.startAnimation(zoomIn);
//                        }
//                        //notifyDataSetChanged();
//                        return true;
//                    }
//                });

                if (!dreamsListViewHolder.binding.getIsItemSelected() && selectedPosition == i) {
                    dreamsListViewHolder.binding.setIsItemSelected(true);

                    //Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
                    //dreamsListViewHolder.binding.deleteLayout.startAnimation(zoomIn);
                }
                else {
                    dreamsListViewHolder.binding.setIsItemSelected(false);

                    //Animation zoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
                    //dreamsListViewHolder.binding.deleteLayout.startAnimation(zoomOut);
                }

                dreamsListViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        selectedPosition = i;
                        notifyDataSetChanged();
                        return true;
                    }
                });

                dreamsListViewHolder.binding.deleteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainPresenter.onItemDeleteClicked(docId);
                    }
                });
            }
        };
        return adapter;
    }
}
