package com.example.mydreams.notification_settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mydreams.R;
import com.example.mydreams.databinding.ActivityNotificationSettingsBinding;
import com.example.mydreams.dream_details.DreamDetailsActivity;
import com.example.mydreams.main.MainActivity;
import com.example.mydreams.notifications.AppNotificationManager;
import com.example.mydreams.notifications.NotificationContract;

import java.util.Calendar;
import java.util.Objects;

public class NotificationSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TimePickerDialog.OnTimeSetListener, NotificationSettingsContract.View {

    ActivityNotificationSettingsBinding binding;

    private String[] settingsOptions;


    private TextView notificationStatus;

    Calendar notificationsTime = Calendar.getInstance();

    private NotificationSettingsPresenter notificationSettingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_settings);
        initViews();
    }

    private void initViews() {
        notificationStatus = binding.tvNotificationStatus;

        Toolbar toolbar = binding.toolbarSettings;

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        ListView listView = binding.listView;
        listView.setOnItemClickListener(this);

        settingsOptions = getResources().getStringArray(R.array.notification_settings_options);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_list_settings, settingsOptions);
        listView.setAdapter(adapter);

        notificationSettingsPresenter = new NotificationSettingsPresenter(this);
        notificationSettingsPresenter.checkNotificationStatus();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedOption = settingsOptions[position];

        if (selectedOption.equals(getString(R.string.notification_settings_option_1))) {
            setTime();
        } else if (selectedOption.equals(getString(R.string.notification_settings_option_2))) {
            notificationSettingsPresenter.removeNotification(getApplicationContext());
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        initTransitionsAnimations(intent);
    }

    public void setTime() {
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(NotificationSettingsActivity.this, this,
                notificationsTime.get(Calendar.HOUR_OF_DAY),
                notificationsTime.get(Calendar.MINUTE), true);

        timePickerDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_design);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        notificationsTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        notificationsTime.set(Calendar.MINUTE, minute);
        notificationsTime.set(Calendar.SECOND, 0);
        notificationsTime.set(Calendar.MILLISECOND, 0);

        long millis = notificationsTime.getTimeInMillis();

        notificationSettingsPresenter.setAlarm(getApplicationContext(), millis);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notificationOn() {
        notificationStatus.setText("вкл");
    }

    @Override
    public void notificationOff() {
        notificationStatus.setText("выкл");
    }

    private void initTransitionsAnimations(Intent intent) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                NotificationSettingsActivity.this,

                new Pair<View, String>(findViewById(R.id.relativeLayout),
                        getString(R.string.transition_name_layout))
        );
        ActivityCompat.startActivity(NotificationSettingsActivity.this, intent, options.toBundle());

        finish();
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
        super.onBackPressed();
    }
}