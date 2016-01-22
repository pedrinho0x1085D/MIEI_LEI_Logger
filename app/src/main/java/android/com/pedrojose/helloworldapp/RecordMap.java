package android.com.pedrojose.helloworldapp;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by PedroJosé on 01/01/2016.
 */
public class RecordMap {
    private TreeMap<GregorianCalendar,Record> mapa;
    public RecordMap(){
        this.mapa=new TreeMap<>();
    }
    public TreeMap getMap(){
        return mapa;
    }
    public void setMapa(TreeMap<GregorianCalendar,Record> mapa){
        this.mapa=mapa;
    }

    public void addRecord(Record rec){
        this.mapa.put(rec.getDate(),rec);
    }

    public Record getFirstRecord(){
        TreeSet<GregorianCalendar> tree=new TreeSet<>();
        for(GregorianCalendar d: this.mapa.keySet())
            tree.add(d);
        return this.mapa.get(tree.first());
    }

    public Record getFinishRecord(){
        TreeSet<GregorianCalendar> tree=new TreeSet<>();
        for(GregorianCalendar d: this.mapa.keySet())
            tree.add(d);
        return this.mapa.get(tree.last());
    }

    // to csv file!
    public ArrayList<String> CSVFormat(){
        ArrayList<String> lines = new ArrayList<>();
        String header="username, datetime, age, height, weight, hasSportHistoric, hasWalkingHistoric, gender, startLat, startLon, startAlt, endLat, endLon, endAlt, dist, altdiff, currSpd, avgSpd, accumSub, accumDesc, modal, load, diffic"+"\n";
        lines.add(header);
        for(Record r: this.mapa.values())
            lines.add(r.formatCSV());
        return lines;
    }

}
