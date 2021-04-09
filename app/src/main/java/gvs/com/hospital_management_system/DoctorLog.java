package gvs.com.hospital_management_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorLog extends AppCompatActivity {
    TextView textView;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_log);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void cpLog(View view){
        final String email=((EditText)findViewById(R.id.cusername1)).getText().toString();
        final String password=((EditText)findViewById(R.id.cpassword1)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(DoctorLog.this, "All Fields Should be More than 3 Characters", Toast.LENGTH_SHORT).show();

        } else {
            final String email_pass=email+"_"+password;
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Doctor_Details");
            ref.orderByChild("username_password").equalTo(email_pass).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            String name = childSnapshot.child("name").getValue().toString();
                            String username = childSnapshot.child("username").getValue().toString();
                            String hosname = childSnapshot.child("hosname").getValue().toString();
                            String phone = childSnapshot.child("phone").getValue().toString();
                            String expi = childSnapshot.child("expi").getValue().toString();
                            String speci = childSnapshot.child("speci").getValue().toString();
                            String about = childSnapshot.child("about").getValue().toString();


                            Log.d("name is", key);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",name);
                            editor.putString("username",username);
                            editor.putString("hosname",hosname);
                            editor.putString("expi",expi);
                            editor.putString("cphone",phone);
                            editor.putString("speci",speci);
                            editor.putString("about",about);
                            editor.putString("key",key);


                            editor.commit();
                            Log.d("keyis", key);
                            Intent intent = new Intent(DoctorLog.this, DoctorHome.class);
                            Toast.makeText(DoctorLog.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }


                    }else{
                        Toast.makeText(DoctorLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
