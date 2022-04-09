package com.cookandroid.routinepartner;

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

    Button btnGetList;
    protected ActInfoRecyclerViewAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<ActInfo> actInfoArrayList;
    protected ArrayList<ActInfoItem> actInfoList = new ArrayList<ActInfoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_info_list);

        btnGetList = findViewById(R.id.btnGetList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.actInfoRecyclerView);
        mAdapter = new ActInfoRecyclerViewAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // database에서 data를 불러오는 task는 main thread에서 할 수 없음
        // main thread 와 multi thread는 따로 움직여서 다른 일을 같이 처리하면 collision이 일어난다
        ActInfoDB.databaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDao mActInfoDao = db.actInfoDao();
            actInfoArrayList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getAll()));
            for(int i = 0; i < actInfoArrayList.size(); i++) {
                actInfoList.add(new ActInfoItem(actInfoArrayList.get(i).getYear(), actInfoArrayList.get(i).getMonth(), actInfoArrayList.get(i).getDate(), actInfoArrayList.get(i).getCategory(), actInfoArrayList.get(i).getStartHour(), actInfoArrayList.get(i).getStartMinute(), actInfoArrayList.get(i).getEndHour(), actInfoArrayList.get(i).getEndMinute()));
            }
        });

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < actInfoArrayList.size(); i++) {
                    mAdapter.addItem(actInfoList.get(i));
                }
                // ui를 바꾸는 task는 무조건 main thread에서만 일어나야 한다
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
