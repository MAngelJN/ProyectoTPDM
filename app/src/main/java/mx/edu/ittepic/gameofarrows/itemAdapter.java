package mx.edu.ittepic.gameofarrows;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juan on 01/06/2016.
 */
public class itemAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> arrayList2;
    int[] imagenes;
    LayoutInflater inflater;




    public itemAdapter(Context contexto, ArrayList<String> arrayList, int[] img) {
        context = contexto;
        arrayList2 = arrayList;
        imagenes = img;

    }

    @Override
    public int getCount() {
        return arrayList2.size();
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
    public View getView(int position, View contentView, ViewGroup parent) {


        ImageView imgImg;

        /*        if(contentView == null){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_item_layout, parent, false);
        }*/
        // Declare Variables

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item_layout, parent, false);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.list_row_title);
        imgImg = (ImageView) itemView.findViewById(R.id.list_row_image);


        txtTitle.setText(arrayList2.get(position));
        imgImg.setImageResource(imagenes[0]);

        return itemView;


    }
}
