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


public class ViewBookings extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    List<BookModel> detailsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_bookings, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.myb_lidt);
        databaseReference= FirebaseDatabase.getInstance().getReference("Booking_Details");
        final String username=sharedPreferences.getString("cusername","");
        databaseReference.orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
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
            view = getActivity().getLayoutInflater().inflate(R.layout.mybooking_list,null);
            TextView docname=(TextView)view.findViewById(R.id.t);
            TextView bookdate=(TextView)view.findViewById(R.id.t0);
            TextView tdel=(TextView)view.findViewById(R.id.t1);
            TextView tstatus=(TextView)view.findViewById(R.id.t2);
            TextView tstatus1=(TextView)view.findViewById(R.id.t3);

            Button button=(Button)view.findViewById(R.id.pay);

            docname.setText(detailsList.get(i).getDocname());
            bookdate.setText(detailsList.get(i).getBookdate());
            tdel.setText(detailsList.get(i).getTreatmentdel());
            tstatus.setText(detailsList.get(i).getDocstatus());
            tstatus1.setText(detailsList.get(i).getUserstatus());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle=new Bundle();
                    bundle.putString("ap_date",detailsList.get(i).getAp_date());
                    bundle.putString("bookdate",detailsList.get(i).getBookdate());
                    bundle.putString("count",detailsList.get(i).getCount());
                    bundle.putString("docname",detailsList.get(i).getDocname());
                    bundle.putString("docstatus",detailsList.get(i).getDocstatus());
                    bundle.putString("ftime",detailsList.get(i).getFromtime());
                    bundle.putString("ttime",detailsList.get(i).getTootime());
                    bundle.putString("id",detailsList.get(i).getId());
                    bundle.putString("tdel",detailsList.get(i).getTreatmentdel());
                    bundle.putString("u_d_b",detailsList.get(i).getUsername_docname_bookdate());
                    Pay addWallet=new Pay();
                    addWallet.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.user_fragment_container,addWallet).commit();
                         }
            });

            return view;
        }
    }

}
