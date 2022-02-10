package turingMachine;

import turingMachine.tape.Direction;
import turingMachine.tape.MultiTapeReadWriteData;

public class TuringTransitionOutput<T> {
    private MultiTapeReadWriteData<T> toWrite;
    private Direction[] directions;
    public MultiTapeReadWriteData<T> getToWrite(){
        //Liefert die zu schreibenden Daten.
        return toWrite;
    }
    public Direction[] getDirections(){
        // Gibt die Bewegungsrichtungen für die einzelnen Köpfe an
        return directions;
    }

    public TuringTransitionOutput(MultiTapeReadWriteData<T> toWrite,Direction...directions){
        //Eine Transitionsausgabe soll durch diesen Konstruktor erstellt werden können.
        if(toWrite.getLength() != directions.length)
            throw new IllegalArgumentException();
        this.toWrite=toWrite;
        this.directions=directions;
    }
    public String toString()
    {
        String result;
        result = getToWrite().toString() + "Directions: ";
        for (Direction d:getDirections()) {
            result += d +" ";
        }
        return result;
    }

}
