package com.example.biji;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private NoteDatabase dbHelper;

    private Context context=this;
    final String TAG="tog";
    FloatingActionButton btn;
    TextView tv;
    private ListView lv;
    private NoteAdapter adapter;
    private List<Note> noteList=new ArrayList<>();
    private Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(FloatingActionButton)findViewById(R.id.fab);
        //tv=findViewById(R.id.tv);
        lv=findViewById(R.id.lv);
        myToolbar=findViewById(R.id.myToolbar);
        adapter=new NoteAdapter(getApplicationContext(),noteList);
        refreshListView();
        lv.setAdapter(adapter);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置Toolbar取代ActionBar

        myToolbar.setNavigationIcon(R.drawable.ic_menu_black_24);

        lv.setOnItemClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("mode",4);
                startActivityForResult(intent,0);
            }

        });

    }
    //接受startActivityResult的结果
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {



        int returnMode;
        long note_Id;
        returnMode = data.getExtras().getInt("mode", -1);
        note_Id = data.getExtras().getLong("id", 0);

        if (returnMode == 1) {//update current note

            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);

            Note newNote = new Note(content, time, tag);
            newNote.setId(note_Id);
            CRUD op = new CRUD(context);
            op.open();
            op.updateNote(newNote);
            //achievement.editNote(op.getNote(note_Id),content);
            op.close();


        } else if (returnMode == 0){//create new note
            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);
            Note newNote = new Note(content, time, tag);
            CRUD op = new CRUD(context);
            op.open();
            op.addNote(newNote);
            op.close();
        }else if(returnMode == 2){//delete
            Note curNote = new Note();
            curNote.setId(note_Id);
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(curNote);
            op.close();
        }

          refreshListView();




        super.onActivityResult(requestCode, resultCode, data);
               /*String content=data.getStringExtra("content");
               String time=data.getStringExtra("time");
               Note note =new Note(content,time,1);
               CRUD op=new CRUD(context);
               op.open();
               op.addNote(note);
               op.close();
               refreshListView();*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);


        //search setting
        MenuItem mSearch =menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_clear:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("确认全部删除吗？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                     dbHelper = new NoteDatabase(context);
                                     SQLiteDatabase db = dbHelper.getWritableDatabase();
                                     db.delete("notes",null,null);
                                     db.execSQL("update sqlite_sequence set seq=0 where name='notes'");
                                     refreshListView();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void refreshListView(){

        CRUD op=new CRUD(context);
        op.open();

        if(noteList.size()>0) noteList.clear();
        noteList.addAll(op.getAllNotes());

        op.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        switch (parent.getId()){
            case R.id.lv:
            Note curNote=(Note) parent.getItemAtPosition(position);
            Intent intent=new Intent(MainActivity.this,EditActivity.class);
            intent.putExtra("content",curNote.getContent());
            intent.putExtra("id",curNote.getId());
            intent.putExtra("time",curNote.getTime());
            intent.putExtra("mode",3);//修改笔记mode为3
            intent.putExtra("tag",curNote.getTag());
            startActivityForResult(intent,1);
            break;
        }
    }
}