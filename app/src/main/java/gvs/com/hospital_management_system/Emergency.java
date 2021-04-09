package gvs.com.hospital_management_system;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Emergency extends AppCompatActivity  {
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
    EditText descrptin;
    LocationManager locationManager;
    String lattitude,longitude,Emp_id;

    private static final int REQUEST_LOCATION = 1;
    DatabaseReference databasereference;
    EditText phone;
    Spinner speclization;
    Button enmegrency;
    public double latitude;


    public Criteria criteria;
    public String bestProvider;
     String descrption;
      String phone1;
      String specilization;
    public static final String CHANNEL_ID= "VSUF";
    private static final String CHANNEL_NAME = "VSUF";
    private static final String CHANNEL_DESC = "VSUF Description";
    String key;
    List<AppTocken> appTockens;
    AppTocken appTocken;
    List<String> tokens;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        databaseReference= FirebaseDatabase.getInstance().getReference("Specialization_Details");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Emergency_Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {


                    String areaName = areaSnapshot.child("specialization").getValue(String.class);
                    fdel.add(areaName);
                }
                Spinner areaSpinner = (Spinner)findViewById(R.id.emergency_spinner);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Emergency.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        descrptin=(EditText)findViewById(R.id.descrption);
        phone=(EditText)findViewById(R.id.emergency_phone);
        speclization=(Spinner)findViewById(R.id.emergency_spinner);
        enmegrency=(Button)findViewById(R.id.emergency_btn);

        enmegrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descrption=descrptin.getText().toString();
                phone1=phone.getText().toString();
                specilization=speclization.getSelectedItem().toString();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocation();
                }





            }
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Date c = Calendar.getInstance().getTime();
                Log.d("dateis", String.valueOf(c));

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                final String curdate = df.format(c);
                databaseReference2.orderByChild("date_phone").equalTo(curdate+"_"+phone1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                EmergencyModel edel = childSnapshot.getValue(EmergencyModel.class);
                                String count=edel.getCount();
                                int countt=Integer.parseInt(count);
                                int count1=countt+1;
                                if(count1 <= 4) {
                                    edel.setDescription(descrption);
                                    edel.setId(edel.getId());
                                    edel.setCount(String.valueOf(count1));
                                    edel.setDate(curdate);
                                    edel.setDate_phone(curdate + "_" + phone1);
                                    edel.setLatitude(lattitude);
                                    edel.setLongtitude(longitude);
                                    edel.setPhone(phone1);
                                    edel.setSpecilization(specilization);
                                    edel.setStatus("Pending");
                                    databaseReference2.child(edel.getId()).setValue(edel);
                                    Toast.makeText(getApplicationContext(), "Send Request Sucess", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    databaseReference2.child(edel.getId()).removeValue();
                                    Toast.makeText(getApplicationContext(), "Fake User", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }else{
                            int count=1;
                            String id=databaseReference2.push().getKey();
                            EmergencyModel edel1=new EmergencyModel();
                            edel1.setDescription(descrption);
                            edel1.setId(id);
                            edel1.setCount(String.valueOf(count));
                            edel1.setDate(curdate);
                            edel1.setDate_phone(curdate+"_"+phone1);
                            edel1.setLatitude(lattitude);
                            edel1.setLongtitude(longitude);
                            edel1.setPhone(phone1);
                            edel1.setSpecilization(specilization);
                            edel1.setStatus("Pending");
                            databaseReference2.child(id).setValue(edel1);
                            Toast.makeText(getApplicationContext(),"Send Request Sucess",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                // + "\n" + "Longitude = " + longitude);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                // + "\n" + "Longitude = " + longitude);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                // + "\n" + "Longitude = " + longitude);

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
