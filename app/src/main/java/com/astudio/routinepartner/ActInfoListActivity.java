package com.astudio.routinepartner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

// 정한 날짜의 ActInfo를 확인하고 수정하는 Activity
public class ActInfoListActivity extends AppCompatActivity {

    Button BtnGetList;
    protected ActInfoRecyclerViewAdapter MAdapter;
    protected RecyclerView MRecyclerView;
    protected RecyclerView.LayoutManager MLayoutManager;
    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_info_list);

        BtnGetList = findViewById(R.id.btnGetList);
        MLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        MRecyclerView = findViewById(R.id.actInfoRecyclerView);
        MAdapter = new ActInfoRecyclerViewAdapter(getApplicationContext());
        MRecyclerView.setAdapter(MAdapter);
        MRecyclerView.setLayoutManager(MLayoutManager);

        // database에서 data를 불러오는 task는 main thread에서 할 수 없음
        // main thread 와 multi thread는 따로 움직여서 다른 일을 같이 처리하면 collision이 일어난다
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getAll()));
            for(int i = 0; i < ActInfoList.size(); i++) {
                ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
            }
        });

        BtnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < ActInfoList.size(); i++) {
                    MAdapter.addItem(ActInfoItemList.get(i));
                }
                // ui를 바꾸는 task는 무조건 main thread에서만 일어나야 한다
                MAdapter.notifyDataSetChanged();
            }
        });
    }
}