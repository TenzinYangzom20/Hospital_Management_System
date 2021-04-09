package gvs.com.hospital_management_system;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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


public class ChangePassword extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    Button add_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Doctor_Details");
        final EditText fname = ((EditText)view.findViewById(R.id.snpass));
        final EditText fid = ((EditText)view.findViewById(R.id.snrpass));
        add_btn=(Button)view.findViewById(R.id.btn_pass);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String spass=fname.getText().toString();
                final String srpass=fid.getText().toString();
                final String dname=sharedPreferences.getString("name","");
                final String dkey=sharedPreferences.getString("key","");
                final String hosname=sharedPreferences.getString("hosname","");
                final String expi=sharedPreferences.getString("expi","");
                final String cphone=sharedPreferences.getString("cphone","");
                final String speci=sharedPreferences.getString("speci","");
                final String about=sharedPreferences.getString("about","");
                final String username=sharedPreferences.getString("username","");
                final String hname_address=username+"_"+hosname;
                final String hname_pass=username+"_"+spass;
                if(spass.length() >=4 && srpass.length() >=4){

                if(spass.equals(srpass)){
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Delivery Boy_Details");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Doctor_Model fdel=new Doctor_Model(dkey,dname,username,cphone,spass,speci,expi,hosname,about,hname_address,hname_pass);
                            databaseReference.child(dkey).setValue(fdel);
                            Toast.makeText(getContext(), "Password ChangedSucess", Toast.LENGTH_SHORT).show();

                            FragmentManager fm=getFragmentManager();
                            fm.beginTransaction().replace(R.id.doctor_fragment_container,new Dhome()).commit();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }else{

                    Toast.makeText(getContext(), "Password Does't Match", Toast.LENGTH_SHORT).show();



                }}else{
                    Toast.makeText(getContext(), "All Feilds above 4 Characteres", Toast.LENGTH_SHORT).show();

                }
            }
        });



        return view;
    }


}
