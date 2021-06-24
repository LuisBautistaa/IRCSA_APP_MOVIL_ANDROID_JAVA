package com.example.ircsa2019;
/*
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainMantenimientoUsuarios extends AppCompatActivity {
    private static final String CERO = "0";
    private static final String BARRA = "/";
    DatabaseHelper datos1;
    Spinner spinner1;
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    int fechaobtenidaguardada=0;
    //Widgets
    TextView etFechaInic,etFechaFin;
    EditText login,password;
    TextView identificado;
    String fechainicio="",fechafin="",logueo="",contraseña="",estado="",NombreRecuperado="",ideUsuario="",datos="";
    CheckBox check_habil;
    ImageButton FechaInicios,FechaFin,agregar,eliminar,actualizar,cancelar,nuevos;
    ListView listas;
    int fechasistema=0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mantenimiento_usuarios);
        Intent recibir = getIntent();
        datos = recibir.getStringExtra("usuarios");
        datos1 = new DatabaseHelper(this);
        //Widget EditText donde se mostrara la fecha obtenida
        etFechaInic = (TextView) findViewById(R.id.text_fecinic);
        etFechaFin=(TextView) findViewById(R.id.text_fecfin);
        identificado=(TextView)findViewById(R.id.ident);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        listas =(ListView)findViewById(R.id.lista);
        String impresion = simpleDateFormat.format(calendar.getTime());
        fechasistema=Integer.parseInt(impresion);
        login=(EditText)findViewById(R.id.text_login);
        password=(EditText)findViewById(R.id.text_contraseña);
        check_habil=(CheckBox)findViewById(R.id.check_habilitado);
        nuevos=(ImageButton)findViewById(R.id.imagbtn_agregarnuevos);
        //Botones
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        FechaInicios = (ImageButton) findViewById(R.id.calendarioInicio);
        FechaFin =(ImageButton)findViewById(R.id.calendarioFin);
        agregar=(ImageButton)findViewById(R.id.btn_Agregar);
        eliminar=(ImageButton)findViewById(R.id.btn_Borrar);
        actualizar=(ImageButton)findViewById(R.id.btn_actualizar);
        cancelar=(ImageButton)findViewById(R.id.btn_cancelar);
        agregar.setEnabled(false);
        eliminar.setEnabled(false);
        actualizar.setEnabled(false);
        if(datos.equals("admin")){
            agregar.setEnabled(true);
            eliminar.setEnabled(true);
            actualizar.setEnabled(true);
        }else{
            habilitarPermisos(datos);
        }
        cargarbd();
        //insertar datos dentro del spinner inicio
        ArrayList<String> ranking = new ArrayList<>();
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        Cursor fila = bd.rawQuery("select Nombre.nombre,Apellido.apellido,ApeMat.apellido,Puesto.puesto from Nombre,Apellido,ApeMat,Puesto,mPersona where Nombre.codigo = mPersona.Nombre and Apellido.codigo =mPersona.ApePat and ApeMat.codigo = mPersona.ApeMat and Puesto.codigo=mPersona.Puesto", null);
        if(fila.moveToFirst()){
            do{
                ranking.add(fila.getString(0)+" "+fila.getString(1)+" "+fila.getString(2)+" "+fila.getString(3) );
            }while(fila.moveToNext());
        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ranking);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NombreRecuperado=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Eventos botones y acciones
        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gurdar[];
                String split= parent.getItemAtPosition(position).toString();
                gurdar=split.split("-");
                String ideUser = gurdar[0];
                String usuario=gurdar[1];
                String Login=gurdar[2];
                String Fecinicios=gurdar[3];
                String Fecfinales=gurdar[4];
               // String Fecfinales=extraerfecf.substring(1);
                identificado.setText(ideUser);
                login.setText(Login);
                password.setText(contraseña);
                etFechaInic.setText(Fecinicios);
                etFechaFin.setText(Fecfinales);
                //String valores=gurdar[1];
            }
        });
        //Evento setOnClickListener - clic
        FechaInicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.calendarioInicio:
                        int tiempos=1;
                        int opcion=1;
                        obtenerFecha(tiempos,opcion,fechasistema);
                        break;
                }
            }
        });
        FechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.calendarioFin:
                        int tiempos=2;
                        int opcion=2;
                        obtenerFecha(tiempos,opcion,fechasistema);
                        break;
                }
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logueo=login.getText().toString();
                contraseña=password.getText().toString();
                fechainicio=etFechaInic.getText().toString();
                fechafin=etFechaFin.getText().toString();
                if (check_habil.isChecked()==true) {
                    estado="habilitado";
                }else {estado="desabilitado";}
                String lista[];
                 lista=NombreRecuperado.split(" ");
                 String nombre=lista[0];
                 if(logueo.equals("")||contraseña.equals("")||fechainicio.equals("")||fechafin.equals("")){
                     Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
                 }else{
                     agregar(nombre,logueo,contraseña,fechainicio,fechafin,estado);
                     cargarbd();
                     limpiar();
                 }

            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idde=identificado.getText().toString();
                borrar(idde);
                cargarbd();
                limpiar();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estados="";
                String idde=identificado.getText().toString();
                String logueos=login.getText().toString();
                String pass=password.getText().toString();
                String inic=etFechaInic.getText().toString();
                String finale=etFechaFin.getText().toString();
                if (check_habil.isChecked()==true) {
                    estados="habilitado";
                }else {estados="desabilitado";}
                actualizar(idde,NombreRecuperado,logueos,pass,inic,finale,estados);
                cargarbd();
                limpiar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
        nuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainMantenimientoUsuarios.this,MainMttoPersonas.class);
                startActivity(i);
            }
        });
    }
    private void habilitarPermisos(String usuario){
        ArrayList <String> ranking = new ArrayList<>();
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from MAccesos where login=?", new String[]{usuario});
        if(fila.moveToFirst()){
            do{
                ranking.add(fila.getString(1) );
            }while(fila.moveToNext());
        }
        bd.close();
        for(int x=0;x<ranking.size();x++){
            if(ranking.get(x).equals("Alta de Usuarios")){
                agregar.setEnabled(true);
            }
            if(ranking.get(x).equals("Baja de Usuarios")){
                eliminar.setEnabled(true);
            }
            if(ranking.get(x).equals("Modificar Usuarios")){
                actualizar.setEnabled(true);
            }
        }
    }
    private void obtenerFecha(final int opcion,final int opciones,final int fechasistemas){

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                String FechainicIngresada=year+mesFormateado+diaFormateado;
                int fechaObtenida=Integer.parseInt(FechainicIngresada);
                if(opciones==1){
                    if(fechasistemas>fechaObtenida){
                        Toast.makeText(getApplicationContext()," fecha inicio anticipada",Toast.LENGTH_SHORT).show();
                        etFechaFin.setText(" ");
                        etFechaInic.setText(" ");
                    }if(fechasistemas <= fechaObtenida){
                        int evalua=Integer.parseInt(diaFormateado);
                        evalua=evalua+10;
                            etFechaInic.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
                        //etFechaFin.setText(year + BARRA + mesFormateado + BARRA + evalua);
                           String obte=etFechaInic.getText().toString();
                        String unificar1=obte.replace("/","");
                           fechaobtenidaguardada=Integer.parseInt(unificar1);
                }
            }
                if (opciones == 2) {
                    if(fechaObtenida<=fechaobtenidaguardada){
                        Toast.makeText(getApplicationContext()," fecha anticipada",Toast.LENGTH_SHORT).show();
                        etFechaFin.setText(" ");

                    }else {//la fecha ingresada es mayor a la del sistema}
                            etFechaFin.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);

                    }}
                }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */

