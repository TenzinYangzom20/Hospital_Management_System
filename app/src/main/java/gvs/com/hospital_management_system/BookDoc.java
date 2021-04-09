package gvs.com.hospital_management_system;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BookDoc extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    String datee;
    String ftime;
    String ttime;
    String docname;
    String id;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_book_doc, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Booking_Details");
        databaseReference1= FirebaseDatabase.getInstance().getReference("User_Details");

        Bundle extras =this.getArguments();
        datee= extras.getString("datee");
        ftime= extras.getString("ftime");
        ttime= extras.getString("ttime");
        id= extras.getString("id");
        docname=extras.getString("docname");
        final EditText dd = ((EditText)view.findViewById(R.id.tdel));
        Button button=(Button)view.findViewById(R.id.dd1);
        Date c = Calendar.getInstance().getTime();
        Log.d("dateis", String.valueOf(c));

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(c);
        final String username=sharedPreferences.getString("cusername","");
        final String phone=sharedPreferences.getString("cphone","");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dd1=dd.getText().toString();
                final String username_docname_bookdate=username+"_"+docname+"_"+formattedDate;
        databaseReference.orderByChild("username_docname_bookdate").equalTo(username_docname_bookdate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();

                        String count = childSnapshot.child("count").getValue().toString();
                        int countt=Integer.parseInt(count);
                        if(countt<=3){
                            int countt1=countt+1;
                        BookModel bookModel=new BookModel(id,docname,username,phone,datee,ftime,ttime,formattedDate,String.valueOf(countt1),"waiting","Not Accepted",username_docname_bookdate,dd1);
                            databaseReference.child(key).setValue(bookModel);
                            Toast.makeText(getContext(), "Booking Sucess", Toast.LENGTH_SHORT).show();

                            FragmentManager fm=getFragmentManager();
                            fm.beginTransaction().replace(R.id.user_fragment_container,new ViewBookings()).commit();

                        }
                        else{
                            databaseReference1.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            String key1 = childSnapshot.getKey();
                                            databaseReference1.child(key1).removeValue();
                                            int permissionCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_PHONE_STATE);
                                            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.SEND_SMS)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions((Activity)getContext(), new String[]{android.Manifest.permission.SEND_SMS},
                                                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                                            } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                                            } else {
                                                SmsManager sms = SmsManager.getDefault();

                                                Log.d("hellow", "sravani2");
                                                String msg = "Your are Fake User..Your Account Deleted form the admin";
                                                sms.sendTextMessage(phone, null, msg, sentPI, deliveredPI);


                                            }

                                        }
                                    }else{

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            databaseReference.child(key).removeValue();
                            Toast.makeText(getContext(), "Your are Fake User..Your Account Deleted form the admin", Toast.LENGTH_SHORT).show();


                        }

                    }
                }else{
                    String id = databaseReference.push().getKey();
                    BookModel bookModel=new BookModel(id,docname,username,phone,datee,ftime,ttime,formattedDate,"0","waiting","Not Accepted",username_docname_bookdate,dd1);
                    databaseReference.child(id).setValue(bookModel);
                    Toast.makeText(getContext(), "Booking Sucess", Toast.LENGTH_SHORT).show();

                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.user_fragment_container,new ViewBookings()).commit();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


            }
        });


        return view;
    }


}
