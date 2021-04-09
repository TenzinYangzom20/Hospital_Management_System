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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Pay extends Fragment {
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
        View view= inflater.inflate(R.layout.fragment_pay, container, false);
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

        final EditText opcost = ((EditText)view.findViewById(R.id.opcaost));
        final EditText cvv = ((EditText)view.findViewById(R.id.cvv));
        final EditText actnum = ((EditText)view.findViewById(R.id.actnum));
        Button button=(Button)view.findViewById(R.id.df);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=sharedPreferences.getString("cusername","");
                final String phone=sharedPreferences.getString("cphone","");

                BookModel bookModel=new BookModel(id,docname,username,phone,apdate,ftime,ttime,bdatee,"0","Op Payed",docstatus,u_d_b,tdel);
                databaseReference.child(id).setValue(bookModel);
                Toast.makeText(getContext(), "Payed Sucess", Toast.LENGTH_SHORT).show();

                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.user_fragment_container,new ViewBookings()).commit();

            }
        });



        return view;
    }


}
