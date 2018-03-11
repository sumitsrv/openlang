package org.openlang.levels;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.openlang.R;
import org.openlang.SpeechService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumit on 27/01/18.
 */

public class OrderedAlphabets extends SpeechFragment {

    private Character alpha;
    private int idX = 0;
    private int idY = 0;
    private List<TextView> alphaViews = new ArrayList<>();
    private short currentIndex = 0;
//    public OrderedAlphabets(Context context, Character alpha, int x, int y) {
//        super(context);
//        super.setClickable(false);
//        this.alpha = alpha;
//        idX = x;
//        idY = y;
//    }
//
//    public OrderedAlphabets(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        super.setClickable(false);
//    }
//
//    public OrderedAlphabets(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        super.setClickable(false);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
//                MeasureSpec.getSize(heightMeasureSpec));
//    }

    public int getIdX() {
        return idX;
    }

    public int getIdY() {
        return idY;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level, container, false);
        mText = new TextView(getActivity());
        myGridLayout = (GridLayout) view.findViewById(R.id.canvas);
        myGridLayout.addView(mText);

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            TextView alphaText = new TextView(getActivity());
            alphaText.setVisibility(View.INVISIBLE);
            alphaText.setGravity(Gravity.FILL_HORIZONTAL);
            myGridLayout.addView(alphaText);
//            alphaText.setLayoutParams(layoutParams);
//            alphaText.
            alphaText.setText(Character.toString(alphabet));
            alphaViews.add(alphaText);
        }
        return view;
    }

    public OrderedAlphabets() {
        setSpeechContext();
        setSpeechServiceListener();
    }

    @Override
    protected void setSpeechServiceListener() {
        mSpeechServiceListener = new SpeechService.Listener() {
            @Override
            public void onSpeechRecognized(final String text, final boolean isFinal) {
                if (isFinal) {
                    mVoiceRecorder.dismiss();
                }
                if (!TextUtils.isEmpty(text)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView currentActiveView = alphaViews.get(currentIndex);

                            Log.i("Current Active View", currentActiveView.getText().toString().toLowerCase());

                            if (isFinal) {
                                if(text.trim().toLowerCase().compareTo(currentActiveView.getText().toString().trim().toLowerCase()) == 0) {
                                    currentActiveView.setVisibility(View.VISIBLE);
                                    currentIndex++;
                                }
                                mText.setText(text);
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    protected void setSpeechContext() {
        speechContextStrings = new ArrayList<>();

        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            speechContextStrings.add(Character.toString(alphabet));
        }

        for(char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            speechContextStrings.add(Character.toString(alphabet));
        }
    }

}
