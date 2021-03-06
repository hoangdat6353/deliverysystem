package hoangdat.tdtu.deliverysystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Handling#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Handling extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    RecyclerView rcvHandling;
    List<ShipObject> lstShipping;
    List<String> mKeys = new ArrayList<>();
    HandlingAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Handling() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Handling.
     */
    // TODO: Rename and change types and number of parameters
    public static Handling newInstance(String param1, String param2) {
        Handling fragment = new Handling();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_handling, container, false);

        getData();
        rcvHandling = rootView.findViewById(R.id.rcvHandling);
        rcvHandling.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HandlingAdapter(lstShipping,getActivity());
        rcvHandling.setAdapter(adapter);
        return rootView;
    }
    private void getData()
    {
        lstShipping = new ArrayList<>();
        myRef = database.getReference("Qu???n L?? ????n H??ng").child("khachhang");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShipObject shipObject = snapshot.getValue(ShipObject.class);
                if (shipObject != null && shipObject.getShipStatus().equals("???? chuy???n t???i nh??n vi??n kho"))
                {
                    lstShipping.add(shipObject);
                    String key = snapshot.getKey();
                    mKeys.add(key);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                ShipObject rmd = snapshot.getValue(ShipObject.class);
//                if (rmd == null || lstShipping == null || lstShipping.isEmpty())
//                {
//                    return;
//                }
//                int index = mKeys.indexOf(snapshot.getKey());
//                lstShipping.set(index,rmd);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                ShipObject rmd = snapshot.getValue(ShipObject.class);
//                if (rmd == null || lstShipping == null || lstShipping.isEmpty())
//                {
//                    return;
//                }
//                int index = mKeys.indexOf(snapshot.getKey());
//                if (index != -1)
//                {
//                    lstShipping.remove(index);
//                    mKeys.remove(index);
//                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}