package gvs.com.hospital_management_system;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewEmergencyNotifications extends Fragment {
    ListView emergency_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
     String specialization,hospitalName,username;
    List<EmergencyModel> detailsList;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_emergency_notifications, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Emergency_Details");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Hospital_Details");
        specialization=sharedPreferences.getString("speci","");
        hospitalName=sharedPreferences.getString("hosname","");
        username=sharedPreferences.getString("username","");
        emergency_list=(ListView)view.findViewById(R.id.emergecny_list);

        databaseReference.orderByChild("specilization").equalTo(specialization).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        final EmergencyModel emergencyModel=childSnapshot.getValue(EmergencyModel.class);
                        databaseReference2.orderByChild("hname").equalTo(hospitalName).addListenerForSingleValueEvent(new ValueEventListener() {

                                                                                                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                     if(dataSnapshot.exists()){
                                                                                                                                         for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                                                                                                             Hospital_Model hospitalModel=childSnapshot.getValue(Hospital_Model.class);
                                                                                                                                             String latitude=hospitalModel.getLati();
                                                                                                                                             String longtitude=hospitalModel.getLang();
                                                                                                                                             String lati1=emergencyModel.getLatitude();
                                                                                                                                             String long1=emergencyModel.getLongtitude();
                                                                                                                                             Location locationA = new Location("point A");
                                                                                                                                             locationA.setLatitude(Double.parseDouble(latitude));
                                                                                                                                             locationA.setLongitude(Double.parseDouble(longtitude));
                                                                                                                                             Location locationB = new Location("point B");
                                                                                                                                             locationB.setLatitude(Double.parseDouble(lati1));
                                                                                                                                             locationB.setLongitude(Double.parseDouble(long1));
                                                                                                                                           float distance = locationB.distanceTo(locationA) ;
                                                                                                                                             Log.d("distance is", String.valueOf(distance));

                                                                                                                                             float distancekm=distance/1000;
                                                                                                                                             float distance2=distancekm/1000;
                                                                                                                                             Log.d("distance in km", String.valueOf(distancekm));
                                                                                                                                             Toast.makeText(getContext(), "Distance is"+ distance2, Toast.LENGTH_SHORT).show();
                                                                                                                                             if(distance2 <= 8){
                                                                                                                                                 detailsList = new ArrayList<EmergencyModel>();
                                                                                                                                                 detailsList.add(emergencyModel);
                                                                                                                                                CustomAdoptor customAdoptor = new CustomAdoptor();
                                                                                                                                                 emergency_list.setAdapter(customAdoptor);


                                                                                                                                             }else{

                                                                                                                                             }





                                                                                                                                         }
                                                                                                                                         }else{
                                                                                                                                         Toast.makeText(getContext(), "Distance is not available", Toast.LENGTH_SHORT).show();


                                                                                                                                     }


                                                                                                                                 }

                                                                                                                                 @Override
                                                                                                                                 public void onCancelled(DatabaseError databaseError) {

                                                                                                                                 }
                                                                                                                             }
                        );

                    }


                    }else{
                    Toast.makeText(getContext(), "Data Is Not Availablee", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
    class CustomAdoptor extends BaseAdapter {

        @Override
        public int getCount() {
            return detailsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.emergency_list,null);
            final TextView descr=(TextView)view.findViewById(R.id.descr);
            TextView reqnum=(TextView)view.findViewById(R.id.reqnum);
            Button button=(Button)view.findViewById(R.id.eaccpt_btn) ;

            descr.setText(detailsList.get(i).getDescription());
            reqnum.setText(detailsList.get(i).getPhone());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmergencyModel fdel=new EmergencyModel(detailsList.get(i).getId(),detailsList.get(i).getLatitude(),detailsList.get(i).getLongtitude(),detailsList.get(i).getSpecilization(),detailsList.get(i).getDescription(),detailsList.get(i).getPhone(),"Accepted",detailsList.get(i).getDate(),detailsList.get(i).getDate_phone(),detailsList.get(i).getCount());

                    databaseReference.child(detailsList.get(i).getId()).setValue(fdel);
                    int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                    } else {
                        SmsManager sms = SmsManager.getDefault();

                        Log.d("hellow", "sravani2");
                        String msg = "Your Emergency Requested by the Doctor" + " " + username + " " + " at  :" + " " + hospitalName+" "+ "Hospital" ;
                        sms.sendTextMessage(detailsList.get(i).getPhone(), null, msg, sentPI, deliveredPI);


                    }


                }
            });

            return view;
        }
    }

}
