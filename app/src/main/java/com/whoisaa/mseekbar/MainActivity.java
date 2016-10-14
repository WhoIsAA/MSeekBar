package com.whoisaa.mseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView mTextView1, mTextView2;
    private MSeekBar mSeekBar1, mSeekBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = (TextView) findViewById(R.id.tv_main1);
        mTextView2 = (TextView) findViewById(R.id.tv_main2);
        mSeekBar1 = (MSeekBar) findViewById(R.id.sb_main1);
        mSeekBar2 = (MSeekBar) findViewById(R.id.sb_main2);
        mSeekBar1.setOnSeekBarChangeListener(this);
        mSeekBar2.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(mSeekBar1)) {
            mTextView1.setText("滑块一当前值为" + progress);
        } else if(seekBar.equals(mSeekBar2)) {
            mTextView2.setText("滑块二当前值为" + progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
