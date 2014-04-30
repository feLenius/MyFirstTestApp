package feLenius.myfirsttestapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public final static String MESSAGE = "feLenius.myfirsttestapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Restarted", Toast.LENGTH_SHORT).show();
        // Activity being restarted from stopped state
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        Toast.makeText(getApplicationContext(), "Destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        //savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        //Caution: Your activity will be destroyed and recreated each time the user rotates
        // the screen. When the screen changes orientation, the system destroys and recreates
        // the foreground activity because the screen configuration has changed and your activity
        // might need to load alternative resources (such as the layout).
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        //mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        //mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);

        //Instead of restoring the state during onCreate() you may choose to implement
        // onRestoreInstanceState(), which the system calls after the onStart() method.
        // The system calls onRestoreInstanceState() only if there is a saved state to
        // restore, so you do not need to check whether the Bundle is null:
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "opa opa aaa", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showText(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        Intent intent = new Intent(this, TextOnlyActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(MESSAGE, message);
        startActivity(intent);
    }

}
