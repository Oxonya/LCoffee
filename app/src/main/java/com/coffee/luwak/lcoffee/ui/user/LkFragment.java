package com.coffee.luwak.lcoffee.ui.user;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.Const;
import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class LkFragment extends MasterFragment {
    private TextView t50, t100, t250, t350, t450;

    public LkFragment() {
        // Required empty public constructor
    }

    public static LkFragment newInstance() {
        return new LkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lk, container, false);

        t50 = view.findViewById(R.id.t50);
        t100 = view.findViewById(R.id.t100);
        t250 = view.findViewById(R.id.t250);
        t350 = view.findViewById(R.id.t350);
        t450 = view.findViewById(R.id.t450);

        observe();
        setInfo(0, 0, 0, 0, 0);

        return view;
    }

    private void setInfo(long t5, long t10, long t25, long t35, long t45) {
        t50.setText(t5 + "");
        t100.setText(t10 + "");
        t250.setText(t25 + "");
        t350.setText(t35 + "");
        t450.setText(t45 + "");
    }

    private void observe() {
        final DocumentReference docRef = App.fbStore
                .collection(Const.CUPS_COLLECTION)
                .document(App.fbAuth.getCurrentUser().getEmail() + Const.CUPS_SUFFIX);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(this.toString(), "Listen failed. ", e);
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Map<String, Object> map = documentSnapshot.getData();
                    setInfo(map.get(Const.T5) == null ? 0 : (long)map.get(Const.T5),
                            map.get(Const.T10) == null ? 0 : (long)map.get(Const.T10),
                            map.get(Const.T25) == null ? 0 : (long)map.get(Const.T25),
                            map.get(Const.T35) == null ? 0 : (long)map.get(Const.T35),
                            map.get(Const.T45) == null ? 0 : (long)map.get(Const.T45));
                } else {
                    setInfo(0, 0, 0, 0, 0);
                }
            }
        });
    }
}
