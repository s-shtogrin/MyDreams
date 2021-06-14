package com.example.mydreams.dream_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mydreams.R;
import com.example.mydreams.create_dream.CreateDreamActivity;
import com.example.mydreams.databinding.ActivityDreamDetailsBinding;
import com.example.mydreams.main.MainActivity;
import com.example.mydreams.model.Dreams;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class DreamDetailsActivity extends AppCompatActivity implements View.OnClickListener, DreamDetailsContract.View {

    ActivityDreamDetailsBinding binding;

    private DreamDetailsPresenter dreamDetailsPresenter;

    Intent data;

    private Dreams dream;

    private FloatingActionButton editDream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dream_details);

        data = getIntent();
        dream = (Dreams) data.getSerializableExtra("dream");
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = binding.toolbarDetails;

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        editDream = binding.editFab;
        editDream.setOnClickListener(this);

        dreamDetailsPresenter = new DreamDetailsPresenter(this);
        dreamDetailsPresenter.setUpData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_fab:
                goToEditDream();
                break;
        }
    }

    private void goToEditDream() {
        Intent intent = new Intent(getApplicationContext(), CreateDreamActivity.class);
        intent.putExtra("dream", dream);
        intent.putExtra("mode", "edit");

        initTransitionsAnimations(intent);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        initTransitionsAnimations(intent);
    }

    @Override
    public void setDream() {
        int[] colors = getResources().getIntArray(R.array.colors);

        binding.setDreams(dream);
        binding.setColor(colors[dream.getEvaluation()]);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goToMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTransitionsAnimations(Intent intent) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                DreamDetailsActivity.this,

                new Pair<View, String>(findViewById(R.id.relativeLayout_details),
                        getString(R.string.transition_name_layout))
        );
        ActivityCompat.startActivity(DreamDetailsActivity.this, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
        super.onBackPressed();
    }
}