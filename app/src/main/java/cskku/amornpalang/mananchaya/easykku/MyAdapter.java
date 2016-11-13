package cskku.amornpalang.mananchaya.easykku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 13-Nov-16.
 */

public class MyAdapter extends BaseAdapter {
    //Explicit
    private Context context;
    private String[] nameString,phoneString, imageString;
    private TextView nameTextView,phoneTextView;
    private ImageView imageView;

    public MyAdapter(Context context,
                     String[] nameString,
                     String[] phoneString,
                     String[] imageString) {
        this.context = context;
        this.nameString = nameString;
        this.phoneString = phoneString;
        this.imageString = imageString;
    }

    @Override
    //จำนวนลิสต์ ที่ต้องการปริ้นออกมา
    public int getCount() {
        return nameString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //หนีบของหลายๆๆอย่างส่งที่ Layout
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_layout,parent,false);

        //Bind Widget
        nameTextView = (TextView) view.findViewById(R.id.textView2);
        phoneTextView = (TextView) view.findViewById(R.id.textView3);
        imageView = (ImageView) view.findViewById(R.id.imageView3);
        //Show view
        nameTextView.setText(nameString[position]);
        phoneTextView.setText(phoneString[position]);
        // โหลดรูปภาพลงมาจาก Server
        Picasso.with(context).load(imageString[position]).into(imageView);




        return view;
    }
}// Main class

