package finiteStateMachine;

import finiteStateMachine.state.State;
import finiteStateMachine.state.Transition;

import java.util.ArrayList;
import java.util.Collection;

public abstract class  AbstractFiniteStateMachine<I,O>{

    private ArrayList <State<I,O>> stateSammlung;
    private State<I,O> currentState;
    public AbstractFiniteStateMachine(){
        stateSammlung = new ArrayList<State<I,O>>();
    }

    public void addState(String state){
        //Legt in der Maschine einen neuen Zustand mit dem Namen state an.
        if(stateSammlung.contains(new State(this,state))){
            throw new IllegalStateException("This state already exists");
        }else{
            stateSammlung.add(new State(this,state));
        }

    }
    public State<I,O> getState(String state){
        //Liefert den Zustand mit dem Namen state zurück.
        if(!stateSammlung.contains(new State(this,state))){
            throw new IllegalStateException("This state doesnt exist");
        }
        int index = stateSammlung.lastIndexOf(new State(this,state));
        return stateSammlung.get(index);
    }
    public void addTransition(String startState, I input, String targetState, O output){
        //Fügt einen Übergang von startState nach targetState bei Eingabe input mit der Ausgabe output hinzu. Der Übergang selbst soll im Zustand mit dem Namen startState gespeichert werden.
        getState(startState);
        getState(targetState);
        getState(startState).addTransition(input,targetState,output);
    }
    public void setCurrentState(String state){
        // etzt den aktuellen Status des Automaten auf den Zustand mit dem Namen state.
        State<I,O> currentState = getState(state);
        this.currentState = currentState;
    }
    public State<I,O> getCurrentState(){
        //Liefert den aktuellen Zustand zurück.
        return currentState;
    }

    public boolean isInAcceptedState(){
        //Gibt an, ob der aktuelle Zustand des Automaten akzeptiert ist
        if(currentState == null)
            return false;
        else
            return currentState.isAccepted();
    }
    public void transit(I input){
        //Führt einen Übergang vom aktuellen Zustand mit der Eingabe input aus.
        if(currentState == null)
            throw new IllegalStateException("Kein zustand gesetzt");
        processOutput(currentState.transit(input));
    }
    public Transition<O> getTransition(I input){
        //Liefert den Übergang zurück, der bei aktuellem Zustand mit der Eingabe input ausgeführt werden würde.
        if(currentState == null)
            throw new IllegalStateException("Kein zustand gesetzt");
        return currentState.getTransition(input);
    }
    protected abstract void processOutput(O output);
}
