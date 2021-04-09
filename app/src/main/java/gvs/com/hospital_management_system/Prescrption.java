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


public class Prescrption extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    String bdatee;
    String ftime;
    String ttime;
    String apdate;
    String id;String count;String docname;String docstatus;String tdel;String u_d_b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_prescrption, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Booking_Details");

        Bundle extras =this.getArguments();
        bdatee= extras.getString("bookdate");
        apdate= extras.getString("ap_date");
        count= extras.getString("count");
        id= extras.getString("id");
        docname= extras.getString("docname");
        docstatus= extras.getString("docstatus");
        ftime= extras.getString("ftime");
        ttime= extras.getString("ttime");
        tdel= extras.getString("tdel");
        u_d_b= extras.getString("u_d_b");

        final EditText prscr = ((EditText)view.findViewById(R.id.prescr));
       Button button=(Button)view.findViewById(R.id.p_btn);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final String prescr=prscr.getText().toString();
               final String username=sharedPreferences.getString("username","");

               if(prescr.length() >=4) {
                   databaseReference = FirebaseDatabase.getInstance().getReference("Prescrption_Details");
                   databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           String id = databaseReference.push().getKey();
                           Prescrption_Model fdel=new Prescrption_Model(id,prescr,username,docname,tdel);
                           databaseReference.child(id).setValue(fdel);
                           Toast.makeText(getContext(), "Prescrtion Sended Sucess", Toast.LENGTH_SHORT).show();

                           FragmentManager fm=getFragmentManager();
                           fm.beginTransaction().replace(R.id.doctor_fragment_container,new Dhome()).commit();

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
               }else{
                   Toast.makeText(getContext(), "Please fill all colums above Four Characters", Toast.LENGTH_SHORT).show();

               }

           }
       });
        return view;
    }


}
