package gvs.com.hospital_management_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void adminlog(View view){
        Intent intent=new Intent(MainActivity.this,Adminlog.class);
        startActivity(intent);

    }
    public void userlog(View view){
        Intent intent=new Intent(MainActivity.this,UserLog.class);
        startActivity(intent);

    }
    public void doctorlog(View view){
        Intent intent=new Intent(MainActivity.this,DoctorLog.class);
        startActivity(intent);

    }
    public void emergrncy(View view){
        Intent intent=new Intent(MainActivity.this,Emergency.class);
        startActivity(intent);

    }

}
