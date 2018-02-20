package com.hastatakip.doktor.doktorapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class HastaActivity extends ActionBarActivity {

    int DoktorID,HastaID;
    ProgressDialog pd;
    Button btn_hastaeklegoster;
    ListView listview_hastalar;
    static List<Hasta> hastalar;
    AdapterListViewHastalar ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta);
        btn_hastaeklegoster=(Button)findViewById(R.id.button_HastaEkleGoster);
        listview_hastalar=(ListView)findViewById(R.id.listView_Hastalar);
        DoktorID=getIntent().getExtras().getInt("DoktorID");
        //Hastaları yukleme
        new AsyncTaskClass().execute();

       listview_hastalar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Hasta h=(Hasta)ad.getItem(position);
               HastaKontrolEklePencereAc(h.ID,h.Ad_Soyad,h.DoktorID);
           }

       });
    }
    public void HastaSil(Hasta h)
    {
       // final Hasta h = (Hasta) ad.getItem(position);
        final AlertDialog.Builder builder = new AlertDialog.Builder(HastaActivity.this);
        builder.setTitle("Hasta Silme");
        builder.setMessage(h.Ad_Soyad + " : Hastayı Silmek Emin misiniz");
        builder.setCancelable(false);

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                new AsyncTaskClass().execute();
                Toast.makeText(HastaActivity.this, "Hasta Silindi.", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void HastaKontrolEklePencereAc(final int hastaid,final String Ad, final int doktorid)
    {
        HastaID=hastaid;
        final Dialog d = new Dialog(HastaActivity.this);
        //Yeni pencere oluşturmak
        Window window = d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        d.setTitle("Kontrol Ekle");
        d.setContentView(R.layout.kontrolekle);
        TextView tv=(TextView)d.findViewById(R.id.textView_HastaBilgileri);
        tv.setText(Ad);
        Button btn_kontrolekle=(Button)d.findViewById(R.id.button4);
        final RadioGroup rg=(RadioGroup)d.findViewById(R.id.radioGroup);
        RadioButton rb_tansiyon=(RadioButton)d.findViewById(R.id.radioButton);
        RadioButton rb_seker=(RadioButton)d.findViewById(R.id.radioButton2);
        btn_kontrolekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kontrol k=new Kontrol();
                int rb_id=rg.getCheckedRadioButtonId();
                switch (rb_id) {
                    case R.id.radioButton:{k.Turu=0;break;}
                    case R.id.radioButton2:{k.Turu=1;break;}
                }
                k.HastaID=HastaID;
                k.DoktorID=DoktorID;
                Doktor.KontrolEkle(k);
                d.dismiss();
                //mesaj
                Toast.makeText(getApplicationContext(),"Eklendi !!",Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
    }
    public void HastaEklePencereAc(View v)
    {
        final Dialog d = new Dialog(HastaActivity.this);
        //Yeni pencere oluşturmak
        Window window = d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        d.setTitle("Yeni Hasta Ekle");
        d.setContentView(R.layout.hastaekle);
        Button btn_hastaekle=(Button)d.findViewById(R.id.button_ekle);
        btn_hastaekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ekleme kodları
                EditText tc=(EditText)d.findViewById(R.id.editText_TC);
                Hasta yeni =new Doktor();
                yeni.TC=tc.getText().toString();
                Doktor.HastaEkle(yeni,DoktorID);
                //pencereyi kapatır.
                d.dismiss();

                //mesaj
                Toast.makeText(getApplicationContext(),"Eklendi !!",Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
    }

    public static void HastalariYukle()
    {
        hastalar=Hasta.GetAll();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hasta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Hastaları yukleme
    public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;

        //AdapterMarka adapmarka;
        @Override
        protected void onPreExecute() {
            //uzun islem oncesi yapilacaklar
            pd = new ProgressDialog(HastaActivity.this);
            pd.setMessage("Veriler Yukleniyor...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
                hastalar=Hasta.GetHastalarByDoktor(DoktorID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
            ad=new AdapterListViewHastalar(HastaActivity.this,hastalar);
            listview_hastalar.setAdapter(ad);
            pd.dismiss();
        }
    }
}
