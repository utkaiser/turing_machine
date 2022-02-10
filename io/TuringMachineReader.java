package turingMachine.io;
import turingMachine.TuringMachine;
import turingMachine.TuringTransitionOutput;
import turingMachine.tape.Direction;
import turingMachine.tape.MultiTape;
import turingMachine.tape.MultiTapeReadWriteData;
import turingMachine.tape.Tape;

import java.io.*;

public class TuringMachineReader {


    public static TuringMachine<Character> read(InputStream input){
        //Diese Methode liest eine Turingmaschine aus einem InputStream und erstellt dazu ein TuringMachine<Character>-Objekt.
        return read(new BufferedReader(new InputStreamReader(input)));
    }

    public static TuringMachine<Character> read(Reader input){
        //Diese Methode liest eine Turingmaschine aus einem Reader und erstellt dazu ein TuringMachine<Character>-Objekt.
        return read(new BufferedReader(input));
    }
    public static TuringMachine<Character> read(BufferedReader bf)
            //Hilfmethode
    {
        String baender = "(\\d):\\S+";
        String transition = "\\S\\d,\\S+ -> \\S\\d,\\S+,[RLN]+";
        String tmp = "";
        int index = 0;
        int tapes = 0;
        int pos = 0;
        TuringMachine<Character> tM = null;
        MultiTape<Character> mT = null;
        try {
            while((tmp = bf.readLine()) !=null)
            {//Newline zeichen ersetzen beim leerzeichen entfernen
                if(tmp==null || tmp.trim().isEmpty() || tmp.charAt(0)=='#')
                {
                    continue;
                }
                if(index == 0)
                {
                    tM = new TuringMachine<Character>(Integer.parseInt(tmp));
                    mT = new MultiTape<Character>(Integer.parseInt(tmp));
                }
                if(index == 1)
                {
                    pos = 0;
                    Tape<Character> tape = new Tape();
                    if(!tmp.matches(baender)) {
                        throw new IllegalArgumentException();
                    }
                    pos = Integer.parseInt(tmp.substring(0,tmp.indexOf(":")));
                    tmp = tmp.substring(tmp.indexOf(":")+1);
                    for (int i = 0; i < tmp.length();i++) {
                        tape.write(tmp.charAt(i));
                        tape.move(Direction.RIGHT);
                    }
                    for (int i = tmp.length(); i >= pos;i--) {
                        tape.move(Direction.LEFT);
                    }
                    mT.getTapes().set(tapes,tape);
                    tapes++;
                    if(tapes <  tM.getTapeCount())
                        continue;
                }
                if(index == 2)
                {
                    while(tmp.contains(","))
                    {
                        tM.addState(tmp.substring(0,tmp.indexOf(',')));
                        tmp = tmp.substring(tmp.indexOf(',')+1);

                    }
                    tM.addState(tmp);
                }
                if(index == 3)
                {
                    tM.setCurrentState(tmp);
                }

                if(index == 4)
                {
                    tM.getState(tmp).setAccepted(true);
                }
                if(index > 4)
                {
                    if(!tmp.matches(transition)) {
                        throw new IllegalArgumentException();
                    }
                    int i = 0;
                    String startState = tmp.substring(0,tmp.indexOf(','));
                    tmp = tmp.substring(tmp.indexOf(',')+1);
                    MultiTapeReadWriteData inputD = new MultiTapeReadWriteData(tapes);
                    while(tmp.charAt(i)!=' ')
                    {
                        if(tmp.charAt(i)=='_')
                            inputD.set(i,null);
                        else
                            inputD.set(i,tmp.charAt(i));
                        i++;
                    }
                    tmp = tmp.substring(tmp.indexOf(' ')+1);
                    if (!tmp.contains("->"))
                        throw new IllegalArgumentException();
                    tmp = tmp.substring(tmp.indexOf(' ')+1);
                    String endState = tmp.substring(0,tmp.indexOf(','));
                    tmp = tmp.substring(tmp.indexOf(','));
                    tmp = tmp.substring(tmp.indexOf(',')+1);
                    MultiTapeReadWriteData outputD = new MultiTapeReadWriteData(tapes);
                    i =0;
                    while(tmp.charAt(i)!=',')
                    {
                        if(tmp.charAt(i)=='_')
                            outputD.set(i,null);
                        else
                        outputD.set(i,tmp.charAt(i));
                        i++;
                    }
                    tmp = tmp.substring(tmp.indexOf(',')+1);
                    Direction[] dirs = new Direction[tM.getTapeCount()];
                    i = 0;
                    while(i < tmp.length())
                    {
                        if(tmp.charAt(i)=='R')
                        {
                            dirs[i] = Direction.RIGHT;
                        }
                        if(tmp.charAt(i)=='L')
                        {
                            dirs[i] = Direction.LEFT;
                        }
                        if(tmp.charAt(i)=='N')
                        {
                            dirs[i] = Direction.NON;
                        }
                        i++;
                    }
                    tM.addTransition(startState,inputD,endState,new TuringTransitionOutput<Character>(outputD,dirs));
                }
                index++;
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Eingabe Fehlerhaft" + index);
        }
        tM.setTapes(mT);
        return tM;
    }
    public static void main(String[] args) {
        //Zeigt die Funktionstüchtigkeit dieser Klasse an einem Beispiel.
        String test = "# Anzahl der Baender\n" +
                "2\n" +
                "\n" +
                "# Vorinitalsierung der Baender\n" +
                "1:111111\n" +
                "1:222222\n" +
                "\n" +
                "# Zustaende der Maschine\n" +
                "s1,s2,s3,s4,s5,s6\n" +
                "# Startzustand\n" +
                "s1\n" +
                "# akzeptierte Zustaende\n" +
                "s6\n" +
                "\n" +
                "# Tranistionen\n" +
                "s1,12 -> s2,__,RR\n" +
                "s1,__ -> s6,__,NN\n" +
                "\n" +
                "s2,12 -> s2,12,RR\n" +
                "s2,__ -> s3,__,RR\n" +
                "\n" +
                "s3,__ -> s4,12,LL\n" +
                "s3,12 -> s3,12,RR\n" +
                "\n" +
                "s4,12 -> s4,12,LL\n" +
                "s4,__ -> s5,__,LL\n" +
                "\n" +
                "s5,12 -> s5,12,LL\n" +
                "s5,__ -> s1,12,RR\n" +
                "\n" +
                "s6,__ -> s6,__,NN";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        TuringMachine<Character> Tm = read(reader);
        System.out.println(Tm.getCurrentState());
        System.out.println(Tm.getTapes().toString());
        for (int i = 0; i < 10; i++) {
            Tm.transit();
            System.out.println(Tm.getCurrentState() + "\n" + Tm.getTapes().toString());
        }
        Tm.run();
        System.out.println();
        System.out.println("AcceptedState: "+Tm.isInAcceptedState() +"\n"+ Tm.getTapes().toString());
    }



}
