package com.example.mydreams.create_dream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydreams.DateConverter;
import com.example.mydreams.R;
import com.example.mydreams.databinding.ActivityCreateDreamBinding;
import com.example.mydreams.main.MainActivity;
import com.example.mydreams.model.Dreams;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateDreamActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CreateDreamContract.View {

    private static final String ARG_MODE = "mode";
    private static final String ARG_DREAM = "dream";
    private static final String ARG_REPLY_TEXT = "replyText";

    ActivityCreateDreamBinding binding;

    private TextView date, evaluation;
    private EditText title, content;
    private ImageView btnDateBefore, btnDateAfter;
    private SeekBar evaluationSeekBar;
    private FloatingActionButton saveDream;

    private CreateDreamPresenter createDreamPresenter;

    private String mode;
    private String dreamId;
    private Dreams dream;

    private String contentFromNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_dream);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            mode = arguments.getString(ARG_MODE);
            dream = (Dreams) arguments.getSerializable(ARG_DREAM);
            dreamId = dream != null ? dream.getId() : "id";

            contentFromNotification = arguments.getString(ARG_REPLY_TEXT);
        }

        initViews();
    }

    private void initViews() {
        date = binding.tvDateCreate;
        evaluation = binding.tvDreamEvaluation;
        title = binding.etDreamTitle;
        content = binding.etDreamContent;

        evaluationSeekBar = binding.seekBar;
        evaluationSeekBar.setOnSeekBarChangeListener(this);

        btnDateBefore = binding.btnDateBefore;
        btnDateBefore.setOnClickListener(this);
        btnDateAfter = binding.btnDateAfter;
        btnDateAfter.setOnClickListener(this);
        btnDateAfter.setVisibility(View.INVISIBLE);
        saveDream = binding.saveFab;
        saveDream.setOnClickListener(this);

        date.setText(DateConverter.getTodayDate());

        createDreamPresenter = new CreateDreamPresenter(this);
        createDreamPresenter.setUpData(mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_fab:
                saveDream();
                break;
            case R.id.btn_date_before:
                date.setText(DateConverter.getClosestDate(date.getText().toString(), -1));
                if (!date.getText().toString().equals(DateConverter.getTodayDate())) {
                    btnDateAfter.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_date_after:
                date.setText(DateConverter.getClosestDate(date.getText().toString(), 1));
                if (date.getText().toString().equals(DateConverter.getTodayDate())) {
                    btnDateAfter.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        initTransitionsAnimations(intent);
    }

    private void saveDream() {
        String newTitle = title.getText().toString();
        int newEvaluation = Integer.parseInt(evaluation.getText().toString());
        String newContent = content.getText().toString();
        //String newTag = tag.getText().toString();
        String newTag = "tag";
        long newDate = DateConverter.getTimeInMillisFromString(date.getText().toString());

        if (newTitle.isEmpty() && newContent.isEmpty()) {
            goToMainActivity();
        } else {
            createDreamPresenter.save(mode, dreamId, newTitle, newEvaluation, newContent, newTag, newDate);
        }
    }

    @Override
    public void setDream() {
        binding.setDreams(dream);
        binding.setMode("edit");
        if (!DateConverter.getStringFromTimeInMillis(dream.getDate()).equals(DateConverter.getTodayDate())) {
            btnDateAfter.setVisibility(View.VISIBLE);
        }
        evaluationSeekBar.setProgress(dream.getEvaluation());
    }

    @Override
    public void setDreamFromNotification() {
        binding.setContentFromNotification(contentFromNotification);
    }

    @Override
    public void onSaveDreamSuccess(String message) {
        goToMainActivity();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveDreamFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        evaluation.setText(String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void initTransitionsAnimations(Intent intent) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                CreateDreamActivity.this,

                new Pair<View, String>(findViewById(R.id.linearLayout_create),
                        getString(R.string.transition_name_layout))
        );
        ActivityCompat.startActivity(CreateDreamActivity.this, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
        super.onBackPressed();
    }
}