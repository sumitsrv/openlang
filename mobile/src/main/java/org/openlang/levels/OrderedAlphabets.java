package org.openlang.levels;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.openlang.R;
import org.openlang.SpeechService;

/**
 * Created by sumit on 27/01/18.
 */

public class OrderedAlphabets extends SpeechFragment {

    private Character alpha;
    private int idX = 0;
    private int idY = 0;

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
        mText.setText("b");
//        voiceText = new TextView(getActivity());
        myGridLayout = (GridLayout) view.findViewById(R.id.canvas);
        myGridLayout.addView(mText);
//        myGridLayout.addView(voiceText);
        return view;
    }

    public OrderedAlphabets() {
        mSpeechServiceListener = new SpeechService.Listener() {
            @Override
            public void onSpeechRecognized(final String text, final boolean isFinal) {
                if (isFinal) {
                    mVoiceRecorder.dismiss();
                }
                if (mText != null && !TextUtils.isEmpty(text)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isFinal) {
                                StringBuilder buffer = new StringBuilder();
//                                buffer.append(mText.getText().toString());
                                buffer.append(text);
                                mText.setText(buffer.toString());
//                                Context context = getActivity().getApplicationContext();
//                                int duration = Toast.LENGTH_LONG;
//                                Toast toast = Toast.makeText(context, text, duration);
//                                toast.show();
                            } else {
                                if (mText.getText().toString().trim().toLowerCase().compareTo(text.trim().toLowerCase()) == 0) {
                                    Context context = getActivity().getApplicationContext();
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                                StringBuilder buffer = new StringBuilder();
//                                buffer.append(mText.getText().toString());
                                buffer.append(text);
                                mText.setText(buffer.toString());
                            }
                        }
                    });
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
