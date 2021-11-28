package com.example.biji;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;

    private List<Note> backList;//用这个来备份原始数据
    private List<Note> noteList;//这个数据是会改变的，所以要有个变量来备份一下原始数据
    private MyFilter myFilter;

    public NoteAdapter(Context mContext,List<Note> noteList){
        this.mContext=mContext;
        this.noteList=noteList;
        backList=noteList;
    }

   @Override
    public int getCount(){ return noteList.size();}

    @Override
    public Object getItem(int position){ return noteList.get(position);}

    @Override
    public long getItemId(int position){ return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.DayTheme);
        View v=View.inflate(mContext,R.layout.note_layout,null);
        TextView tv_content=(TextView)v.findViewById(R.id.tv_content);
        TextView tv_time=(TextView)v.findViewById(R.id.tv_time);

        //Set text for TextView
        String  allText=noteList.get(position).getContent();
        /*if(sharedPreference.getBoolean("noteTitle",true))
          tv_content.setText(allText.split("\n")[0]);
          else tv_content.setText(allText);
          */
        tv_content.setText(allText);
        tv_time.setText(noteList.get(position).getTime());

        //save note id to tag
        v.setTag(noteList.get(position).getId());

        return v;

    }

    class MyFilter extends Filter{
        //在performFiltering方法中定义过滤器
        @Override
       protected FilterResults performFiltering(CharSequence charSequence){
            FilterResults results=new FilterResults();
            List<Note> list;
            if(TextUtils.isEmpty(charSequence)){
                list=backList;
            }else{
                list=new ArrayList<>();
                for (Note note : backList){
                       if(note.getContent().contains(charSequence)){
                           list.add(note);
                       }
                }
            }
            results.values=list;
            results.count=list.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence,FilterResults filterResults){
              noteList = (List<Note>)filterResults.values;
              if(filterResults.count>0){
                  notifyDataSetChanged();//通知数据发生改变;
              }else{
                  notifyDataSetInvalidated();//通知数据失败;
              }
        }

    }

    @Override
    public Filter getFilter(){
        if(myFilter==null){
            myFilter=new MyFilter();
        }
        return myFilter;
    }

}
