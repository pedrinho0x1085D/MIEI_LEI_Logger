package android.com.pedrojose.helloworldapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LoggingAct extends Activity {
    User u;
    int carga;
    String modal;
    ThreadLogging tl;
    RecordMap mapa=new RecordMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getIntent().getExtras();
        this.u=(User)(b.get("user"));
        this.modal=b.getString("modal");
        this.carga=b.getInt("carga");
        setContentView(R.layout.activity_logging);
        TextView tv=(TextView)findViewById(R.id.textView10);
        tv.setText(modal);
    }

    public void endLogging(View v) {
        tl.removeRequest();

        ArrayList<String> data=mapa.CSVFormat();
        try {
            LoggingAct.this.writeToCSV(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent=new Intent(LoggingAct.this,PreModalidade.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    public void startLogging(View v) {
        Button terminar=(Button)findViewById(R.id.button5);
        terminar.setClickable(true);
        ((Button)findViewById(R.id.button4)).setClickable(false);
        tl=new ThreadLogging(u,carga,modal,this,mapa);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public void writeToCSV(ArrayList<String> list) throws IOException {
        if (isExternalStorageWritable()) {
            File folder = new File(Environment.getExternalStorageDirectory()
                    +File.separator +"dadosLogging");
            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();
            final String filename = folder.toString() + File.separator + this.mapa.getFirstRecord().simpleTextDateTime() + this.mapa.getFirstRecord().getUser().getName().replaceAll(" ", "") + ".csv";
            FileWriter fw = new FileWriter(filename);
            for (String str : list)
                fw.write(str);
            fw.flush();
            fw.close();
        }
        else {

        }
    }
    public void updateLatLonAl(double lat, double lon, double al){
        TextView tv1=(TextView)findViewById(R.id.textView13);
        TextView tv2=(TextView)findViewById(R.id.textView15);
        TextView tv3=(TextView)findViewById(R.id.textView17);
        tv1.setText(lat + "");
        tv2.setText(lon + "");
        tv3.setText(al + "");
    }

    public String getDiffic(){
        RadioButton rb1=(RadioButton)findViewById(R.id.radioButton3);
        RadioButton rb2=(RadioButton)findViewById(R.id.radioButton4);
        RadioButton rb3=(RadioButton)findViewById(R.id.radioButton5);
        if(rb1.isChecked()) return "easy";
        else if(rb2.isChecked()) return "medium";
        else return "hard";
    }
}
