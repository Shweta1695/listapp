package com.example.shwetatripathi.listofapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private PackageManager packageManager=null;
    private List<ApplicationInfo> applist=null;
    private AppAdapter listadapter=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager =getPackageManager();

        new LoadApplication().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app= applist.get(position);

        try{
            Intent intent=packageManager.getLaunchIntentForPackage(app.packageName);
            if(intent!=null)
            {
                startActivity(intent);

            }
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    private List<ApplicationInfo>checkForLaunchIntent(List<ApplicationInfo> list)
    {
        ArrayList<ApplicationInfo> applist= new ArrayList<ApplicationInfo>();
        for(ApplicationInfo info:list)
        {
            try{
                if(packageManager.getLaunchIntentForPackage(info.packageName)!=null)
                {
                    applist.add(info);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }


    private class LoadApplication extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog progress=null;

        @Override
        protected Void doInBackground(Void... voids) {

        applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));

        listadapter= new AppAdapter(MainActivity.this,R.layout.list_items,applist);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setListAdapter(listadapter);
            progress.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            progress=ProgressDialog.show(MainActivity.this,null,"Loading apps info..");


            super.onPreExecute();
        }
    }
}
