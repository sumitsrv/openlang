package org.openlang.levels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import org.openlang.R;
import org.openlang.SpeechService;

import java.util.ArrayList;
import java.util.List;

public class SpellingWords extends SpeechFragment {
    private Character alpha;
    private int idX = 0;
    private int idY = 0;
    private List<TextView> alphaViews = new ArrayList<>();
    private short currentIndex = 0;

    public SpellingWords(){
        setSpeechContext();
        setSpeechServiceListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level, container, false);
        mText = new TextView(getActivity());
        myGridLayout = (GridLayout) view.findViewById(R.id.canvas);
        myGridLayout.addView(mText);


        return view;
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
}
