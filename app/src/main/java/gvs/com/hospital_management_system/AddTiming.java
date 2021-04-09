package gvs.com.hospital_management_system;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AddTiming extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;


    Button add_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_timing, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        databaseReference= FirebaseDatabase.getInstance().getReference("Specialization_Details");
        final EditText datee = ((EditText)view.findViewById(R.id.datee));
        final EditText fromtime = ((EditText)view.findViewById(R.id.fromtime));
        final EditText tootime = ((EditText)view.findViewById(R.id.tootime));
        final String username=sharedPreferences.getString("username","");

        add_btn=(Button)view.findViewById(R.id.time_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String datee1=datee.getText().toString();
                final String fromtime1=fromtime.getText().toString();
                final String tootime1=tootime.getText().toString();

                if(datee.length() >=3 && fromtime1.length() >=3 && tootime1.length() >=3) {
                    databaseReference2 = FirebaseDatabase.getInstance().getReference("Doctor_Available_Details");
                    databaseReference2.orderByChild("docname").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getContext(), "Details Already Exist Please Update Your Details", Toast.LENGTH_SHORT).show();

                            }else{
                               String id = databaseReference2.push().getKey();
                                Available_Model fdel=new Available_Model(id,datee1,fromtime1,tootime1,username);
                                databaseReference2.child(id).setValue(fdel);
                                Toast.makeText(getContext(), "Time Added Sucess", Toast.LENGTH_SHORT).show();

                                FragmentManager fm=getFragmentManager();
                                fm.beginTransaction().replace(R.id.doctor_fragment_container,new ViewAvailableTime()).commit();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(getContext(), "All Feilds Should be above 3 characters", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return view;
    }


}
