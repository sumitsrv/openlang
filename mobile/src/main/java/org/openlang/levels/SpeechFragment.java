package org.openlang.levels;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;

import org.openlang.R;
import org.openlang.SpeechService;
import org.openlang.VoiceRecorder;

/**
 * Created by sumit on 30/01/18.
 */

public class SpeechFragment extends Fragment {
    protected SpeechService mSpeechService;
    protected SpeechService.Listener mSpeechServiceListener;
    protected VoiceRecorder mVoiceRecorder;

    // Resource caches
    protected int mColorHearing;
    protected int mColorNotHearing;

    // View references
    protected TextView mText;
    protected TextView voiceText;
    protected GridLayout myGridLayout;

    private TextView mStatus;

    public SpeechFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSpeechService.addListener(mSpeechServiceListener);
        startVoiceRecorder();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SpeechServiceAccessor speechServiceAccessor = (SpeechServiceAccessor) getActivity();
        this.mSpeechService = speechServiceAccessor.getSpeechService();
        this.mStatus = speechServiceAccessor.getStatusView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        stopVoiceRecorder();

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        mSpeechService = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {
        private static final String TAG = "VoiceRecorder";

        @Override
        public void onVoiceStart() {
            Log.i(TAG, "Voice Start");
            showStatus(true);
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            Log.i(TAG, "Voice End");
            showStatus(false);
            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }

    };

    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    private void showStatus(final boolean hearingVoice) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatus.setTextColor(hearingVoice ? mColorHearing : mColorNotHearing);
            }
        });
    }

    public interface SpeechServiceAccessor {
        SpeechService getSpeechService();
        TextView getStatusView();
    }
}
