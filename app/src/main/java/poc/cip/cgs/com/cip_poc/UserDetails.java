package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class UserDetails extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        Button sync = (Button) findViewById(R.id.sync);
        Button gotowork = (Button) findViewById(R.id.work);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView emp_id = (TextView) findViewById(R.id.emp_id_value);
        TextView branch_value = (TextView) findViewById(R.id.branch_value);
        TextView lob_value = (TextView) findViewById(R.id.lob_value);
        TextView role_value = (TextView) findViewById(R.id.role_value);
        String emp = LoginActivity.dto.getEmpid();
        String branch = LoginActivity.dto.getBranchname();
        String lob = LoginActivity.dto.getLobname();
        String role = LoginActivity.dto.getRolename();
        emp_id.setText(emp);
        branch_value.setText(branch);
        lob_value.setText(lob);
        role_value.setText(role);



        Picasso.with(this)
                .load("http://www.american.edu/uploads/profiles/large/James%20Thurber%20Profile%20Photo.jpg")
                .into(imageView);

        sync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);


            }
        });


        gotowork.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), ItemFragment.class);

                startActivity(intent);


            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