/*
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }
    private void cargarbd(){
        ArrayList <String> ranking = new ArrayList<>();
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from MttoUsuario" , null);
        if(fila.moveToFirst()){
            do{
                ranking.add(fila.getString(0) + "-" + fila.getString(1)+ "-" + fila.getString(2)+ "-" + fila.getString(4)+ "-" + fila.getString(5)+ "-" + fila.getString(6));
            }while(fila.moveToNext());
        }
        bd.close();
        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ranking);
        listas.setAdapter(adapterr);
    }
    private  void agregar(String R_NombreR,String R_logueo,String R_contraseña,String R_fecinicio,String R_fecfin,String R_estado){
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre",R_NombreR);
        contentValues.put("login",R_logueo);
        contentValues.put("password",R_contraseña);
        contentValues.put("fecinic",R_fecinicio);
        contentValues.put("fecfin",R_fecfin);
        contentValues.put("edocta",R_estado);
        int regreso=busquedaAlagregarUsuarios("login",R_logueo);
        if(regreso==1) {
            int regreso2=busquedaAlagregarUsuarios("password",R_contraseña);
            if(regreso2==1) {
                bd.insert("MttoUsuario", null, contentValues);
            }else{ Toast.makeText(getApplicationContext(),"la contraseña ya esta siendo utilizada",Toast.LENGTH_SHORT).show();}
        }else{ Toast.makeText(getApplicationContext(),"El login ya esta siendo utilizado",Toast.LENGTH_SHORT).show();}
    }
    private int busquedaAlagregarUsuarios(String nombrec,String valor) {
        int regresando = 0;
        ArrayList<String> ranking = new ArrayList<>();
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getReadableDatabase();
        Cursor fila = bd.rawQuery("select * from MttoUsuario where " + nombrec + "=?", new String[]{valor});
        if (fila.getCount() == 0) {
            regresando = 1;
        }else regresando=0;
        bd.close();
        return regresando;
    }
    private void borrar(String ide){
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        bd.delete("MttoUsuario","codigo=?",new String[]{String.valueOf(ide)});
    }
    private  void actualizar(String ide,String R_NombreR,String R_logueo,String R_contraseña,String R_fecinicio,String R_fecfin,String R_estado){
        DatabaseHelper juego = new DatabaseHelper(this);
        SQLiteDatabase bd = juego.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre",R_NombreR);
        contentValues.put("login",R_logueo);
        contentValues.put("password",R_contraseña);
        contentValues.put("fecinic",R_fecinicio);
        contentValues.put("fecfin",R_fecfin);
        contentValues.put("edocta",R_estado);
        bd.update("MttoUsuario",contentValues,"codigo=?",new String[]{String.valueOf(ide)});

    }
    private void limpiar(){
        login.setText("");
        password.setText("");
        etFechaInic.setText("");
        etFechaFin.setText("");

    }


}
        */