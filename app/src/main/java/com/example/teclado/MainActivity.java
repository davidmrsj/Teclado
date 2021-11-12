package com.example.teclado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContextParams;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout tecladomin;
    ConstraintLayout tecladomax;
    String texto;
    TextView txtTeclado;
    int mayus;
    int nulo;
    String[] valoresSpinner={"0","1","2","3","4","5","6","7","8","9"};
    Spinner rotacion;
    String textoEncriptado;
    String textoDesencriptado;
    Button encriptacion;
    Button desencriptacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mayus = 0;


        tecladomin = findViewById(R.id.contenedor1);
        tecladomax = findViewById(R.id.contenedor);
        txtTeclado = (TextView) findViewById(R.id.textView);
        rotacion = (Spinner) findViewById(R.id.spinner);
        encriptacion=(Button) findViewById(R.id.button2);
        desencriptacion = (Button) findViewById(R.id.button);

        ArrayAdapter ad;
        ad = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, valoresSpinner);
        rotacion.setAdapter(ad);

        iniciaBotonesTeclado(tecladomin);
        iniciaBotonesTeclado(tecladomax);
        manejoImageBotonClick();
        onclickEncriptacion();
    }

    // Inicializa todos los botones del layout que le pases
    //@Param cont Layout que se le pasa para iniciar los botones que esten dentro de el
    public void iniciaBotonesTeclado(ConstraintLayout cont){
        for(int i = 0; i< cont.getChildCount(); i++){
            if(cont.getChildAt(i).getTag()!="contenedor" || cont.getChildAt(i).getTag()!="contenedor1") {
                Button letra = (Button) cont.getChildAt(i);
                letra.setOnClickListener(this);
            }else{

            }
        }
    }

    public void onclickEncriptacion(){

        encriptacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encriptarTexto();
            }
        });
        desencriptacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desencriptarTexto();
            }
        });

    }
    /*
     Manejo de los cambios del imagebutton y del teclado
    Variable mayus para controlar que teclado hay que poner 0 minus 1 mayus una vez y 2 mayus sostenido
    */

    public void manejoImageBotonClick(){
        ImageButton img = (ImageButton) findViewById(R.id.imageButton);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton click = (ImageButton) v;
                    if(mayus ==2){
                        click.setImageResource(R.drawable.mayus);
                        mayus=0;
                        tecladomin.setVisibility(View.VISIBLE);
                        tecladomax.setVisibility(View.INVISIBLE);
                    }
                    else if(mayus==1){
                        click.setImageResource(R.drawable.mayus);
                        mayus=0;
                        tecladomin.setVisibility(View.VISIBLE);
                        tecladomax.setVisibility(View.INVISIBLE);
                    }else if(mayus==0) {
                        click.setImageResource(R.drawable.mayus1);
                        mayus = 1;
                        tecladomin.setVisibility(View.INVISIBLE);
                        tecladomax.setVisibility(View.VISIBLE);
                    }
                }
            });

        //Clase anonima onLongClick del imagebutton que se queda sostenido con el teclado en mayusculas

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageButton click = (ImageButton) v;
                if(mayus==2) {
                    click.setImageResource(R.drawable.mayus);
                    mayus=0;
                    tecladomin.setVisibility(View.VISIBLE);
                    tecladomax.setVisibility(View.INVISIBLE);
                }else {
                    click.setImageResource(R.drawable.mayus2);
                    mayus = 2;
                    tecladomin.setVisibility(View.INVISIBLE);
                    tecladomax.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

  // Metodo onclick del teclado que escribe en el textview controlando mayusculas y minusculas
    @Override
    public void onClick(View v) {

        int nombre = getResources().getIdentifier(String.valueOf(v.getId()),
                "id", getPackageName());
        Button letra = (Button) findViewById(nombre);

        if(nulo==0){
            if(mayus==0) {
                texto = letra.getText().toString().toLowerCase();
            }
            else if(mayus==1){
                texto = letra.getText().toString();
                ImageButton imagen1 = (ImageButton) findViewById(R.id.imageButton);
                imagen1.setImageResource(R.drawable.mayus);
                mayus = 0;
                tecladomin.setVisibility(View.VISIBLE);
                tecladomax.setVisibility(View.INVISIBLE);
            }else if(mayus==2){
                texto= letra.getText().toString();
            }
        }else {
            if (mayus == 0) {
                texto += letra.getText().toString().toLowerCase();
            } else if (mayus == 1) {
                texto += letra.getText();
                ImageButton imagen1 = (ImageButton) findViewById(R.id.imageButton);
                imagen1.setImageResource(R.drawable.mayus);
                mayus = 0;
                tecladomin.setVisibility(View.VISIBLE);
                tecladomax.setVisibility(View.INVISIBLE);
            } else if (mayus == 2) {
                texto += letra.getText();
            }
        }
        txtTeclado.setText(texto);
        nulo++;
    }

    //Metodo que encripta el texto
    public void encriptarTexto(){
        textoEncriptado ="";
        String abcMinus = "abcdefghijklmnñopqrstuvwxyz";
        String abcMayus = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String textoAencriptar = txtTeclado.getText().toString();
        int numRotacion = Integer.parseInt((String)rotacion.getSelectedItem());
        for(int i = 0; i<textoAencriptar.length();i++){
            if(Character.isUpperCase(textoAencriptar.charAt(i))){
                Character letra = textoAencriptar.charAt(i);
                for (int c = 0;c<abcMayus.length();c++){
                    if(letra==abcMayus.charAt(c)) {
                        int posicion = (c + numRotacion) % 27;
                        textoEncriptado += abcMayus.charAt(posicion);
                    }
                }
            }else if(Character.isLowerCase(textoAencriptar.charAt(i))){
                Character letra = textoAencriptar.charAt(i);
                for(int b=0; b<abcMinus.length();b++) {
                    if( letra==abcMinus.charAt(b)) {
                        int posicion = (b + numRotacion) % 27;
                        textoEncriptado += Character.toString(abcMinus.charAt(posicion));
                    }
                }
            }
        }
        txtTeclado.setText(textoEncriptado);
        texto = "";
    }
    //Metodo que desencripta el texto
    public void desencriptarTexto(){
        textoEncriptado ="";
        String abcMinus = "abcdefghijklmnñopqrstuvwxyz";
        String abcMayus = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String textoAencriptar = txtTeclado.getText().toString();
        int numRotacion = Integer.parseInt((String)rotacion.getSelectedItem());
        for(int i = 0; i<textoAencriptar.length();i++){
            if(Character.isUpperCase(textoAencriptar.charAt(i))){
                Character letra = textoAencriptar.charAt(i);
                for (int c = 0;c<abcMayus.length();c++){
                    if(letra==abcMayus.charAt(c)) {
                        int rotacion=c + (-numRotacion);
                        if(rotacion<0){
                            rotacion+=27;
                        }
                        int posicion = (rotacion) % 27;
                        textoEncriptado += abcMayus.charAt(posicion);
                    }
                }
            }else if(Character.isLowerCase(textoAencriptar.charAt(i))){
                Character letra = textoAencriptar.charAt(i);
                for(int b=0; b<abcMinus.length();b++) {
                    if( letra==abcMinus.charAt(b)) {
                        int rotacion=b + (-numRotacion);
                        if(rotacion<0){
                            rotacion+=27;
                        }
                        int posicion = rotacion % 27;
                        textoEncriptado += Character.toString(abcMinus.charAt(posicion));
                    }
                }
            }
        }
        txtTeclado.setText(textoEncriptado);
        texto = "";
    }

}