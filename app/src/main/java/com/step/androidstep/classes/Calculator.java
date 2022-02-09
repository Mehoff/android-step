package com.step.androidstep.classes;

import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.step.androidstep.enums.Action;
import com.step.androidstep.statics.ArrayHelper;

import java.util.ArrayList;

public class Calculator {
    private final String TAG = "Calc";
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

    private Action getActionFromChar(char c){
        switch (c){
            case '+': return Action.PLUS;
            case '-': return Action.MINUS;
            case '/': return  Action.DIVIDE;
            case '*': return  Action.MULTIPLY;
            case '.': return Action.ASTERISK;
            default: return null;
        }
    }

    private void clear(){
        expression = "0";
        actionHistory.clear();
    }

    private void equals(){
//        if(!isWritable( actionHistory.get(actionHistory.size() - 2) )){
//            Log.i(TAG, "Последний экшн не число, ничего не делаем");
//            return;
//        }

        boolean isFirstArg = true;
        Action action = Action.EQUALS;
        String arg1_str = "";
        String arg2_str = "";
        for(int i = 0; i < expression.length(); i++){
            boolean isDigit = Character.isDigit(expression.charAt(i));

            // todo: ignore "."
            if(isDigit){
                if(isFirstArg){
                    arg1_str += expression.charAt(i);
                    Log.i(TAG, "arg1_str + " + expression.charAt(i));
                } else {
                    Log.i(TAG, "arg2_str + " + expression.charAt(i));
                    arg2_str += expression.charAt(i);
                }
            } else {
                if(!isFirstArg){
                    // Second arg filled, make calculation and put it in first arg
                    // if expression string is not ended

                    int arg1 = Integer.parseInt(arg1_str);
                    int arg2 = Integer.parseInt(arg2_str);

                    Log.i(TAG, "arg1 = " + arg1);
                    Log.i(TAG, "arg2 = " + arg2);


                    int result = calculate(arg1, arg2, action);
                    Log.i(TAG, "result = " + result);


                    // End of expression - print result
                    if(i == expression.length() - 1){
                        calcResultView.setText(result + "");
                        calcExpressionView.setText("0");
                    }
                    // Still have arg to parse
                    else {
                        arg1_str = String.valueOf(result);
                        arg2_str = "";
                        action = Action.EQUALS;
                    }
                } else {
                    // . + * - /
                    action = getActionFromChar(expression.charAt(i));
                    Log.i(TAG, "Action = " + action);

                    if(action == null){
                        return;
                        //throw new Exception("Undefined action exception");
                    }
                    isFirstArg = false;
                }

            }
        }

        int arg1 = Integer.parseInt(arg1_str);
        int arg2 = Integer.parseInt(arg2_str);

        Log.i(TAG, "arg1 = " + arg1);
        Log.i(TAG, "arg2 = " + arg2);


        int result = calculate(arg1, arg2, action);
        Log.i(TAG, "result = " + result);


        calcResultView.setText(result + "");
        expression = result + "";
        updateCalcExpressionUI();
    }

    private void erase(){
        if(expression.length() <= 0 || expression.length() == 1 || expression.equals("0")){
            expression = "0";
            return;
        }

        expression = expression.substring(0, expression.length() - 1);
        updateCalcExpressionUI();
    }

    private int calculate(int a, int b, Action action){
        switch (action) {
            case PLUS: return a + b;
            case MINUS: return a - b;
            case DIVIDE: return divide(a, b);
            case MULTIPLY: return a * b;
            default: return -1;

            //throw unexpected action exception
        }
    }

    private int divide(int a, int b){
        if(b == 0){
            // throw ZeroDivisionException
        }
        return a / b;
    }
}
