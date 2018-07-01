package com.chandlerobaker.alcchallenge.android.journalapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.chandlerobaker.alcchallenge.android.journalapp.database.DiaryEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DiaryAdapter.ListItemClickListener {

    public final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final String PREF_APP = "appSharedPreferences";
    static final String EXTRA_USER_ACCOUNT_ID = "USER_ACCOUNT_ID";
    static final String EXTRA_DIARY_ID = "diaryId";
    static final String EXTRA_DIARY_CONTENT = "diaryContent";
    static final String EXTRA_DIARY_MOOD = "diaryMood";
    static final String EXTRA_DIARY_DATE_ENREG = "diaryDateEnreg";
    private DiaryAdapter mDiaryAdapter;
    private RecyclerView mRecyclerView;
    private List<DiaryEntry> mDiaryEntries;
    private DatabaseReference mDatabaseReferenceDiaries;
    private String mUserId;
    private EditText mEditTextThoughts;
    private int mRadioButtonMoodCheckedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        launchCorrectActivity();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_thoughts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mDiaryEntries = new ArrayList<>();


        mEditTextThoughts = (EditText) findViewById(R.id.edittext_setThoughts);
        Button btn_addThoughts = (Button) findViewById(R.id.button_add_thoughts);


        btn_addThoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addThouhts();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                AlertDialog.Builder builderAlert = new AlertDialog.Builder(MainActivity.this);
                builderAlert.setTitle(getResources().getText(R.string.alert_delete_title));
                builderAlert.setMessage(getResources().getText(R.string.alert_delete_message));
                builderAlert.setCancelable(true);
                builderAlert.setPositiveButton(
                        getResources().getText(R.string.action_delete),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                deleteThoughts(position);
                            }
                        });

                builderAlert.setNegativeButton(
                        getResources().getText(R.string.action_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                mRecyclerView.setAdapter(mDiaryAdapter);
                            }
                        });

                AlertDialog alert = builderAlert.create();
                alert.show();

            }
        }).attachToRecyclerView(mRecyclerView);


    }

    @Override
    protected void onStart() {
        super.onStart();


        mDatabaseReferenceDiaries.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDiaryEntries.clear();

                ArrayList<DiaryEntry> tempArrayList = new ArrayList<>();
                tempArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiaryEntry diaryEntry = snapshot.getValue(DiaryEntry.class);
                    tempArrayList.add(diaryEntry);

                }

                for (int j = tempArrayList.size() - 1; j >= 0; j--) {
                    mDiaryEntries.add(tempArrayList.get(j));
                }


                mDiaryAdapter = new DiaryAdapter(getApplicationContext(), MainActivity.this, mDiaryEntries);
                mRecyclerView.setAdapter(mDiaryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //launch activity for seeing and/or updating
        DiaryEntry diaryEntry = (mDiaryEntries == null) ? null : mDiaryEntries.get(clickedItemIndex);
        Intent intent = new Intent(this, SeeOrUpdateDiaryActivity.class);

        intent.putExtra(EXTRA_USER_ACCOUNT_ID, mUserId);
        intent.putExtra(EXTRA_DIARY_ID, Objects.requireNonNull(diaryEntry).getDiaryId());
        intent.putExtra(EXTRA_DIARY_CONTENT, diaryEntry.getContent());
        intent.putExtra(EXTRA_DIARY_MOOD, diaryEntry.getMood());
        intent.putExtra(EXTRA_DIARY_DATE_ENREG, SIMPLE_DATE_FORMAT.format(diaryEntry.getDateEnreg()));

        startActivity(intent);

    }


    private void launchCorrectActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_APP, Activity.MODE_PRIVATE);
        mUserId = sharedPreferences.getString("USER_ACCOUNT_ID", "");

        if (TextUtils.isEmpty(mUserId)) {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtra("SIGN_OUT", true);
            startActivity(intent);
            finish();

        } else {
            mDatabaseReferenceDiaries = FirebaseDatabase.getInstance().getReference(getResources().getText(R.string.firebase_db_diaries).toString()).child(mUserId);
            mDatabaseReferenceDiaries.keepSynced(true);
        }

    }

    private void signOut() {

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_APP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_ACCOUNT_ID", "");
        editor.putString("USER_ACCOUNT_EMAIL", "");
        editor.putString("USER_ACCOUNT_NAME", "");
        editor.apply();

        launchCorrectActivity();


    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_happy:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 0;
                break;
            case R.id.radio_hungry:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 1;
                break;
            case R.id.radio_love:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 2;
                break;
            case R.id.radio_sad:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 3;
                break;
            case R.id.radio_sick:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 4;
                break;
            case R.id.radio_tired:
                if (checked)
                    mRadioButtonMoodCheckedPosition = 5;
                break;
        }
    }

    private void addThouhts() {
        String thought = mEditTextThoughts.getText().toString();
        int mood = mRadioButtonMoodCheckedPosition;

        if (!TextUtils.isEmpty(thought) && mEditTextThoughts.getText().toString().trim().length() > 0) {

            String id = mDatabaseReferenceDiaries.push().getKey();
            Date date = new Date();
            DiaryEntry diaryEntry = new DiaryEntry(id, thought, mood, date, date);

            mDatabaseReferenceDiaries.child(Objects.requireNonNull(id)).setValue(diaryEntry);

            updateUI();
            Toast.makeText(this, getResources().getText(R.string.success_add_message).toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getResources().getText(R.string.error_invalid_entry).toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI() {
        mEditTextThoughts.setText("");
        RadioButton moodRadioButton = (RadioButton) findViewById(R.id.radio_happy);
        moodRadioButton.setChecked(true);

    }

    private void deleteThoughts(int swipedItemIndex) {
        DiaryEntry diaryEntry = mDiaryEntries.get(swipedItemIndex);
        if (diaryEntry != null) {
            mDatabaseReferenceDiaries.child(diaryEntry.getDiaryId()).removeValue();

            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), getResources().getText(R.string.success_delete_message).toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), getResources().getText(R.string.error_updating_entry).toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
