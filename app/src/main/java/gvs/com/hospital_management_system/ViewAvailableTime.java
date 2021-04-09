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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAvailableTime extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    List<Available_Model> detailsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_available_time, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.timelist);
        databaseReference= FirebaseDatabase.getInstance().getReference("Doctor_Available_Details");
        final String username=sharedPreferences.getString("username","");


        databaseReference.orderByChild("docname").equalTo(username).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList = new ArrayList<Available_Model>();
               if(dataSnapshot.exists()){
                   for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                       Available_Model clubdel = childSnapshot.getValue(Available_Model.class);
                       detailsList.add(clubdel);
                   }
                 CustomAdoptor customAdoptor = new CustomAdoptor();
                   club_list.setAdapter(customAdoptor);


               } else{

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
            view = getActivity().getLayoutInflater().inflate(R.layout.available_list,null);
            TextView hnamee=(TextView)view.findViewById(R.id.adate);
            TextView ftime=(TextView)view.findViewById(R.id.ftime);
            TextView ttime=(TextView)view.findViewById(R.id.ttime);
            Button button=(Button)view.findViewById(R.id.update_btn);

            hnamee.setText(detailsList.get(i).getDatee());
            ftime.setText(detailsList.get(i).getFromtime());
            ttime.setText(detailsList.get(i).getTootime());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle=new Bundle();
                    bundle.putString("datee",detailsList.get(i).getDatee());
                    bundle.putString("ftime",detailsList.get(i).getFromtime());
                    bundle.putString("ttime",detailsList.get(i).getTootime());
                    bundle.putString("id",detailsList.get(i).getId());
                    UpdateTime addWallet=new UpdateTime();
                    addWallet.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.doctor_fragment_container,addWallet).commit();



                }
            });

            return view;
        }
    }

}
