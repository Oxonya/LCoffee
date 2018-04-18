package com.coffee.luwak.lcoffee.ui.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.FSUtils;
import com.coffee.luwak.lcoffee.ui.MasterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReportFragment extends MasterFragment {
    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        final TextView tvForToday = view.findViewById(R.id.tvForToday);
        final ProgressBar progress = view.findViewById(R.id.progress);

        tvForToday.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        try {
            SimpleDateFormat formatDay = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
            Long mills = formatDay.parse(formatDay.format(new Date())).getTime();
            Long mills1d = mills + TimeUnit.DAYS.toMillis(1);

        FSUtils.getOrdersCol().whereGreaterThanOrEqualTo("date", mills)
                .whereLessThanOrEqualTo("date", mills1d)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                int docNumber = documents.size();
                Long sum = 0L;
                Long sumCard = 0L;
                Long sumCash = 0L;

                for (DocumentSnapshot doc : documents) {
                    Map<String, Object> map = doc.getData();
                    Long curSum = (Long) map.get("sum");

                    boolean cardPay = (Boolean) map.get("isCardPay");
                    if (cardPay)
                        sumCard += curSum;
                    else
                        sumCash += curSum;

                    sum += curSum;
                }

                tvForToday.setText(getString(R.string.report, docNumber, sum, sumCard, sumCash));
                tvForToday.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tvForToday.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        });

        } catch (Exception e) {
            tvForToday.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            e.printStackTrace();
        }
        return view;
    }

}
