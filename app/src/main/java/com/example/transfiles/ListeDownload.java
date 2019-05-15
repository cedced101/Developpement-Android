package com.example.transfiles;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ListeDownload extends ListActivity {

    private List<String> fileList = new ArrayList<String>();
    private List<String> pathList = new ArrayList<String>();
    File root;
    File[] files;
    ListView listView;
    Button btnrjoindrelobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();
        root  = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        ListDir(root);
    }

    public void ListDir(File f)
    {
        files = f.listFiles();
        fileList.clear();
        for (File file : files) {
            if (file.getName().endsWith(".pdf"))
            fileList.add(file.getName());
            pathList.add(file.getPath());
        }
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,fileList);
        setListAdapter(directoryList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String recuppath = test(position);
        int itsaintent = 200;
        Intent myIntent = new Intent(getBaseContext(), JoindreLobby.class);
        myIntent.putExtra("filepath", recuppath);
        myIntent.putExtra("filepdf", itsaintent);
        startActivity(myIntent);
    }
    public String test(int i)
    {
        return pathList.get(i);
    }

    public void Initialize()
    {
        listView=(ListView) findViewById(R.id.dowloadFile);
        btnrjoindrelobby = (Button) findViewById(R.id.btnrjoindrelobby);
    }
}
