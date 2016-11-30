package com.example.administrator.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by dragyu on 2016/11/23.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int mItemPosition;

    @Nullable
    @Override
    //用inflater方法将实例化的View托管给CrimeListActivity
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        //创建CrimeRecyclerView视图
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        //立即转交给LayoutManager对象，LayoutManager负责在屏幕上定位列表项，定义屏幕的滚动行为
        //RecyclerView的正常工作必须有LayoutManager
        mCrimeRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        updateUI();
        return  view;

    }

    //刷新显示列表项
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //将Adapter与RecyclerView关联起来，实现一个设置CrimeListFragment用户界面的方法
    //该方法创建CrimeAdapter,并设置RecyclerView
    private void updateUI(){
        //?
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyItemChanged(mItemPosition);
        }

    }


    //定义ViewHolder内部类
    private class CrimeHolder extends RecyclerView.ViewHolder
                              implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;



        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView =(TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_check_box);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(DateFormat.format("EEEE,MMMM d,yyyy HH:mm",mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCrime.setSolved(b);
                }
            });

        }

        @Override
        public void onClick(View view) {

            mItemPosition = mCrimeRecyclerView.getChildAdapterPosition(view);
            //从Fragment启动Activity
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);

        }
    }

    //定义adapter内部类
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public  CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        //RecyclerView创建新的View视图时调用
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //？
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }

        //把ViewHolder的View视图
        //和模型层数据绑定起来
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            //接收传入的索引位置，绑定要显示数据
            Crime crime = mCrimes.get(position);
            //刷新显示
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
