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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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


public class AddDoctor extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    Button add_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_add_doctor, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("Specialization_Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {


                    String areaName = areaSnapshot.child("specialization").getValue(String.class);
                    fdel.add(areaName);
                }
                Spinner areaSpinner = (Spinner)view.findViewById(R.id.spec_spinner);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference1= FirebaseDatabase.getInstance().getReference("Hospital_Details");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {


                    String areaName = areaSnapshot.child("hname").getValue(String.class);
                    fdel.add(areaName);
                }
                Spinner areaSpinner = (Spinner)view.findViewById(R.id.hos_spiner);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final EditText odcname = ((EditText)view.findViewById(R.id.odcname));
        final EditText docusername = ((EditText)view.findViewById(R.id.docusername));
        final EditText docexp = ((EditText)view.findViewById(R.id.docexp));
        final EditText docaddress = ((EditText)view.findViewById(R.id.docaddress));
        final EditText dphone = ((EditText)view.findViewById(R.id.docphone));
        final Spinner hos_spiner = (Spinner)view.findViewById(R.id.hos_spiner);
        final Spinner spec_spinner = (Spinner)view.findViewById(R.id.spec_spinner);
        add_btn=(Button)view.findViewById(R.id.doc_btn);
        final String doc_hos=docusername+"_"+hos_spiner;
        odcname.setInputType(InputType.TYPE_CLASS_TEXT|
                InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );
        docusername.setInputType(InputType.TYPE_CLASS_TEXT|
                InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );
        docaddress.setInputType(InputType.TYPE_CLASS_TEXT|
                InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String odcname1=odcname.getText().toString();
                final String docusername1=docusername.getText().toString();
                final String docexp1=docexp.getText().toString();
                final String docaddress1=docaddress.getText().toString();
                final String dphone1=dphone.getText().toString();
                final String hos_spiner1 = hos_spiner.getSelectedItem().toString();
                final String spec_spinner1 = spec_spinner.getSelectedItem().toString();
                String name=Character.toUpperCase(odcname1.charAt(0)) + odcname1.substring(1) + " ";
                String username=Character.toUpperCase(docusername1.charAt(0)) + odcname1.substring(1) + " ";
                String add=Character.toUpperCase(docaddress1.charAt(0)) + odcname1.substring(1) + " ";
                final String add1=add.trim();
                final String username1=username.trim();
                final String name1=name.trim();
                final String hname_address=username1+"_"+hos_spiner1;

                Log.d("name is",name1);
                if(odcname1.length()>=4 && docusername1.length() >=4 && docexp1.length() >=1 && docaddress1.length() >=4 && dphone1.length() ==10) {
                    databaseReference2 = FirebaseDatabase.getInstance().getReference("Doctor_Details");
                    databaseReference2.orderByChild("username_hosname").equalTo(hname_address).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getContext(), "Doctor already exists", Toast.LENGTH_SHORT).show();

                            }else{
                                Random random=new Random();
                                int i=random.nextInt(456123);
                                final String hname_password=username1+"_"+String.valueOf(i);


                                String id = databaseReference2.push().getKey();
                                Doctor_Model fdel=new Doctor_Model(id,name1,username1,dphone1,String.valueOf(i),spec_spinner1,docexp1,hos_spiner1,add1,hname_address,hname_password);
                                databaseReference2.child(id).setValue(fdel);
                                Toast.makeText(getContext(), "Doctor Added Sucess", Toast.LENGTH_SHORT).show();
                                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.SEND_SMS},
                                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                                } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                                    SmsManager sms = SmsManager.getDefault();

                                    Log.d("hellow", "sravani2");
                                    String msg = "Your Login Credentials" + "UserName:" + " " + docusername1 + " " + " and Use This OTP :" + " " + i ;
                                    sms.sendTextMessage(dphone1, null, msg, sentPI, deliveredPI);

                                } else {
                                    SmsManager sms = SmsManager.getDefault();

                                    Log.d("hellow", "sravani2");
                                    String msg = "Your Login Credentials" + "UserName:" + " " + docusername1 + " " + " and Use This OTP :" + " " + i ;
                                    sms.sendTextMessage(dphone1, null, msg, sentPI, deliveredPI);


                                }


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
