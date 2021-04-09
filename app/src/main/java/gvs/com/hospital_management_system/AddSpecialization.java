package gvs.com.hospital_management_system;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddSpecialization extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    Button add_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_specialization, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Specialization_Details");

        final EditText spece = ((EditText)view.findViewById(R.id.spece));
        add_btn=(Button)view.findViewById(R.id.spc_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String spece1=spece.getText().toString();




                if(spece1.length()>=4){
                    final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Specialization_Details");
                    ref.orderByChild("specialization").equalTo(spece1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                Toast.makeText(getContext(), "Specialization already exists", Toast.LENGTH_SHORT).show();

                            }else{

                                String id = ref.push().getKey();
                                Specialization_Model fdel=new Specialization_Model(spece1,id);
                                ref.child(id).setValue(fdel);
                                Toast.makeText(getContext(), "Specialization Added Sucess", Toast.LENGTH_SHORT).show();


                                FragmentManager fm=getFragmentManager();
                                fm.beginTransaction().replace(R.id.admin_fragment_container,new ahome()).commit();


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }else{
                    Toast.makeText(getContext(), "All Feilds Should be above 4 characters", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }


}
