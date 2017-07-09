package com.example.pc1.tuvung_everyday.Adapter;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc1.tuvung_everyday.Database.DBTuVung_Everyday;
import com.example.pc1.tuvung_everyday.MainActivity;
import com.example.pc1.tuvung_everyday.Model.TuVung;
import com.example.pc1.tuvung_everyday.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.Voice.LATENCY_LOW;

/**
 * Created by pc1 on 7/5/2017.
 */

public class AdapterItemTuVung extends ArrayAdapter<TuVung> {
    private Context context;
    private int resource;
    private ArrayList<TuVung> objects;



    public AdapterItemTuVung(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TuVung> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tu_vung, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTA = (TextView) convertView.findViewById(R.id.tvTA);
            viewHolder.tvTV = (TextView) convertView.findViewById(R.id.tvTV);
            viewHolder.imbXoaTu = (ImageButton) convertView.findViewById(R.id.imbXoaTu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TuVung tuVung = objects.get(position);
        viewHolder.tvTA.setText(tuVung.getTiengAnh().toString());
        viewHolder.tvTV.setText(tuVung.getTiengViet().toString());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.textToSpeech1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {
                      //  int i = textToSpeech.setVoice(Voice.LATENCY_NORMAL);

                        MainActivity.spkTA(tuVung.getTiengAnh().toString());


                    }
                });
                MainActivity.textToSpeech2 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        MainActivity.spkTV(" Có nghĩa là " + tuVung.getTiengViet().toString());
                    }
                });
               // Toast.makeText(context, "Position ... " + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imbXoaTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dataBase = new DBTuVung_Everyday(context);
                MainActivity.dataBase .deleteTu(tuVung);
                objects.remove(position);
               // MainActivity.SIZE_LIST = objects.size();
                if(objects.size()==0)
                {
                    MainActivity.khungList.setVisibility(View.GONE);
                }
                MainActivity.txtTitleListTu.setText("Các từ được thêm ("+objects.size()+")");

                notifyDataSetChanged();


            }
        });

        return convertView;
    }
    // cập nhật lại id của tu




    public class ViewHolder {
        public TextView tvTA;
        public TextView tvTV;
        public ImageButton imbXoaTu;
    }
}
