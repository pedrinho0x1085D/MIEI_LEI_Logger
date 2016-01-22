package android.com.pedrojose.helloworldapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
    User us;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        us=new User();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        final Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText mIdade=(EditText) findViewById(R.id.editText);
                EditText mAltura=(EditText) findViewById(R.id.editText2);
                EditText mPeso=(EditText) findViewById(R.id.editText3);
                EditText mNome=(EditText) findViewById(R.id.editText4);
                CheckBox mHSport=(CheckBox) findViewById(R.id.checkBox);
                CheckBox mHWalking=(CheckBox) findViewById(R.id.checkBox2);
                RadioButton male=(RadioButton) findViewById(R.id.radioButton);
                RadioButton female=(RadioButton) findViewById(R.id.radioButton2);
                if(mIdade.getText().toString().isEmpty()||mAltura.getText().toString().isEmpty()||mPeso.getText().toString().isEmpty()||mNome.getText().toString().isEmpty()){
                    TextView tv=(TextView) findViewById(R.id.textView6);
                    tv.setVisibility(View.VISIBLE);
                    TextView tv2=(TextView) findViewById(R.id.textView7);
                    tv2.setVisibility(View.INVISIBLE);
                }
                else{
                    TextView tv=(TextView) findViewById(R.id.textView6);
                    tv.setVisibility(View.INVISIBLE);
                    int idade,altura,peso;
                    boolean hSport,hWalking;
                    char gender;
                    String nome;
                    idade=Integer.parseInt(mIdade.getText().toString());
                    altura=Integer.parseInt(mAltura.getText().toString());
                    peso=Integer.parseInt(mPeso.getText().toString());
                    nome=mNome.getText().toString();
                    hSport=mHSport.isChecked();
                    hWalking=mHWalking.isChecked();
                    if(male.isChecked())
                        gender='M';
                    else gender='F';
                    us=new User(nome,idade,peso,altura,hSport,hWalking,gender);
                    TextView tv2=(TextView) findViewById(R.id.textView7);
                    tv2.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(MainActivity.this,PreModalidade.class);
                    intent.putExtra("user",us);
                    startActivity(intent);
                    finish();
                }
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
