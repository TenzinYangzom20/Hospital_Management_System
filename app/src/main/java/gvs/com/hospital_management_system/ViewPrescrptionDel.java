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


public class ViewPrescrptionDel extends Fragment {

    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    List<Prescrption_Model> detailsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_prescrption_del, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Prescrption_Details");
        final String username=sharedPreferences.getString("cusername","");

        club_list=(ListView)view.findViewById(R.id.hos_list1);
        databaseReference.orderByChild("too").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    detailsList = new ArrayList<Prescrption_Model>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Prescrption_Model clubdel = childSnapshot.getValue(Prescrption_Model.class);
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
            view = getActivity().getLayoutInflater().inflate(R.layout.prescr_list,null);
            TextView hnamee=(TextView)view.findViewById(R.id.x);
            TextView hostype=(TextView)view.findViewById(R.id.y);
            TextView address=(TextView)view.findViewById(R.id.z);

            hnamee.setText(detailsList.get(i).getFromm());
            hostype.setText(detailsList.get(i).getTrdel());
            address.setText(detailsList.get(i).getPrescrption());
            Button button=(Button)view.findViewById(R.id.feedback);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString("fromm",detailsList.get(i).getFromm());

                    Feedback addWallet=new Feedback();
                    addWallet.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.user_fragment_container,addWallet).commit();


                }
            });

            return view;
        }
    }

}
