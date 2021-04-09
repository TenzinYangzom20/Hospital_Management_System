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
import java.util.Random;


public class ViewAppoinments extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    List<BookModel> detailsList;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_appoinments, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.myb_lidt1);
        databaseReference= FirebaseDatabase.getInstance().getReference("Booking_Details");
        final String username=sharedPreferences.getString("username","");
        databaseReference.orderByChild("docname").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList = new ArrayList<BookModel>();

                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        BookModel clubdel = childSnapshot.getValue(BookModel.class);
                        detailsList.add(clubdel);
                    }
                   CustomAdoptor customAdoptor = new CustomAdoptor();
                    club_list.setAdapter(customAdoptor);
                }else{

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  view;
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
            view = getActivity().getLayoutInflater().inflate(R.layout.mybook,null);
            TextView docname=(TextView)view.findViewById(R.id.t11);
            TextView bookdate=(TextView)view.findViewById(R.id.t01);
            TextView tdel=(TextView)view.findViewById(R.id.t111);
            TextView tstatus=(TextView)view.findViewById(R.id.t211);
            TextView tstatus1=(TextView)view.findViewById(R.id.t311);

            Button button=(Button)view.findViewById(R.id.opm);
            Button button1=(Button)view.findViewById(R.id.prescr);
            Button token=(Button)view.findViewById(R.id.token);

            docname.setText(detailsList.get(i).getUsername());
            bookdate.setText(detailsList.get(i).getBookdate());
            tdel.setText(detailsList.get(i).getTreatmentdel());
            tstatus.setText(detailsList.get(i).getDocstatus());
            tstatus1.setText(detailsList.get(i).getUserstatus());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BookModel bookModel=new BookModel(detailsList.get(i).getId(),detailsList.get(i).getDocname(),detailsList.get(i).getUsername(),detailsList.get(i).getPhone(),detailsList.get(i).getAp_date(),detailsList.get(i).getFromtime(),detailsList.get(i).getTootime(),detailsList.get(i).getBookdate(),detailsList.get(i).getCount(),detailsList.get(i).getUserstatus(),"Accepted",detailsList.get(i).getUsername_docname_bookdate(),detailsList.get(i).getTreatmentdel());
                    databaseReference.child(detailsList.get(i).getId()).setValue(bookModel);
                    Toast.makeText(getContext(), "Accepted Sucess", Toast.LENGTH_SHORT).show();

                    int permissionCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_PHONE_STATE);
                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                    } else {
                        SmsManager sms = SmsManager.getDefault();
                        Random r=new Random();
                        int z=r.nextInt(45);

                        Log.d("hellow", "sravani2");
                        String msg = "Your Requeste Accepted By The Doctor Please pay the OP cost";
                        sms.sendTextMessage(detailsList.get(i).getPhone(), null, msg, sentPI, deliveredPI);


                    }

                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.doctor_fragment_container,new ViewAppoinments()).commit();


                }
            });

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle=new Bundle();
                    bundle.putString("ap_date",detailsList.get(i).getAp_date());
                    bundle.putString("bookdate",detailsList.get(i).getBookdate());
                    bundle.putString("count",detailsList.get(i).getCount());
                    bundle.putString("docname",detailsList.get(i).getUsername());
                    bundle.putString("docstatus",detailsList.get(i).getDocstatus());
                    bundle.putString("ftime",detailsList.get(i).getFromtime());
                    bundle.putString("ttime",detailsList.get(i).getTootime());
                    bundle.putString("id",detailsList.get(i).getId());
                    bundle.putString("tdel",detailsList.get(i).getTreatmentdel());
                    bundle.putString("u_d_b",detailsList.get(i).getUsername_docname_bookdate());
                    Prescrption addWallet=new Prescrption();
                    addWallet.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.doctor_fragment_container,addWallet).commit();
                }
            });
            token.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailsList.get(i).getUserstatus().equalsIgnoreCase("Op Payed")){
                        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_PHONE_STATE);
                        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                        } else {
                            SmsManager sms = SmsManager.getDefault();
                            Random r=new Random();
                            int z=r.nextInt(45);

                            Log.d("hellow", "sravani2");
                            String msg = "YourToken Number is"+z;
                            sms.sendTextMessage(detailsList.get(i).getPhone(), null, msg, sentPI, deliveredPI);


                        }

                        FragmentManager fm=getFragmentManager();
                        fm.beginTransaction().replace(R.id.doctor_fragment_container,new ViewAppoinments()).commit();

                    }else{
                        Toast.makeText(getContext(),"OP not payed",Toast.LENGTH_LONG).show();
                    }
                }
            });

            return view;
        }
    }


}
