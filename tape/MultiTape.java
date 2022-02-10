package turingMachine.tape;

import java.util.ArrayList;
import java.util.List;

public class MultiTape<T> {
    private int tapeCount;
    private ArrayList<Tape<T>> tapes;
    public MultiTape(int tapeCount){
        this.tapeCount = tapeCount;
        tapes = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++)
            tapes.add(new Tape<T>());
    }

    public MultiTapeReadWriteData<T> read(){
        //Liest die Werte unter allen Köpfen der einzelnen Bänder und gibt diese zurück.
        MultiTapeReadWriteData<T> result = new MultiTapeReadWriteData<T>(tapeCount);
        for (int i = 0; i < tapeCount; i++) {
            result.set(i,tapes.get(i).read());
        }
        return result;
    }
    public void write(MultiTapeReadWriteData<T> values){
        //Schreibt die Werte in values auf die einzelnen Bänder.
        if(values.getLength()!=tapeCount)
            throw new IllegalArgumentException();
        for (int i = 0; i < tapeCount; i++) {
            tapes.get(i).write(values.get(i));
        }
    }
    public void move(Direction[] directions){
        //Bewegt die Lese-/Schreibköpfe der einzelnen Bänder.
        if(directions.length != tapes.size())
            throw new IllegalArgumentException();
        for (int i = 0; i < tapeCount; i++) {
            tapes.get(i).move(directions[i]);
        }

    }
    public List<Tape<T>> getTapes(){
        return tapes;
    }
    public int getTapeCount(){
        return this.tapeCount;
    }
    public String toString(){
        String result = "";
        for (int i = 0; i < tapeCount; i++) {
            result+=tapes.get(i).toString()+"\n";
        }
        return result;
    }

}
