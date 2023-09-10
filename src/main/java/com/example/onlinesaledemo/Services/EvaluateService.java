package com.example.onlinesaledemo.Services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Expression;
import java.util.concurrent.Callable;
import java.util.*;

@Getter
@Setter
@Service

public class EvaluateService implements Callable<Integer> {

    private String expression;


    public Integer call(){


        String postFix = infixToPostFix(expression);

        return evaluate(postFix);
    }

    public int evaluate(String postFix){

        int n = postFix.length();

        Stack<Integer> s = new Stack<>();

        int i = 0;

        while(i < n){

            char ch = postFix.charAt(i);

            if(Character.isDigit(ch)){
                s.push(ch - '0');
            }
            else if(ch == '+' || ch == '-' || ch == '*' || ch == '/'|| ch == '^'){

                int y = s.pop();
                int x = s.pop();

                switch (ch){

                    case '+' :
                        int val = x + y;
                        s.push(val);
                        break;
                    case '-' :
                        int val2 = x - y;
                        s.push(val2);
                        break;
                    case '*' :
                        int val3 = x * y;
                        s.push(val3);
                        break;
                    case '/' :
                        int val4 = x / y;
                        s.push(val4);
                        break;
                    case '^' :
                        int val5 = (int)Math.pow(x,y);
                        s.push(val5);
                        break;
                }
            }
            else{
                StringBuilder sb = new StringBuilder();
                while(i < n && postFix.charAt(i) != 'e'){
                    char c = postFix.charAt(i);

                    if(c != 'r'){
                        sb.append(c);
                    }
                    i++;
                }
                int val = evaluate(sb.toString());
                s.push((int)Math.sqrt(val));
            }
            i++;
        }

        StringBuilder digit = new StringBuilder();

        while(s.size() > 0){
            digit.append(s.pop());
        }
        digit.reverse();

        return Integer.parseInt(digit.toString());
    }
    public String infixToPostFix(String A) {

        StringBuilder sb = new StringBuilder();
        Stack <Character> s = new Stack <Character>();

        int i = 0;

        while(i < A.length())
        {
            char ch = A.charAt(i);

            if(Character.isDigit(ch)) // ch is Character than directly adding it into StringBuilder
            {
                sb.append(ch);
            }
            else if(Character.isWhitespace(ch)){
                i++;
                continue;
            }
            else if(ch == '(') // if ch is opening bracket than pushing it into Stack
            {
                s.push(ch);
            }
            else if(ch == ')') // if ch is closing bracket than one by one poping out element from Stack until we get opening bracket
            {
                while(s.size() != 0 && s.peek() != '(')
                {
                    char c = s.pop();
                    sb.append(c);
                }
                if(s.size() > 0 && s.peek() == '(')
                {
                    s.pop();
                }
            }
            else if (ch == 's') {

                while(i < A.length() && ch == 's' || ch == 'q' || ch == 'r' || ch == 't'){
                    ch = A.charAt(i);
                    i++;
                }

                StringBuilder tem = new StringBuilder();
                // tem.append(ch);

                while(i < A.length() && A.charAt(i) != ')'){
                    tem.append(A.charAt(i));
                    i++;
                }
                //tem.append(A.charAt(i));
                sb.append('r');
                sb.append(infixToPostFix(tem.toString()));
                sb.append('e');

            }
            else {
/* If peek of the Character at top of Stack has higher precedence than ch we are are going to poping out one by one operators from
Stack and we will append it to the StringBuilder until we get lower precedence Character at peek of Stack.*/

                while(s.size() > 0 && checkPrecedence(s.peek()) >= checkPrecedence(ch))
                {
                    char t  = s.pop();

                    sb.append(t);
                }
                // if above operation complete than we have only have lower precedence Character at peek of Stack
                s.push(ch);// if we have already have lower precedence at peek of Stack than it directly add.


            }
            i++;
        }

        while(s.size() != 0) // after iterating over String we only have to append the Stack Character at the StringBuilder
        {
            //System.out.println(sb.toString());
            char c = s.pop();
            sb.append(c);
        }
        return sb.toString();
    }

    public static int checkPrecedence(char c) // checking precedence of new Character and peek Character of Stack by using this function
    {
        if(c == '(')
        {
            return -1;
        }
        else if(c =='^')
        {
            return 3;
        }
        else if(c == '*' || c == '/')
        {
            return 2;
        }
        return 1;

    }
}
