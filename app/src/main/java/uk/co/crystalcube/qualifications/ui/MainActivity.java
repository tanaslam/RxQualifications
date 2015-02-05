package uk.co.crystalcube.qualifications.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

import java.util.Objects;

import uk.co.crystalcube.qualifications.R;
import uk.co.crystalcube.qualifications.model.Qualification;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity
            implements AbstractListFragment.OnListItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, QualificationsFragment_.builder().build())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Object qualification) {

        Toast.makeText(this, "Item clicked", Toast.LENGTH_LONG).show();

        String id = ((Qualification) qualification).getId();
        SubjectsActivity_.intent(this).qualificationId(id).start();
    }

}
