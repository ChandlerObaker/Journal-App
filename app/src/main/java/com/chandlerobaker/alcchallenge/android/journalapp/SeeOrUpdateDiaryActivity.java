package com.chandlerobaker.alcchallenge.android.journalapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chandlerobaker.alcchallenge.android.journalapp.database.DiaryEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.EXTRA_DIARY_CONTENT;
import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.EXTRA_DIARY_DATE_ENREG;
import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.EXTRA_DIARY_ID;
import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.EXTRA_DIARY_MOOD;
import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.EXTRA_USER_ACCOUNT_ID;

public class SeeOrUpdateDiaryActivity extends AppCompatActivity {

    private LinearLayout mLayoutEditMode;
    private TextView mTextViewThoughts;
    private EditText mEdittextThoughts;
    private int mRadioButtonMoodCheckedPosition;
    private Toolbar mToolbar;
    private String mUserId;
    private final SimpleDateFormat sDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DiaryEntry mSampleDiaryEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_or_update_diary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // back press button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mLayoutEditMode = (LinearLayout) findViewById(R.id.ly_edit_mode);
        mTextViewThoughts = (TextView) findViewById(R.id.textview_thought_note);
        mEdittextThoughts = (EditText) findViewById(R.id.edittext_thought_update);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mSampleDiaryEntry = new DiaryEntry();
            mSampleDiaryEntry.setDiaryId(extras.getString(EXTRA_DIARY_ID));
            mSampleDiaryEntry.setContent(extras.getString(EXTRA_DIARY_CONTENT));
            mSampleDiaryEntry.setMood(extras.getInt(EXTRA_DIARY_MOOD));
            try {
                mSampleDiaryEntry.setDateEnreg(sDf.parse(extras.getString(EXTRA_DIARY_DATE_ENREG)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mUserId = extras.getString(EXTRA_USER_ACCOUNT_ID);
        }


        configureView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                this.finish();
            case R.id.action_update :
                updateMenu(item.getItemId());
                return true;
            case R.id.action_cancel:
                updateMenu(item.getItemId());
                return true;
            case R.id.action_save:
                updateMenu(item.getItemId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void configureView() {
        mTextViewThoughts.setText(mSampleDiaryEntry.getContent());
        mEdittextThoughts.setText(mSampleDiaryEntry.getContent());
        RadioButton mRadioButtonMood;

        switch (mSampleDiaryEntry.getMood()) {
            case 0:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_happy);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 0;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_happy_diary_update));
                break;
            case 1:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_hungry);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 1;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_hungry_diary_update));
                break;
            case 2:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_love);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 2;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_love_diary_update));
                break;
            case 3:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_sad);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 3;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_sad_diary_update));
                break;
            case 4:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_sick);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 4;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_sick_diary_update));
                break;
            case 5:
                mRadioButtonMood = (RadioButton) findViewById(R.id.radio_tired);
                mRadioButtonMood.setChecked(true);
                mRadioButtonMoodCheckedPosition = 5;
                mTextViewThoughts.setBackground(getDrawable(R.drawable.rounded_tired_diary_update));
                break;
        }

    }

    private void updateDiary(String userId, DiaryEntry sampleEntry)
    {
        sampleEntry.setContent(mEdittextThoughts.getText().toString());
        sampleEntry.setMood(mRadioButtonMoodCheckedPosition);

        if(!TextUtils.isEmpty(sampleEntry.getContent())) {
            if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sampleEntry.getDiaryId())) {
             DatabaseReference   mDatabaseDiaries = FirebaseDatabase.getInstance().getReference(getResources().getText(R.string.firebase_db_diaries).toString()).child(userId).child(sampleEntry.getDiaryId());
             mDatabaseDiaries.keepSynced(true);

                sampleEntry.setDateLastModification(new Date());
                mDatabaseDiaries.setValue(sampleEntry);
                configureView();

                Snackbar.make(mEdittextThoughts,  getResources().getText(R.string.success_update_message).toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        else
            Toast.makeText(this, getResources().getText(R.string.error_updating_entry).toString(), Toast.LENGTH_LONG).show();
    }

    private void updateMenu(int itemId)
    {
        Menu menu = mToolbar.getMenu();
        switch (itemId) {
            case R.id.action_update:

                mLayoutEditMode.setVisibility(View.VISIBLE);
                mTextViewThoughts.setVisibility(View.INVISIBLE);
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(true);
                break;
            case R.id.action_cancel:
                mLayoutEditMode.setVisibility(View.INVISIBLE);
                mTextViewThoughts.setVisibility(View.VISIBLE);
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                break;
            case R.id.action_save:
                mLayoutEditMode.setVisibility(View.INVISIBLE);
                mTextViewThoughts.setVisibility(View.VISIBLE);
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                updateDiary(mUserId, mSampleDiaryEntry);
                break;
        }

    }

}
