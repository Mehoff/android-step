package com.step.androidstep.classes;

import android.os.Debug;
import android.widget.Button;
import android.widget.TextView;

import com.step.androidstep.enums.Action;
import com.step.androidstep.statics.ArrayHelper;

import java.util.ArrayList;

public class Calculator {
    private final int MAX_NUMBER_SIZE = 9;
    private TextView calcExpressionView;
    private TextView calcResultView;
    private String expression;
    private ArrayList<Action> actionHistory;

    public Calculator(TextView _calcExpressionView, TextView _calcResultView){
        expression = "0";
        calcExpressionView = _calcExpressionView;
        calcResultView = _calcResultView;
        actionHistory = new ArrayList<>();
    }

    public void updateCalcExpressionUI(){
        calcExpressionView.setText(expression);
    }

    public void addActionButton(Button button, Action action){

        try{
            button.setOnClickListener((v) -> {
                if(actionHistory.size() <= 0) return;

                Action prevAction = actionHistory.get(actionHistory.size() - 1);

                if(isWritable(action)){
                    if(isWritable(prevAction)){
                        String actionString = getActionString(action);
                        StringBuilder sb = new StringBuilder(expression);
                        assert actionString != null;
                        char c = actionString.toCharArray()[0];
                        if(expression.length() <= 0) return;
                        sb.setCharAt(expression.length() - 1, c);
                        expression = sb.toString();
                    } else {
                        expression += getActionString(action);
                    }
                } else {
                    handleAction(action);
                }
                updateCalcExpressionUI();
                actionHistory.add(action);
            });
        }
        catch (Exception ex){
            System.out.println("<!ERR> addActionButton()\n" + ex.getMessage());
        }
    }

    public void addNumberButton(Button button, int number){
        try{
            button.setOnClickListener((v) -> {
                if(expression.equals("0") || (number == 0 && expression.equals("0"))){
                    expression = "";
                }
                expression += number;
                updateCalcExpressionUI();
                actionHistory.add(Action.NUMBER);
            });
        }
        catch (Exception ex){
            System.out.println("<!ERR> addNumberButton()\n" + ex.getMessage());
        }
    }

    // ОЧ плохо, нужно переделать с более высоким уровнем абстракции
    private boolean isWritable(Action action){
        return (action == Action.PLUS || action == Action.MINUS || action == Action.ASTERISK || action == Action.MULTIPLY || action == Action.DIVIDE);
    }

    private void handleAction(Action action) {
        // todo: handle action

        switch (action){
            case CLEAR: clear();
                break;
            case EQUALS: equals();
                break;
            case ERASE: erase();
                break;
            default: return;
        }
        actionHistory.add(action);
    }

    private String getActionString(Action action){
        switch (action){
            case PLUS: return "+";
            case MINUS: return "-";
            case DIVIDE: return "/";
            case MULTIPLY: return "*";
            default: return null;
        }
    }

    private void clear(){

    }

    private void equals(){

    }

    private void erase(){
        if(expression.length() <= 0 || expression.length() == 1 || expression.equals("0")){
            expression = "0";
            return;
        }

        expression = expression.substring(0, expression.length() - 1);
        updateCalcExpressionUI();
    }
}
