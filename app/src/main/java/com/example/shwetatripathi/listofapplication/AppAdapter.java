package com.example.shwetatripathi.listofapplication;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends ArrayAdapter<ApplicationInfo>{

    private List<ApplicationInfo> applist=null;
    private Context context;
    private PackageManager packageManager;


    public AppAdapter(@NonNull Context context, int resource, @NonNull List<ApplicationInfo> objects) {
        super(context, resource, objects);

        this.context=context;
        this.applist=objects;
        packageManager=context.getPackageManager();

    }

    @Override
    public int getCount() {
        return ((null != applist) ? applist.size(): 0);
    }

    @Nullable
    @Override
    public ApplicationInfo getItem(int position) {
        return ((null !=applist) ? applist.get(position) : null);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;

        if(null==view)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.list_items,null);
        }

        ApplicationInfo data=applist.get(position);
        if(null!=data)
        {
            TextView appName=(TextView)view.findViewById(R.id.app_name);
            TextView packageName=(TextView)view.findViewById(R.id.app_package);
            ImageView iconView =(ImageView)view .findViewById(R.id.app_icon);

            appName.setText(data.loadLabel(packageManager));
            packageName.setText(data.packageName);
            iconView.setImageDrawable(data.loadIcon(packageManager));
        }
        return view;
    }
}


