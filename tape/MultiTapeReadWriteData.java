package turingMachine.tape;

import java.util.ArrayList;
import java.util.List;

public class MultiTapeReadWriteData<T> {
    private List<T> values;
    private int length;
    public MultiTapeReadWriteData(int length){
        this.length = length;
        values = new ArrayList<T>(length);
        for (int i = 0; i < length;i++) {
            values.add(i,null);
        }
        if(values.size()!= length)
            throw new IllegalArgumentException();
    }

    public T get(int i){
        //Liefert den Wert, der für den Inhalt der Zelle unter dem Lese-/Schreibkopf des i-ten Bandes steht.
        if(i>= length || i < 0){
            throw new IndexOutOfBoundsException();
        }
        return values.get(i);
    }
    public void set(int i, T value){
        //Setzt den Wert, der für den Inhalt der Zelle unter dem Lese-/Schreibkopf des i-ten Bandes steht.
        if(i>=length || i < 0){
            throw new IndexOutOfBoundsException();
        }
        values.set(i,value);

    }
    public int getLength(){
     return this.length;
    }
    public String toString(){
        String result="";
        for(int i=0;i<getLength();i++){
            if(get(i) == null){
                result+="_";
            }else {
                result += get(i).toString();
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if(this.hashCode() != o.hashCode()){
            return false;
        }
        MultiTapeReadWriteData<?> that = (MultiTapeReadWriteData<?>) o;
        if(that.toString().equals(o.toString())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return values.toString().hashCode();
    }
}
