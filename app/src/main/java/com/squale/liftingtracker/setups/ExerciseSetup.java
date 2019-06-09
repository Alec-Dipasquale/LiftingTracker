package com.squale.liftingtracker.setups;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squale.liftingtracker.R;
import com.squale.liftingtracker.dao.ExerciseDAO;
import com.squale.liftingtracker.models.Exercise;
import com.squale.liftingtracker.models.Workout;

import java.util.ArrayList;
import java.util.Locale;

import static android.view.Gravity.END;
import static android.view.Gravity.FILL;

public class ExerciseSetup {

    private static String TAG = "ExerciseSetup";

    private LinearLayout mAddedToLinearLayout;
    private LinearLayout mAddExerciseLinearLayout;
    private LinearLayout mTitleBarLinearLayout;
    private EditText mEtExerciseName;
    private LinearLayout mSetButtonLinearLayout;
    private ImageButton mBtnAddSet;
    private LinearLayout mSetsListLinearLayout;
    private TextView mTvExerciseCount;
    private Context mContext;
    private ArrayList<SetSetup> mSetSetupArrayList = new ArrayList<>();



    public ExerciseSetup(int count, LinearLayout addedToLinearLayout, Context context){
        this.mContext = context;
        this.mAddExerciseLinearLayout = new LinearLayout(context);
        this.mTitleBarLinearLayout = new LinearLayout(context);
        this.mSetButtonLinearLayout = new LinearLayout(context);
        this.mSetsListLinearLayout = new LinearLayout(context);
        this.mEtExerciseName = new EditText(context);
        this.mTvExerciseCount = new TextView(context);
        this.mBtnAddSet = new ImageButton(context);
        this.mAddedToLinearLayout = addedToLinearLayout;


        mAddExerciseLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mAddExerciseLinearLayout.setGravity(View.FOCUS_LEFT);
        mAddExerciseLinearLayout.setPadding(5, 25, 5, 25);
        mAddExerciseLinearLayout.setId(View.generateViewId());


        mTitleBarLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTitleBarLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mTitleBarLinearLayout.setGravity(FILL);
        mTitleBarLinearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        mTitleBarLinearLayout.setId(View.generateViewId());


        mSetButtonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mSetButtonLinearLayout.setGravity(END);
        mSetButtonLinearLayout.setBackgroundColor(Color.WHITE);
        mSetButtonLinearLayout.setId(View.generateViewId());


        mSetsListLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mSetsListLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mSetsListLinearLayout.setGravity(FILL);
        mSetsListLinearLayout.setBackgroundColor(Color.WHITE);
        mSetsListLinearLayout.setId(View.generateViewId());


        mEtExerciseName.setHint("Exercise Name");
        mEtExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mEtExerciseName.setTextColor(Color.BLACK);
        mEtExerciseName.setPadding(10, 5, 5, 5);
        mEtExerciseName.setId(View.generateViewId());


        mTvExerciseCount.setText(String.format(Locale.US, "%d", count));
        mTvExerciseCount.setTextColor(Color.BLACK);
        mTvExerciseCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTvExerciseCount.setPadding(5, 5, 10, 5);
        mTvExerciseCount.setId(View.generateViewId());


        mBtnAddSet.setImageResource(R.drawable.plus);
        mBtnAddSet.setMaxHeight(30);
        mBtnAddSet.setLayoutParams(new LinearLayout.LayoutParams(400, 200));
        mBtnAddSet.setId(View.generateViewId());

        SetSetup set = new SetSetup(mSetSetupArrayList.size() + 1, mSetsListLinearLayout, context);
        mSetSetupArrayList.add(set);

        mAddExerciseLinearLayout.addView(mTitleBarLinearLayout);
        mAddExerciseLinearLayout.addView(mSetsListLinearLayout);
        mSetButtonLinearLayout.addView(mBtnAddSet);
        mAddExerciseLinearLayout.addView(mSetButtonLinearLayout);

        mTitleBarLinearLayout.addView(mTvExerciseCount);
        mTitleBarLinearLayout.addView(mEtExerciseName);

        mBtnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSetup setSetup = new SetSetup(
                        mSetSetupArrayList.size() + 1, mSetsListLinearLayout, mContext);
                mSetSetupArrayList.add(setSetup);

            }
        });


        mAddedToLinearLayout.addView(mAddExerciseLinearLayout);
    }

    public void sendCurrentToDatabase(Workout workout) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(this.mContext);
        if (!TextUtils.isEmpty(this.getName())) {
            Exercise createExercise = exerciseDAO.createExercise(
                    this.getName(), workout.getId());

            for (int i = 0; i < mSetSetupArrayList.size(); i++) {
                SetSetup setSetup = mSetSetupArrayList.get(i);
                setSetup.sendCurrentToDatabase(createExercise);
            }
        }
    }

    public void delete() {
        this.mAddExerciseLinearLayout.removeView(mAddedToLinearLayout);
    }

    public void makeNonEditable() {
        for (int i = mSetSetupArrayList.size() - 1; i >= 0; i--) {
            SetSetup setSetup = mSetSetupArrayList.get(i);
            setSetup.makeNonEditable();
            if (setSetup.isRepsEmpty()) {
                mSetSetupArrayList.remove(setSetup);
            }
        }
        if (mSetSetupArrayList.isEmpty() && TextUtils.isEmpty(mEtExerciseName.getText().toString())) {
            mAddedToLinearLayout.removeView(mAddExerciseLinearLayout);
        }
        mEtExerciseName.setEnabled(false);
        mSetButtonLinearLayout.removeView(mBtnAddSet);
    }

    public void makeEditable() {
        for (SetSetup setSetup : mSetSetupArrayList) {
            setSetup.makeEditable();
        }
        mEtExerciseName.setEnabled(true);
        mSetButtonLinearLayout.addView(mBtnAddSet);
    }

    public int getCount(){
        return Integer.parseInt(mTvExerciseCount.getText().toString());
    }
    public void setCount(int x){
        mTvExerciseCount.setText(String.format(Locale.US, "%d", x));
    }

    public String getName() {
        return this.mEtExerciseName.getText().toString();
    }
    public void setName(String exerciseName) {
        this.mEtExerciseName.setText(exerciseName);
    }
}
