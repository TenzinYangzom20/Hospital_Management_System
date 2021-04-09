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


public class Feedback extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    String fromm;
    String ftime;
    String ttime;
    String mny;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_feedback, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Feedback_Details");

        Bundle extras =this.getArguments();
        fromm= extras.getString("fromm");

        final EditText feeedbak1 = ((EditText)view.findViewById(R.id.feedback1));
        Button button=(Button)view.findViewById(R.id.fd_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String feeedbak=feeedbak1.getText().toString();
                final String username=sharedPreferences.getString("cusername","");
                String id1 = databaseReference.push().getKey();
                Feedback_Model fdel=new Feedback_Model(id,feeedbak,fromm,username);
                databaseReference.child(id1).setValue(fdel);
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.user_fragment_container,new ViewPrescrptionDel()).commit();


            }
        });
        return view;
    }


}
