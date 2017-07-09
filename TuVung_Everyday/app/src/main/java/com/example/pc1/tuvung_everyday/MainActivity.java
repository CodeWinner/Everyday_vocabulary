package com.example.pc1.tuvung_everyday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc1.tuvung_everyday.Adapter.AdapterItemTuVung;
import com.example.pc1.tuvung_everyday.Database.DBTuVung_Everyday;
import com.example.pc1.tuvung_everyday.Model.TuVung;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public static int SIZE_LIST = 0;
    TuVung tuVung;
    public static ArrayList<TuVung> arrTu;
    ListView lvTuVuaNhap;
    AdapterItemTuVung adapterItemTuVung;
    EditText edtTA, edtTV;
    public static TextView txtTitleListTu;
    Button btnAdd, btnDelete, btnStartTime;
    public static DBTuVung_Everyday dataBase;
    public static LinearLayout khungList;
    public static TextToSpeech textToSpeech1;
    public static TextToSpeech textToSpeech2;

    // thong bao
    private NotificationCompat.Builder notBuilder;

    private static final int MY_NOTIFICATION_ID = 12345;

    private static final int MY_REQUEST_CODE = 100;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtTA = (EditText) findViewById(R.id.edtTA);
        edtTV = (EditText) findViewById(R.id.edtTV);
        txtTitleListTu = (TextView) findViewById(R.id.txtTitleList);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnStartTime = (Button) findViewById(R.id.btnStartRun);
        lvTuVuaNhap = (ListView) findViewById(R.id.lvTuVuaNhap);
        khungList = (LinearLayout) findViewById(R.id.khungList);

        try {
            dataBase = new DBTuVung_Everyday(this);
            arrTu = new ArrayList<TuVung>();
            arrTu = dataBase.getListTu();
            if (arrTu.size() == 0) {
                khungList.setVisibility(View.GONE);
            }
            LoadListView();
        } catch (Exception ex) {
            Log.i("EX", ex.toString());
        }

    }


    public static void spkTA(String TA) {
        textToSpeech1.setLanguage(Locale.forLanguageTag("eng"));
        textToSpeech1.speak(TA, TextToSpeech.QUEUE_FLUSH, null);
    }

    public static void spkTV(String TV) {
        textToSpeech2.setLanguage(Locale.forLanguageTag("vni"));
        textToSpeech2.speak(TV, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void LoadListView() {
        txtTitleListTu.setText("Các từ được thêm (" + arrTu.size() + ")");
        adapterItemTuVung = new AdapterItemTuVung(this, R.layout.item_tu_vung, arrTu);
        lvTuVuaNhap.setAdapter(adapterItemTuVung);

    }

    // add 1 tu vung
    private void addTu(String tiengAnh, String tiengViet, String dateStart, String dateKetThuc, String trangThai) {

        tuVung = new TuVung(tiengAnh, tiengViet, dateStart, dateKetThuc, trangThai);
        dataBase.addTuVung(tuVung);
        arrTu = dataBase.getListTu();
        khungList.setVisibility(View.VISIBLE);
        edtTA.setText("");
        edtTV.setText("");
        LoadListView();

    }

    public void Click_Button(View view) {

        switch (view.getId()) {
            case R.id.btnAdd:

                String tiengAnh = edtTA.getText().toString();
                String tiengViet = edtTV.getText().toString();

                Date date = Calendar.getInstance().getTime();
                addTu(tiengAnh, tiengViet, date.toString(), null, "NEW");

                // lvTuVuaNhap.deferNotifyDataSetChanged();
                // txtTitleListTu.setText("Các từ được thêm ("+arrTu.size()+")");
                //  Toast.makeText(getApplicationContext(), "Thanh cong", 100).show();
                break;

            case R.id.btnDelete:
                edtTA.setText("");
                edtTV.setText("");
                break;
            //case R.id.btnStartRun:

             //   break;

        }

    }

    //Xây dựng thông báo
    public  void notification(View view)
    {
        Toast.makeText(this,"Notifivation",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(R.mipmap.ic_launcher, "Call", pIntent)
                .addAction(R.mipmap.ic_launcher, "More", pIntent)
                .addAction(R.mipmap.ic_launcher, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }

    @Override
    protected void onPause() {
        if (textToSpeech2 != null && textToSpeech1 != null) {
            textToSpeech2.stop();
            textToSpeech1.stop();
            textToSpeech2.shutdown();
            textToSpeech1.shutdown();
        }

        super.onPause();
    }
}
