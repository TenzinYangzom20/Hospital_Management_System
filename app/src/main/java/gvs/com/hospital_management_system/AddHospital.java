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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddHospital extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    Button add_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_hospital, container, false);
        final EditText hos = ((EditText)view.findViewById(R.id.aname));
        final EditText address = ((EditText)view.findViewById(R.id.address));
        final EditText type = ((EditText)view.findViewById(R.id.atype));
        final EditText lati = ((EditText)view.findViewById(R.id.lati));
        final EditText lang = ((EditText)view.findViewById(R.id.lang));
        add_btn=(Button)view.findViewById(R.id.hos_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String hos1=hos.getText().toString();
                final String address1=address.getText().toString();
                final String type1=type.getText().toString();
                final String lati1=lati.getText().toString();
                final String lang1=lang.getText().toString();
                final String hname_address=hos1+"_"+address1;
                if(hos1.length() >=4 && address1.length() >=4 && type1.length() >=4 && lati1.length() >=4 && lang1.length() >=4){
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Hospital_Details");
                    databaseReference.orderByChild("hname_address").equalTo(hname_address).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getContext(), "Hospital Details are already exists", Toast.LENGTH_SHORT).show();

                            }else{
                                String id = databaseReference.push().getKey();
                                Hospital_Model fdel=new Hospital_Model(id,hos1,type1,address1,lati1,lang1,hname_address);
                                databaseReference.child(id).setValue(fdel);
                                Toast.makeText(getContext(), "Hospital Added Sucess", Toast.LENGTH_SHORT).show();

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
