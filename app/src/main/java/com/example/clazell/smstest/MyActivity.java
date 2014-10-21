package com.example.clazell.smstest;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        private TextView heading;
        private Button btnSearch;
        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_my, container, false);

            btnSearch = (Button) rootView.findViewById(R.id.btn_search);
            btnSearch.setVisibility(View.VISIBLE);
            btnSearch.setEnabled(true);
            btnSearch.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

                    String wordToSearch = "love";
                    Boolean found;

                    String[] body = new String[cursor.getCount()];
                    String[] number = new String[cursor.getCount()];
                    String[] loveLetters = new String[cursor.getCount()];
                    int amountofLL = 0;

                    if(cursor.moveToFirst()){
                        for(int i=0;i<cursor.getCount();i++) {
                            body[i] = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                            found = body[i].contains(wordToSearch);
                            if(found == true){
                                loveLetters[amountofLL] = body[i];
                                amountofLL++;
                                found = false;
                            }
                            cursor.moveToNext();
                        }
                    }
                    cursor.close();

                    Random rand = new Random();
                    int  n = rand.nextInt(amountofLL);
                    heading = (TextView) rootView.findViewById(R.id.text_heading);
                    heading.setText(loveLetters[n] + " "+amountofLL);

                }
            });



                        return rootView;
        }
    }
}
