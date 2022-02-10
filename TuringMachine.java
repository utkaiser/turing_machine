package turingMachine;

import finiteStateMachine.AbstractFiniteStateMachine;
import turingMachine.tape.Direction;
import turingMachine.tape.MultiTape;
import turingMachine.tape.MultiTapeReadWriteData;
import turingMachine.tape.Tape;




public class TuringMachine<T> extends AbstractFiniteStateMachine<MultiTapeReadWriteData<T>, TuringTransitionOutput<T>> {
    private MultiTape<T> mT;
    private int tapeCount;
    public TuringMachine(int tapeCount){
        super();
        this.tapeCount = tapeCount;
        mT = new MultiTape<T>(tapeCount);
    }

    public void run(){
        //Diese Methode führt so lange Zustandsübergänge aus, bis die Maschine einen akzeptierten Zustand erreicht hat oder der Fall eintritt, dass kein Zustandsübergang definiert ist
        while(!isInAcceptedState())
        {
            try {
                transit();
            }catch(IllegalArgumentException e)
            {
                break;
            }
        }
    }
    public void transit(){
        //Führt basierend auf den aktuell von den Bändern gelesenen Daten (und natürlich dem aktuellen Zustand) einen Zustandsübergang aus.
        if(isInAcceptedState())
            throw new IllegalStateException();
        MultiTapeReadWriteData<T> mTRWD = new MultiTapeReadWriteData<T>(tapeCount);
        for (int i = 0; i < tapeCount; i++) {
            mTRWD.set(i,mT.getTapes().get(i).read());
        }

        processOutput(getCurrentState().transit(mTRWD));
    }
    public int getTapeCount(){
        return this.tapeCount;
    }

    public MultiTape<T> getTapes(){
        return mT;
    }

    public void setTapes(MultiTape<T> mT) {
        //Setzt die Tapes
        this.mT = mT;
    }

    public void processOutput(TuringTransitionOutput<T> output){
        // Hier werden zunächst die neuen Daten auf die Bänder geschrieben und anschließend die Lese-/Schreibköpfe in die entsprechenden Richtungen bewegt.
        for (int i = 0; i < tapeCount;i++) {
            mT.getTapes().get(i).write(output.getToWrite().get(i));
            mT.getTapes().get(i).move(output.getDirections()[i]);
        }
    }
    public static void main(String[] args) {
        //Zeigt die Funktionstüchtigkeit dieser Klasse an einem Beispiel.
        TuringMachine<Character> tMtes = new TuringMachine<>(1);
        Tape<Character> ttest = new Tape<Character>();
        MultiTape<Character> mTtest = new MultiTape<Character>(1);
        MultiTapeReadWriteData<Character> mTRWDtest = new MultiTapeReadWriteData<Character>(1);

        ttest.write('c');
        ttest.move(Direction.RIGHT);
        ttest.write('a');
        ttest.move(Direction.RIGHT);
        ttest.write('b');
        ttest.move(Direction.LEFT);

        mTtest.getTapes().set(0,ttest);
        tMtes.setTapes(mTtest);

        System.out.println("Tape1:\n"+ ttest.toString());
        tMtes.addState("s1");
        tMtes.addState("s2");
        tMtes.addState("s3");
        tMtes.setCurrentState("s1");
        tMtes.getState("s3").setAccepted(true);

        MultiTapeReadWriteData<Character> mTRWDIn = new MultiTapeReadWriteData<>(1);
        MultiTapeReadWriteData<Character> mTRWDout = new MultiTapeReadWriteData<>(1);
        Direction[] dir = {Direction.RIGHT};
        mTRWDIn.set(0,'a');
        mTRWDout.set(0,'d');
        tMtes.addTransition("s1",mTRWDIn,"s2",new TuringTransitionOutput<Character>(mTRWDout,Direction.RIGHT));
        dir[0] = Direction.LEFT;
        MultiTapeReadWriteData<Character> mTRWDIn1 = new MultiTapeReadWriteData<>(1);
        MultiTapeReadWriteData<Character> mTRWDout1 = new MultiTapeReadWriteData<>(1);
        mTRWDIn1.set(0,'b');
        mTRWDout1.set(0,null);
        tMtes.addTransition("s2",mTRWDIn1,"s2",new TuringTransitionOutput<Character>(mTRWDout1,dir));

        MultiTapeReadWriteData<Character> mTRWDIn2 = new MultiTapeReadWriteData<>(1);
        MultiTapeReadWriteData<Character> mTRWDout2 = new MultiTapeReadWriteData<>(1);
        mTRWDIn2.set(0,'d');
        mTRWDout2.set(0,null);
        tMtes.addTransition("s2",mTRWDIn2,"s3",new TuringTransitionOutput<Character>(mTRWDout2,dir));
        System.out.println("CurrentState: "+ tMtes.getCurrentState());
        System.out.println("Current Value in Tape: " + ttest.getCurrent());
        tMtes.run();
        System.out.println("Endstate: "+tMtes.getCurrentState().toString());





    }

}
