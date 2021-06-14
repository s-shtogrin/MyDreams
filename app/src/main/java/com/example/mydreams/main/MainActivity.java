package com.example.mydreams.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mydreams.R;
import com.example.mydreams.create_dream.CreateDreamActivity;
import com.example.mydreams.databinding.ActivityMainBinding;
import com.example.mydreams.dialog.ActionBottomDialogFragment;
import com.example.mydreams.dialog.AppDialogFragment;
import com.example.mydreams.dream_details.DreamDetailsActivity;
import com.example.mydreams.login.LoginActivity;
import com.example.mydreams.main.dreams_list.DreamsListAdapter;
import com.example.mydreams.main.dreams_list.DreamsListViewHolder;
import com.example.mydreams.model.Dreams;
import com.example.mydreams.notification_settings.NotificationSettingsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AppDialogFragment.DialogListener, ActionBottomDialogFragment.ActionBottomDialogListener, MainContract.View{

    ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private FloatingActionButton createDream;
    private LinearLayout notificationSettings;
    private ImageView logout;
    private ImageView notificationImg;

    FirestoreRecyclerAdapter<Dreams, DreamsListViewHolder> adapter;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        mainPresenter.userIdentification();
    }

    private void initViews() {
        recyclerView = binding.rvDreamsList;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        notificationImg = binding.btnNotificationsImg;
        notificationImg.setImageResource(R.drawable.ic_notification_off);

        notificationSettings = binding.btnNotifications;
        notificationSettings.setOnClickListener(this);
        createDream = binding.fabCreateDream;
        createDream.setOnClickListener(this);
        logout = binding.btnLogout;
        logout.setOnClickListener(this);

        int[] colors = getResources().getIntArray(R.array.colors);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        adapter = new DreamsListAdapter(mainPresenter).SetAdapter(colors);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        mainPresenter.checkNotificationStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_dream:
                goToCreateDream();
                break;
            case R.id.btn_notifications:
                goToNotificationSettings();
                break;
            case R.id.btn_logout:
                showActionBottomDialog();
                break;
        }
    }

    private void goToSignIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToCreateDream() {
        Intent intent = new Intent(getApplicationContext(), CreateDreamActivity.class);
        intent.putExtra("mode", "create");

        initTransitionsAnimations(intent);
    }

    private void goToNotificationSettings() {
        Intent intent = new Intent(getApplicationContext(), NotificationSettingsActivity.class);

        initTransitionsAnimations(intent);
    }

    private void initTransitionsAnimations(Intent intent) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,

                new Pair<View, String>(findViewById(R.id.dreams_list_layout),
                        getString(R.string.transition_name_layout))
        );
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
    }

    private void initLogout() {
        mainPresenter.logout();
    }

    private void initDelete() {
       mainPresenter.delete();
    }

    @Override
    public void userIdentified() {
        initViews();
        mainPresenter.setUpNotificationChannels(this);
    }

    @Override
    public void userNotIdentified() {
        goToSignIn();
    }

    @Override
    public void showDreamDetails(Dreams dream) {
        Intent intent = new Intent(getApplicationContext(), DreamDetailsActivity.class);
        intent.putExtra("dream", dream);

        initTransitionsAnimations(intent);
    }

    @Override
    public void showDialog(String message) {
        AppDialogFragment dialog = AppDialogFragment.newInstance(message);
        dialog.show(getSupportFragmentManager(), "missiles");
    }

    public void showActionBottomDialog() {
        ActionBottomDialogFragment actionBottomDialogFragment = ActionBottomDialogFragment.newInstance();
        actionBottomDialogFragment.show(getSupportFragmentManager(), "missiles");
    }

    @Override
    public void notificationOn() {
        notificationImg.setImageResource(R.drawable.ic_notification);
    }

    @Override
    public void notificationOff() {
        notificationImg.setImageResource(R.drawable.ic_notification_off);
    }

    @Override
    public void onDeleteDataSuccess(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteDataFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLogOutClick() {
        initLogout();
    }

    @Override
    public void userLoggedOut() {
        goToSignIn();
    }

    @Override
    public void onDialogPositiveClick() {
        initDelete();
    }



    @Override
    protected void onStart() {
        super.onStart();

        //adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();

//        if (adapter != null)
//            adapter.stopListening();
    }
}