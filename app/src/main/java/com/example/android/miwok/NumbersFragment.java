package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer;
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
                // i.e. for a phone call
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback, because you lost the Audio Focus.
                // i.e. the user started some other playback app
                // Remember to unregister your controls/buttons here.
                // And release the kra — Audio Focus!
                // You’re done.
                audioManager.abandonAudioFocus(mFocusChangeListener);
            } else if (focusChange ==
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, because something else is also
                // playing audio over you.
                // i.e. for notifications or navigation directions
                // Depending on your audio playback, you may prefer to
                // pause playback here instead. You do you.
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 7, 0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback, because you hold the Audio Focus
                // again!
                // i.e. the phone call ended or the nav directions
                // are finished
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

    public NumbersFragment() {
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

        words.add(new Word("lutti", "One", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("otiiko", "Two", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("tolookosu", "Three", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("oyyisa", "Four", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("massokka", "Five", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("temmokka", "Six", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("kenekaku", "Seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("kawinta", "Eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("wo’e", "Nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("na’aacha", "Ten", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter Adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word position1 = words.get(position);


                int result = audioManager.requestAudioFocus(mFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_GAIN) {

                    mediaPlayer = MediaPlayer.create(getActivity(), position1.getmAudioResource());
                    mediaPlayer.start();


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

