package com.example.nemus.osakaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Info_viewer extends AppCompatActivity {

    private TextView mTitleText;
    private TextView mInfoText;
    private ImageView mInfoImage;
    private ListView mReplylist;

    //temp
    private OsakaData od = new OsakaData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viewer);

        Intent intent = getIntent();

        mTitleText = (TextView) findViewById(R.id.infotitle);
        mInfoImage = (ImageView) findViewById(R.id.infoimage);
        mInfoText = (TextView) findViewById(R.id.infotext);
        mReplylist = (ListView) findViewById(R.id.replylist);

        int id = Integer.parseInt(intent.getStringExtra("id"));

        Amuse am = od.items.get(id-1);

        mTitleText.setText(am.name);
    }
}
