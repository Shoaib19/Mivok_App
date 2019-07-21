package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public MediaPlayer mediaPlayer;

    public WordAdapter(Activity context, ArrayList<Word> arrayList, int colorResourceId) {

        super(context, 0, arrayList);
        mColorResourceId = colorResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Checking if there is a View present for reusing if not inflate one.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        //getting the current position of the word object in the View
        final Word currentWord = getItem(position);

        //getting the text view resource for setting our desired text.
        TextView mivok = listItemView.findViewById(R.id.mivokTranslation);

        mivok.setText(currentWord.getmMiwokTrans());

        TextView defaultTran = listItemView.findViewById(R.id.defaultTranslation);

        defaultTran.setText(currentWord.getmDefaultTrans());

        ImageView imageView = listItemView.findViewById(R.id.image);

         //Checking if the View has an Image resource, if yes then setting the correct image.
        if (currentWord.checkImageResource == 0) {
            imageView.setImageResource(currentWord.getmImageResource());

        } else {
            imageView.setVisibility(View.GONE);
        }
        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

     /*  listItemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mediaPlayer = MediaPlayer.create(getContext(),currentWord.getmAudioResource());
               mediaPlayer.start();
           }
       });*/

        return listItemView;
    }
}
