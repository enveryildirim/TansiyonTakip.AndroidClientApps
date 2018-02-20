package com.hastatakip.doktor.doktorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Map;


public class LoginActivity extends ActionBarActivity {
    ProgressDialog pd;
    Button btn_giris;
    EditText tc,sifre;
    TextView durum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        tc=(EditText)findViewById(R.id.editText_TC);
        sifre=(EditText)findViewById(R.id.editText_Sifre);
        durum=(TextView)findViewById(R.id.textView_Durum);
        btn_giris=(Button)findViewById(R.id.button_Giris);

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(tc.getText().length()>0&&sifre.getText().length()>0)
                    {
                        pd = new ProgressDialog(LoginActivity.this);
                        pd.setMessage("Giriş Yapılıyor..");
                        pd.show();
                        Map<String,Object> degerler=new Hashtable<String, Object>();
                        degerler.put("tc",tc.getText().toString());
                        degerler.put("sifre",sifre.getText().toString());
                        String sonuc="";
                        sonuc=WS.İstek("WS_Doktor.svc","Login","IWS_Doktor",degerler);
                        pd.dismiss();
                        if(!sonuc.equalsIgnoreCase("0"))
                        {
                            Intent i =new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("DoktorID",Integer.parseInt(sonuc));
                            startActivity(i);
                        }
                        else
                            durum.setText("Yanlış Bilgi");
                    }
                    else
                        durum.setText("Boş Alan Bırakmayınız");
                }catch(Exception e){

                    e.getStackTrace();
                }



            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

}
