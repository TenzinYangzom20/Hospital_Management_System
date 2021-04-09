package gvs.com.hospital_management_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class UserLog extends AppCompatActivity {
TextView textView;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        textView=(TextView)findViewById(R.id.creg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserLog.this,UserRegister.class);
                startActivity(intent);

            }
        });


    }
    public void cpLog(View view){
        final String email=((EditText)findViewById(R.id.cusername)).getText().toString();
        final String password=((EditText)findViewById(R.id.cpassword)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(UserLog.this, "All Fields Should be More than 3 Characters", Toast.LENGTH_SHORT).show();

        } else {
            final String email_pass=email+"_"+password;
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("User_Details");
            ref.orderByChild("username_password").equalTo(email_pass).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            String name = childSnapshot.child("name").getValue().toString();
                            String email = childSnapshot.child("email").getValue().toString();
                            String username = childSnapshot.child("username").getValue().toString();
                            String phone = childSnapshot.child("phone").getValue().toString();

                            Log.d("name is", key);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("cname",name);
                            editor.putString("cemail",email);
                            editor.putString("cusername",username);
                            editor.putString("ckey",key);
                            editor.putString("cphone",phone);

                            editor.commit();
                            Log.d("keyis", key);
                            Intent intent = new Intent(UserLog.this, UserHome.class);
                            Toast.makeText(UserLog.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }


                    }else{
                        Toast.makeText(UserLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }


}
