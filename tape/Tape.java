package turingMachine.tape;

import com.sun.javafx.UnmodifiableArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tape<T> {
    private ArrayList<T> fields;
    private int index;
    private ArrayList<TapeChangeListener<T>> listener;
    public Tape(){
        fields = new ArrayList<>();
        fields.add(null);
        index = 0;
        listener = new ArrayList<TapeChangeListener<T>>();
    }

    public void move(Direction direction){
        //
        if(direction.equals(Direction.LEFT)) {
            if (index == 0) {
                fields.add(0, null);
                for (TapeChangeListener listen: listener) {
                    listen.onExpand(direction);
                }
            } else {
                index--;
            }
        }
        else if(direction.equals(Direction.RIGHT)){
            if (index == fields.size()-1) {
                fields.add(null);
                index++;
                for (TapeChangeListener listen: listener) {
                    listen.onExpand(direction);
                }
            } else {
                index++;

            }
        }
        for (TapeChangeListener listen: listener) {
            listen.onMove(direction);
        }
    }

    public T read(){
        //Gibt das aktuelle Element unter dem Kopf zurück.
        return fields.get(index);
    }

    public List<T> getContents(){
        //Gibt den Inhalt des Bandes inklusive leerer Zellen an Anfang und Ende zurück.
        final ArrayList<T> result = new ArrayList<>(fields);
        return Collections.unmodifiableList(result); //soll nicht modifizierbar sein
        //return result
    }
    public int getPosition(){
        return index;
    }
    public void write(T content){
        //Schreibt den Inhalt von content an die aktuelle Stelle des Kopfes.
        fields.set(index,content);
        for (TapeChangeListener listen: listener) {
            listen.onWrite(content);
        }
    }
    public void addListener(TapeChangeListener<T> listener){
        //Fügt einen neuen TapeChangeListener zu den zu informierenden Listenern hinzu
        this.listener.add(listener);

    }
    public String getCurrent(){
        //Gibt das aktuelle Element unter dem Kopf als String zurück
        if(fields.get(index)!= null)
            return fields.get(index).toString();
        else
            return "_";
    }
    public String getLeft(){
        //Gibt den Bandinhalt links von der aktuellen Position als String zurück
        String result = "";
        for(int i = 0; i <= index; i++)
        {
            if(fields.get(i)!= null)
                result += fields.get(i).toString();
            else
            {
                result += "_";
            }
        }
        if(result.length() > 0)
        {
            int startIndex = 0;
            while(startIndex < result.length()-1 && result.charAt(startIndex) == '_' )
            {
                startIndex++;
            }
            return result.substring(startIndex,result.length()-1);
        }
        else
            return result;
    }
    public String getRight(){
        //Gibt den Bandinhalt rechts von der aktuellen Position als String zurück
        String result = "";
        for(int i = index +1; i < fields.size(); i++)
        {
            if(fields.get(i)!= null)
                result += fields.get(i).toString();
            else
            {
                result += "_";
            }
        }
        if(result.length() > 0)
        {
            int endIndex = result.length();
            while(endIndex > 0 && result.charAt(endIndex-1) == '_')
            {
                endIndex--;
            }
            return result.substring(0,endIndex);
        }
        else
            return result;
    }
    public String toString(){
        String result = "";
        for (T t:fields) {
            if(t != null)
                result += t.toString();
            else
            {
                result += "_";
            }
        }
        int startIndex = 0;
        int endIndex = result.length();
        while(startIndex < index && result.charAt(startIndex) == '_' )
        {
            startIndex++;
        }
        while(endIndex > index+1 && result.charAt(endIndex-1) == '_')
        {
            endIndex--;
        }
        result = result.substring(startIndex,endIndex);
        if(result.length() == 0)
            result="_";
        result +="\n";
        for(int i = 0; i < index-startIndex;i++)
        {
            result += " ";
        }
        result += "^";
        return result;
    }

}
