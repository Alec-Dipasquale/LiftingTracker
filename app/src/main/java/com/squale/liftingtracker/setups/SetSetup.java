package com.squale.liftingtracker.setups;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import static android.view.Gravity.FILL_HORIZONTAL;

public class SetSetup {

    public static final String TAG = "SetSetup";
    private LinearLayout mSetAddedToLinearLayout;
    private LinearLayout mAddSetLinearLayout;
    private EditText mEtReps;
    private EditText mEtWeight;
    private int mCount = 0;

    public SetSetup( int count, LinearLayout addSetLinearLayout, Context context) {
        this.mAddSetLinearLayout = addSetLinearLayout;
        this.mCount = count;
        mSetAddedToLinearLayout = new LinearLayout(context);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        mSetAddedToLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mSetAddedToLinearLayout.setGravity(FILL_HORIZONTAL);
        mSetAddedToLinearLayout.setLayoutParams(layoutParams);

        TextView mTvSetsCount = new TextView(context); // this refers to the activity or the context.
        this.mEtReps = new EditText(context);
        this.mEtWeight = new EditText(context);

        // set attributes as need
        mEtWeight.setLayoutParams(layoutParams);
        mEtWeight.setHint("Weight");
        mEtWeight.setPadding(50, 20, 50, 20);
        mEtWeight.setInputType(InputType.TYPE_CLASS_NUMBER);

        mEtReps.setLayoutParams(layoutParams);
        mEtReps.setHint("Reps");
        mEtReps.setPadding(50, 20, 50, 20);
        mEtReps.setInputType(InputType.TYPE_CLASS_NUMBER);

        mTvSetsCount.setText(String.format(Locale.US, "%d", mCount));
        mTvSetsCount.setTextColor(Color.BLACK);
        mTvSetsCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTvSetsCount.setPadding(5, 0, 20, 0);

        mSetAddedToLinearLayout.addView(mTvSetsCount);
        mSetAddedToLinearLayout.addView(mEtWeight);
        mSetAddedToLinearLayout.addView(mEtReps);

        this.mAddSetLinearLayout.addView(mSetAddedToLinearLayout);
    }

    public void makeNonEditable() {
        if (TextUtils.isEmpty(mEtReps.getText().toString()) || TextUtils.isEmpty(mEtWeight.getText().toString())) {
            delete();
        }
        mEtWeight.setEnabled(false);
        mEtReps.setEnabled(false);
    }

    public void makeEditable() {
        mEtWeight.setEnabled(true);
        mEtReps.setEnabled(true);
    }

    public boolean isRepsEmpty() {
        return TextUtils.isEmpty(mEtReps.getText().toString());
    }

    public void delete() {
        this.mAddSetLinearLayout.removeView(mSetAddedToLinearLayout);
    }

    public int getReps() {
        return Integer.parseInt(mEtReps.getText().toString());
    }

    public void setReps(int x) {
        this.mEtReps.setText(String.format(Locale.US, "%d" , x));
    }

    public int getWeight() {
        return Integer.parseInt(mEtWeight.getText().toString());
    }

    public void setWeight(int x) {
        this.mEtWeight.setText(String.format(Locale.US, "%d", x));
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }
}
