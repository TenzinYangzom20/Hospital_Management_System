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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewDoctors extends Fragment {
        ListView club_list;
        public static final String MyPREFERENCES = "MyPrefs";
        SharedPreferences sharedPreferences;
        DatabaseReference databaseReference;
        DatabaseReference databaseReference1;
        DatabaseReference databaseReference2;

        List<Doctor_Model> detailsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_view_doctors, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.doc_list);
        databaseReference= FirebaseDatabase.getInstance().getReference("Doctor_Details");
        final String username=sharedPreferences.getString("username","");

        databaseReference2= FirebaseDatabase.getInstance().getReference("Specialization_Details");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {


                    String areaName = areaSnapshot.child("specialization").getValue(String.class);
                    fdel.add(areaName);
                }
                Spinner areaSpinner = (Spinner)view.findViewById(R.id.spec);
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
                Spinner areaSpinner = (Spinner)view.findViewById(R.id.hos);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Spinner hos_spiner = (Spinner)view.findViewById(R.id.hos);
        final Spinner spec_spinner = (Spinner)view.findViewById(R.id.spec);

        Button  add_btn=(Button)view.findViewById(R.id.deoc_btn);
      add_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              final String hos_spiner1 = hos_spiner.getSelectedItem().toString();
              final String spec_spinner1 = spec_spinner.getSelectedItem().toString();
                databaseReference.orderByChild("hosname").equalTo(hos_spiner1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            databaseReference.orderByChild("speci").equalTo(spec_spinner1).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    detailsList = new ArrayList<Doctor_Model>();
                                   if(dataSnapshot.exists()){
                                       for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                           Doctor_Model clubdel = childSnapshot.getValue(Doctor_Model.class);
                                           detailsList.add(clubdel);
                                       }
                                      CustomAdoptor customAdoptor = new CustomAdoptor();
                                       club_list.setAdapter(customAdoptor);


                                   } else{
                                       Toast.makeText(getContext(), "Required Specilization Doctors are Not Available", Toast.LENGTH_SHORT).show();

                                   }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }else{
                            Toast.makeText(getContext(), "Doctor are Not Available", Toast.LENGTH_SHORT).show();

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
            view = getActivity().getLayoutInflater().inflate(R.layout.doctor_list,null);
            TextView dname=(TextView)view.findViewById(R.id.dname);
            TextView exp=(TextView)view.findViewById(R.id.exp);
            TextView about=(TextView)view.findViewById(R.id.about);
            Button button=(Button)view.findViewById(R.id.view_btn);

            dname.setText(detailsList.get(i).getUsername());
            exp.setText(detailsList.get(i).getExpi());
            about.setText(detailsList.get(i).getAbout());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle=new Bundle();
                    bundle.putString("datee",detailsList.get(i).getUsername());

                    BookDoctor addWallet=new BookDoctor();
                    addWallet.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.user_fragment_container,addWallet).commit();



                }
            });

            return view;
        }
    }

}
