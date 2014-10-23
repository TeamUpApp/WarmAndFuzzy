package com.example.clazell.smstest;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TextSwitcher heading;
        private Button btnSearch;
        private String[] loveLetters;
        private int amountofLL;


        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_my, container, false);

            Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);


            btnSearch = (Button) rootView.findViewById(R.id.btn_search);
            btnSearch.setVisibility(View.VISIBLE);
            btnSearch.setEnabled(true);
            btnSearch.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    nextSMS();
                }
            });

            heading = (TextSwitcher) rootView.findViewById(R.id.text_heading);
            heading.setFactory(new ViewSwitcher.ViewFactory() {

                public View makeView() {
                    TextView myText = new TextView(getActivity());
                    myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    myText.setTextSize(28);
                    myText.setTextColor(Color.WHITE);
                    return myText;
                }
            });
            // set the animation type of textSwitcher
            heading.setInAnimation(in);
            heading.setOutAnimation(out);
            search();
            nextSMS();
            return rootView;
        }

        private void search() {
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            String wordToSearch = "love";
            Boolean found;

            String[] body = new String[cursor.getCount()];
            String[] number = new String[cursor.getCount()];
            loveLetters = new String[cursor.getCount()];
            amountofLL = 0;

            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    body[i] = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                    found = body[i].contains(wordToSearch);
                    if (found == true) {
                        loveLetters[amountofLL] = body[i];
                        amountofLL++;
                        found = false;
                    }
                    cursor.moveToNext();
                }
            }
            cursor.close();


        }

        private void nextSMS() {
            Random rand = new Random();
            int n = rand.nextInt(amountofLL);
            heading.setText(loveLetters[n] + " ");
        }

    }


}
