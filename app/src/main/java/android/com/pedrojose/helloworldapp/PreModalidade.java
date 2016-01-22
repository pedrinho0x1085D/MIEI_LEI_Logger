package android.com.pedrojose.helloworldapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PreModalidade extends Activity {
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getIntent().getExtras();
        this.u=(User)b.get("user");
        setContentView(R.layout.activity_pre_modalidade);
        TextView tv=(TextView)findViewById(R.id.textView9);
        tv.append(u.getName());
    }
    public void startWalk(View v) {
        int carga;
        EditText et=(EditText)findViewById(R.id.editText5);
        carga=Integer.parseInt((et.getText().toString()));
        Intent intent=new Intent(PreModalidade.this,LoggingAct.class);
        intent.putExtra("user",this.u);
        intent.putExtra("modal", "Caminhada");
        intent.putExtra("carga", carga);
        startActivity(intent);
        finish();
    }

    public void startRace(View v) {
        int carga;
        EditText et=(EditText)findViewById(R.id.editText5);
        carga=Integer.parseInt((et.getText().toString()));
        Intent intent=new Intent(PreModalidade.this,LoggingAct.class);
        intent.putExtra("user",this.u);
        intent.putExtra("modal","Corrida");
        intent.putExtra("carga",carga);
        startActivity(intent);
        finish();
    }
}
