package com.example.roadmanagement.kaltara;

import android.content.Intent;
import android.os.Handler;

import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.RoadView.RoadViewIndex;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.Loginku;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.api.Apiku;
import com.example.roadmanagement.kaltara.api.InterfaceKaltara;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    String usnm;
    String psw;
    CardView submit;
    Retrofit retrofit;
    Apiku api;
    Session session;
    RelativeLayout relativeLayout;
    CardView cardView;
    final Handler handler_interact=new Handler();//not defined as final variable. may cause problem
    int images[] = new int[]{R.drawable.background1, R.drawable.background2, R.drawable.background3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        submit = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        cardView = findViewById(R.id.cardLoginku);

        api = new Apiku(this);
        relativeLayout = findViewById(R.id.ubahbackground);
        retrofit = api.initRetrofit();

        session = new Session(this);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    password.clearFocus();
                }
                return false;
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String > params = new HashMap<>();
                usnm = username.getText().toString();
                psw = password.getText().toString();

                //params.put("username", username.getText().toString());
                //params.put("password", password.getText().toString());
                login(usnm, psw);
            }
        });

        if(session.getValueLogin()){

            Intent intent;


            /*if(session.getUserTipe()==99){

                intent = new Intent(LoginActivity.this, RoadViewIndex.class);

            }else if(session.getSurvey()==1){
                intent = new Intent(LoginActivity.this, FormUtama.class);
            }else {
                intent = new Intent(LoginActivity.this, Menuutama.class);
            }*/

            if(session.getSurvey()==1){
                intent = new Intent(LoginActivity.this, FormTabUtama.class);
            }else {
                intent = new Intent(LoginActivity.this, Menuutama.class);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


      //session.saveSPString(Session.USER, null);

        Timer timer_interact=new Timer();


           timer_interact.schedule(new TimerTask() {
               @Override
               public void run() {
                   UpdateGUI();
               }
           }, 0,5000);
    }


    private void UpdateGUI() {
        handler_interact.post(runnable_interact);
    }
    //creating runnable
    final Runnable runnable_interact = new Runnable() {
        public void run() {
            int a =  new Random().nextInt(3);
            relativeLayout.setBackgroundResource(images[a]);//this is the line which makes the app Force close.
        }
    };




    private void login(final String username, final String password) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Loginku> result = apiService.getLogin(username, password);
        result.enqueue(new Callback<Loginku>() {
            @Override
            public void onResponse(Call<Loginku> call, Response<Loginku> response) {
                try {
                    if (response.body() != null) {
                       if(response.body().getError().equals("FALSE")) {
                          // Toast.makeText(LoginActivity.this, response.body().getLogins().get(0).getKodeprov(), Toast.LENGTH_SHORT).show();
                           session.saveSPString(Session.SP_KODEPROV, response.body().getLogins().getKodeprov());
                           session.saveSPString(Session.USER, response.body().getLogins().getUsername());
                           session.saveSPInt(Session.TIPEUSER, response.body().getLogins().getKodelogin());
                           session.saveSPBoolean(Session.LOGIN, true);

                           //Toast.makeText(getApplicationContext(), String.valueOf(response.body().getLogins().getKodelogin()), Toast.LENGTH_SHORT).show();

                           Intent signin;

                           /*if(response.body().getLogins().getKodelogin()==99) {
                               signin = new Intent(LoginActivity.this, RoadViewIndex.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                           }else{
                               signin = new Intent(LoginActivity.this, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                           }*/
                           signin = new Intent(LoginActivity.this, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(signin);
                           finish();

                       }else {
                           Snackbar.make(getWindow().getDecorView().getRootView(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                   .setAction("Action", null).show();
                             }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Loginku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
}
