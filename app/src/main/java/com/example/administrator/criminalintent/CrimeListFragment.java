package com.example.administrator.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private boolean mSubtitleVisible;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        if (savedInstanceState!=null){
            mSubtitleVisible = savedInstanceState
                    .getBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
        }

        updateUI();
        return  view;

    }

    /*CrimeListActivity恢复运行后，操作系统发出指令调用onResume()生命周期方法。
    CrimeListActivity接受指令后，它的FragmentManager会调用被当前activity托管的fragment的onResume()
    方法。
    要保证fragment视图得到刷新，一般在onResume()方法内更新代码最安全。
     */
    //Fragment刷新显示列表项
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    //实例化选项菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_cirme_list,menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    //响应菜单选项选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //设置工具栏子标题
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format,crimeCount+"");
        if (!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

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
        updateSubtitle();

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
