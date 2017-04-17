package com.myapp.example.aulasharedpreferencesexample1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bt_01;
    TextView tv;
    LinearLayout layout;
    SharedPreferences sharedPreferences;


    //instancia um listener para ouvir mudanças na Shared Preference
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
            SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Log.d("MAIN","onSharedPreferenceChanged: " + key);
                    setUsersPreferences();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // carrega as preferências padrão de usuário
        //Tudo o que é setado em SharedPreferences o Android trata de armazenar e compartilhar com a MainActivity.
        //Além disso o Android armazenas estas configurações em disco e as deixa sempre disponível
        //através de uma instância de SharedPreferences na próxima vez que reinicializar seu App.
        //Basta passar o this de sua classe para o Android que ele recupera o SharedPreference p/ você.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Obtém referência para o botão e seta o Listener
        bt_01 = (Button)findViewById(R.id.bt_01_view);
        bt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PrefActivity.class);
                //startActivity(it);
                startActivityForResult(it, 1);
            }
        });
        //Obtém referência para a TextView
        tv = (TextView) findViewById(R.id.tv_color_view);
        //Obtém referência para o Layout
        layout = (LinearLayout) findViewById(R.id.main_layout);
        //Exibe as preferências de usuário desde o último uso
        setUsersPreferences();


        //registra o Listener para mudanças na SharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(spChanged);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            //exibe as preferências de usuário recém alteradas
            //setUsersPreferences();
        }
    }

    /*
    setLayoutBackgroudColor: Altera a cor de fundo do Layout de acordo com a cor passada como parâmetro
     */
    public void setLayoutBackgroudColor(String cor){
        switch(cor){
            case "azul":
                layout.setBackgroundColor(getResources().getColor(R.color.azul));
                break;
            case "vermelho":
                layout.setBackgroundColor(getResources().getColor(R.color.vermelho));
                break;
            case "verde":
                layout.setBackgroundColor(getResources().getColor(R.color.verde));
                break;
            case "amarelo":
                layout.setBackgroundColor(getResources().getColor(R.color.amarelo));
                break;
            case "preto":
                layout.setBackgroundColor(getResources().getColor(R.color.preto));
                break;
            case "branco":
                layout.setBackgroundColor(getResources().getColor(R.color.branco));
                break;
        }

    }

    /*
    setUsersPreferences - seta as configurações Preferenciais do usuário no App
     */
    public void setUsersPreferences(){
        Log.d("MAIN", "setUsersPreferences" );
        //Lembre-se que SharedPreferences armazena valores em bundle, uma tupla (key, valor) é usada
        //para recuperar os dados. Temos de usar a mesma key lá do arquivo xml de preferencias, no nosso caso
        //preferences.xml
        //seta a cor de fundo do Layout
        setLayoutBackgroudColor(sharedPreferences.getString("listchoice_01_view","white"));
        //recupera a preferência para cor
        String userTxtPreferences = "Cor: " + sharedPreferences.getString("listchoice_01_view","white");
        //recupera a preferência para nome
        userTxtPreferences = userTxtPreferences + "\n" + "Nome: " + sharedPreferences.getString("nameChoice_01_view","Luis");
        //recuera a preferência para Binary choice
        userTxtPreferences = userTxtPreferences + "\n" + "Binary: " + sharedPreferences.getBoolean("booleanchoice_01_view",true);
        //recupera o preferência para Notificatios
        userTxtPreferences = userTxtPreferences + "\n" + "Notifications: " + sharedPreferences.getBoolean("switch_01_view",true);

        tv.setText(userTxtPreferences);
    }
}
