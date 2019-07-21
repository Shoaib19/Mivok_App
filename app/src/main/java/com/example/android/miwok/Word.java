package com.example.android.miwok;


public class Word {

    private String mDefaultTrans;
    private String mMiwokTrans;
    private int mImageResource;
    public int checkImageResource = 0;
    private int mAudioResource;

    public Word(String miwokTranslation, String defaultTranslation, int imageResource, int audioResource) {
        mMiwokTrans = miwokTranslation ;
         mDefaultTrans = defaultTranslation;
         mImageResource = imageResource;
         mAudioResource = audioResource;
         checkImageResource = 0;
    }
    public Word(String miwokTranslation, String defaultTranslation, int audioResource) {
        mMiwokTrans = miwokTranslation ;
        mDefaultTrans = defaultTranslation;
        mAudioResource = audioResource;
        checkImageResource = 1;
    }


    public String getmMiwokTrans() {
        return mMiwokTrans;
    }

    public String getmDefaultTrans() {
        return mDefaultTrans;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getmAudioResource(){ return mAudioResource; }

}


