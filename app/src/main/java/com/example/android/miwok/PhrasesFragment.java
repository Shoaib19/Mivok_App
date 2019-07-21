package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {


    private String TAG = "kuch bhi";
    MediaPlayer mediaPlayer;
    AudioManager audioManager;


    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    private AudioManager.OnAudioFocusChangeListener mFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback because your Audio Focus was
                // temporarily stolen, but will be back soon.
                Log.d(TAG, "Loss_Transient: AudioFocus");
                // i.e. for a phone call
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback, because you lost the Audio Focus.
                // i.e. the user started some other playback app
                Log.d(TAG, "Loss_: AudioFocus");
                // Remember to unregister your controls/buttons here.
                // And release the kra — Audio Focus!
                // You’re done.
                audioManager.abandonAudioFocus(mFocusChangeListener);
            } else if (focusChange ==
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, because something else is also
                // playing audio over you.
                Log.d(TAG, "Loss_Transient_CAN_DUCK: AudioFocus");
                // i.e. for notifications or navigation directions
                // Depending on your audio playback, you may prefer to
                // pause playback here instead. You do you.
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 7, 0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback, because you hold the Audio Focus
                // again!
                // i.e. the phone call ended or the nav directions
                // are finished
                Log.d(TAG, "GAIN: AudioFocus");
                // If you implement ducking and lower the volume, be
                // sure to return it to normal here, as well.
                mediaPlayer.start();
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        //declaring an array for adding words to our Numbers Category
        final ArrayList<Word> words = new ArrayList<>();
        //creating a word object and assigning the value
        //Word m = new Word("lutti", "One");
        //words.add(m);
        //OR We can do the following for each case:


        words.add(new Word("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new Word("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        words.add(new Word("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit", "I’m feeling good.", R.raw.phrase_im_feeling_good));
        words.add(new Word("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new Word("hәә’ әәnәm", "Yes, I’m coming.", R.raw.phrase_yes_im_coming));
        words.add(new Word("әәnәm", "I’m coming.", R.raw.phrase_im_coming));
        words.add(new Word("yoowutis", "Let’s go.", R.raw.phrase_lets_go));
        words.add(new Word("әnni'nem", "Come here.", R.raw.phrase_come_here));

        WordAdapter Adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

        final ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(Adapter);
        Log.d(TAG, "phrases CHECKING LOGS WORK");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word position1 = words.get(position);


                int result = audioManager.requestAudioFocus(mFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), position1.getmAudioResource());
                    mediaPlayer.start();
                    Log.d(TAG, "CHECKING IF");
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }


        });

        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.stop();
            mediaPlayer.release();
            Log.d(TAG, "PHRASES releaseMediaPlayer ke andar");

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mFocusChangeListener);
        }
    }
}
