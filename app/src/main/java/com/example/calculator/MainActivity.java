package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.PrimitiveElement;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String expresion = "";

    /*
        Utilizamos mXparser, que se encargará de todas las operaciones lógicas,
        ahorrando así líneas de código y tiempo de desarrollo.
    */
    Expression expression = new Expression(expresion);
    double resultado = expression.calculate();

    boolean abrirParentesis = true;

    TextView pantalla;
    TextView[] numButtons = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pantalla = findViewById(R.id.pantalla);
        pantalla.setText("0");

        /*
            getResources().getIdentifier("num"+i, "id", getPackageName())
            para buscar R.id.num0...num9.
        */

        for (int i=0; i<=9; i++){
            String numero = "num"+i;
            int resId = getResources().getIdentifier(numero, "id",getPackageName());
            if (resId != 0){
                numButtons[i] = findViewById(resId);
                if (numButtons[i] != null) {
                    numButtons[i].setOnClickListener(this);
                }
            }
        }

        TextView ac = findViewById(R.id.ac);
        if (ac != null) ac.setOnClickListener(this);

        TextView divide = findViewById(R.id.divide);
        if (divide != null) divide.setOnClickListener(this);

        TextView multiply = findViewById(R.id.multiply);
        if (multiply != null) multiply.setOnClickListener(this);

        TextView rest = findViewById(R.id.restar);
        if (rest != null) rest.setOnClickListener(this);

        TextView sum = findViewById(R.id.sumar);
        if (sum != null) sum.setOnClickListener(this);

        TextView coma = findViewById(R.id.coma);
        if (coma != null) coma.setOnClickListener(this);

        TextView equal = findViewById(R.id.equal);
        if (equal != null) equal.setOnClickListener(this);

        TextView parentheses = findViewById(R.id.parentheses);
        if (parentheses != null) parentheses.setOnClickListener(this);

        TextView percentage = findViewById(R.id.percentage);
        if (percentage != null) percentage.setOnClickListener(this);

        TextView delete = findViewById(R.id.delete);
        if (delete != null) delete.setOnClickListener(this);

        TextView igual = findViewById(R.id.equal);
        if (igual != null) igual.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        for (int i = 0; i<=9; i++){
            if (id == (numButtons[i] != null ? numButtons[i].getId() : -1)) {
                expresion += i;
                pantalla.setText(expresion);
                return;
            }
        }

        if (id == R.id.ac) {
            expresion = "";
            pantalla.setText("");
            return;
        }

        if (id == R.id.delete) {
            String elementoPant = pantalla.getText().toString();
            if (!elementoPant.isEmpty()){
                elementoPant = elementoPant.substring(0, elementoPant.length()-1);
                pantalla.setText(elementoPant);
            }else {
                pantalla.setText(" ");
            }
            return;
        }

        if (id == R.id.coma) {
            expresion += ".";
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.sumar) {
            expresion += "+";
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.restar) {
            expresion += "-";
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.divide) {
            expresion += "/";
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.multiply) {
            expresion += "*";
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.parentheses) {
            expresion += abrirParentesis ? "(" : ")";
            abrirParentesis = !abrirParentesis;
            pantalla.setText(expresion);
            return;
        }
        if (id == R.id.percentage) {
            expresion += "%";
            pantalla.setText(expresion);
            return;
        }

        if (id == R.id.equal) {
            Expression exp = new Expression(expresion);
            double resultado = exp.calculate();

            if (Double.isNaN(resultado)){
                pantalla.setText("ERROR");
            } else {
                pantalla.setText(String.valueOf(resultado));
                expresion = String.valueOf(resultado);
            }
        }
        }
    }
