package gvs.com.hospital_management_system;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateTime extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    String datee;
    String ftime;
    String ttime;
    String mny;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_time, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Doctor_Available_Details");

        Bundle extras =this.getArguments();
        datee= extras.getString("datee");
        ftime= extras.getString("ftime");
        ttime= extras.getString("ttime");
        id= extras.getString("id");
        final EditText dd = ((EditText)view.findViewById(R.id.udatee));
        final EditText ft = ((EditText)view.findViewById(R.id.ufromtime));
        final EditText tt = ((EditText)view.findViewById(R.id.utootime));


        dd.setText(datee);
        tt.setText(ttime);
        ft.setText(ftime);
        Button button=(Button)view.findViewById(R.id.utime_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dd1=dd.getText().toString();
                final String ft1=ft.getText().toString();
                final String tt1=tt.getText().toString();

                final String username=sharedPreferences.getString("username","");

                Available_Model fdel=new Available_Model(id,dd1,ft1,tt1,username);
                databaseReference.child(id).setValue(fdel);
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.doctor_fragment_container,new ViewAvailableTime()).commit();






            }
        });
        return view;
    }


}
