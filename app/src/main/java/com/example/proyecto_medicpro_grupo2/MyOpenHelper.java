package com.example.proyecto_medicpro_grupo2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyecto_medicpro_grupo2.utiles.Medicamento;
import com.example.proyecto_medicpro_grupo2.utiles.Recordatorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String tbUsuario = "CREATE TABLE Usuario (idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombresApellidos TEXT, " +
            "correo TEXT, " +
            "clave TEXT)";
    public static final String tbMedicamento = "CREATE TABLE Medicamento (idMedicamento INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "dosis TEXT, " +
            "idUsuario INTEGER, " +
            "FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario))";

    /*public static final String tbRecordatorio = "CREATE TABLE Recordatorio (idRecordatorio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "hora TEXT, " +
            "fecha TEXT, " +
            "idMedicamento INTEGER, " +
            "idUsuario INTEGER,"+
            "FOREIGN KEY (idMedicamento) REFERENCES Medicamento (idMedicamento), " +
            "FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario));";*/
    public static final String tbEstado = "CREATE TABLE Estado (" +
            "idEstado INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descripcionEstado TEXT, " +
            "colorEstado TEXT);";
    public static final String tbRecordatorio = "CREATE TABLE Recordatorio (" +
            "idRecordatorio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "hora TEXT, " +
            "fecha TEXT, " +
            "idMedicamento INTEGER, " +
            "idUsuario INTEGER, " +
            "idEstado INTEGER, " +
            "FOREIGN KEY (idMedicamento) REFERENCES Medicamento (idMedicamento), " +
            "FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario), " +
            "FOREIGN KEY (idEstado) REFERENCES Estado (idEstado));";
    public static final String dropTbRecordatorio = "DROP TABLE IF EXISTS Recordatorio;";

    public static final String insertEstado1 = "INSERT INTO Estado (descripcionEstado, colorEstado) VALUES ('Ingresado', '#a39595');";
    public static final String insertEstado2 = "INSERT INTO Estado (descripcionEstado, colorEstado) VALUES ('Completado', '#33FF57');";
    public static final String insertEstado3 = "INSERT INTO Estado (descripcionEstado, colorEstado) VALUES ('Pospuesto', '#3357FF');";

    public static final String dbName = "MedicPro.db";
    public static final int dbVersion = 5;
    public MyOpenHelper (Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbUsuario);
        db.execSQL(tbMedicamento);
        //db.execSQL(tbRecordatorio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 6) {
            db.execSQL(tbRecordatorio);
            //db.execSQL(dropTbRecordatorio);
            //db.execSQL(tbEstado);
            //db.execSQL(insertEstado1);
            //db.execSQL(insertEstado2);
            //db.execSQL(insertEstado3);
        }
    }

    @SuppressLint("Range")
    public List<Medicamento> obtenerMedicamentos(int idUsuario) {
        List<Medicamento> medicamentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM Medicamento WHERE idUsuario = " + idUsuario;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Medicamento medicamento = new Medicamento();
                medicamento.setIdMedicamento(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idMedicamento"))));
                medicamento.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                medicamento.setDosis(cursor.getString(cursor.getColumnIndex("dosis")));
                medicamento.setIdUsuario(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUsuario"))));

                medicamentos.add(medicamento);

            } while (cursor.moveToNext());

        }
        return medicamentos;
    }

    public long agregarRecordatorios(Recordatorio recordatorio) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", recordatorio.getNombreRecordatorio());
        values.put("hora", recordatorio.getHoraRecordatorio());
        values.put("fecha", recordatorio.getFechaRecordatorio());
        values.put("idMedicamento", recordatorio.getIdMedicamento());
        values.put("idUsuario", recordatorio.getIdUsuario());
        values.put("idEstado", recordatorio.getIdEstado());

        return db.insert("Recordatorio", null, values);

    }

    @SuppressLint("Range")
    public List<Recordatorio> obtenerRecordatorios(int userId) {
        List<Recordatorio> recordatorios = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT Recordatorio.idRecordatorio, Recordatorio.nombre AS nombreRecordatorio, " +
                "Recordatorio.hora AS horaRecordatorio, Recordatorio.fecha AS fechaRecordatorio, " +
                "Estado.descripcionEstado, Medicamento.nombre AS nombreMedicamento, Estado.colorEstado " +
                "FROM Recordatorio " +
                "INNER JOIN Estado ON Recordatorio.idEstado = Estado.idEstado " +
                "INNER JOIN Medicamento ON Recordatorio.idMedicamento = Medicamento.idMedicamento " +
                "WHERE Recordatorio.idUsuario = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setIdRecordatorio(cursor.getInt(cursor.getColumnIndex("idRecordatorio")));
                recordatorio.setNombreRecordatorio(cursor.getString(cursor.getColumnIndex("nombreRecordatorio")));
                recordatorio.setHoraRecordatorio(cursor.getString(cursor.getColumnIndex("horaRecordatorio")));
                recordatorio.setFechaRecordatorio(cursor.getString(cursor.getColumnIndex("fechaRecordatorio")));
                recordatorio.setDescripcionEstado(cursor.getString(cursor.getColumnIndex("descripcionEstado")));
                recordatorio.setNombreMedicamento(cursor.getString(cursor.getColumnIndex("nombreMedicamento")));
                recordatorio.setColorEstado(cursor.getString(cursor.getColumnIndex("colorEstado")));

                recordatorios.add(recordatorio);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return recordatorios;
    }

    @SuppressLint("Range")
    public Recordatorio obtenerRecordatorioPorId(int idUsuario, int idRecordatorio) {
        Recordatorio recordatorio = new Recordatorio();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT Recordatorio.idRecordatorio, Recordatorio.nombre AS nombreRecordatorio, " +
                "Recordatorio.hora AS horaRecordatorio, Recordatorio.fecha AS fechaRecordatorio, " +
                "Estado.descripcionEstado, Medicamento.nombre AS nombreMedicamento, Estado.colorEstado " +
                "FROM Recordatorio " +
                "INNER JOIN Estado ON Recordatorio.idEstado = Estado.idEstado " +
                "INNER JOIN Medicamento ON Recordatorio.idMedicamento = Medicamento.idMedicamento " +
                "WHERE Recordatorio.idUsuario = ? AND Recordatorio.idRecordatorio = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(idUsuario), String.valueOf(idRecordatorio)});
        if (cursor.moveToFirst()) {
            do{
                recordatorio.setIdRecordatorio(cursor.getInt(cursor.getColumnIndex("idRecordatorio")));
                recordatorio.setNombreRecordatorio(cursor.getString(cursor.getColumnIndex("nombreRecordatorio")));
                recordatorio.setHoraRecordatorio(cursor.getString(cursor.getColumnIndex("horaRecordatorio")));
                recordatorio.setFechaRecordatorio(cursor.getString(cursor.getColumnIndex("fechaRecordatorio")));
                recordatorio.setDescripcionEstado(cursor.getString(cursor.getColumnIndex("descripcionEstado")));
                recordatorio.setNombreMedicamento(cursor.getString(cursor.getColumnIndex("nombreMedicamento")));
                recordatorio.setColorEstado(cursor.getString(cursor.getColumnIndex("colorEstado")));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return recordatorio;
    }

    public void actualizarEstadoRecordatorio(int idRecordatorio, int idEstado) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEstado", idEstado);
        db.update("Recordatorio", values, "idRecordatorio = ?", new String[]{String.valueOf(idRecordatorio)});
    }

    /*public int calcularPorcentajeCumplimiento(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        int porcentajeTotalCumplimiento = 0;
        int totalMedicamentos = 0;

        // Consulta para obtener todos los medicamentos del usuario
        String queryMedicamentos = "SELECT idMedicamento FROM Medicamento WHERE idUsuario = ?";
        Cursor cursorMedicamentos = db.rawQuery(queryMedicamentos, new String[]{String.valueOf(idUsuario)});

        if (cursorMedicamentos.moveToFirst()) {
            do {
                int idMedicamento = cursorMedicamentos.getInt(0);
                int totalRecordatorios = 0;
                int recordatoriosCompletados = 0;

                // Consulta para obtener el número total de recordatorios para el medicamento
                String queryTotal = "SELECT COUNT(*) FROM Recordatorio WHERE idMedicamento = ? AND idUsuario = ?";
                Cursor cursorTotal = db.rawQuery(queryTotal, new String[]{String.valueOf(idMedicamento), String.valueOf(idUsuario)});
                if (cursorTotal.moveToFirst()) {
                    totalRecordatorios = cursorTotal.getInt(0);
                }
                cursorTotal.close();

                // Consulta para obtener el número de recordatorios completados para el medicamento
                String queryCompletados = "SELECT COUNT(*) FROM Recordatorio WHERE idMedicamento = ? AND idUsuario = ? AND idEstado = 2";
                Cursor cursorCompletados = db.rawQuery(queryCompletados, new String[]{String.valueOf(idMedicamento), String.valueOf(idUsuario)});
                if (cursorCompletados.moveToFirst()) {
                    recordatoriosCompletados = cursorCompletados.getInt(0);
                }
                cursorCompletados.close();

                // Calcular el porcentaje de cumplimiento para este medicamento
                if (totalRecordatorios > 0) {
                    int porcentajeCumplimiento = (recordatoriosCompletados * 100) / totalRecordatorios;
                    porcentajeTotalCumplimiento += porcentajeCumplimiento;
                    totalMedicamentos++;
                }

            } while (cursorMedicamentos.moveToNext());
        }
        cursorMedicamentos.close();

        // Calcular el porcentaje total de cumplimiento promedio para todos los medicamentos
        if (totalMedicamentos > 0) {
            return porcentajeTotalCumplimiento / totalMedicamentos;
        } else {
            return 0;
        }
    }*/

    public List<Medicamento> obtenerMedicamentosConCumplimiento(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Medicamento> medicamentos = new ArrayList<>();

        // Consulta para obtener todos los medicamentos del usuario
        String queryMedicamentos = "SELECT idMedicamento, nombre, dosis, idUsuario FROM Medicamento WHERE idUsuario = ?";
        Cursor cursorMedicamentos = db.rawQuery(queryMedicamentos, new String[]{String.valueOf(idUsuario)});

        if (cursorMedicamentos.moveToFirst()) {
            do {
                int idMedicamento = cursorMedicamentos.getInt(0);
                String nombre = cursorMedicamentos.getString(1);
                String dosis = cursorMedicamentos.getString(2);
                int userId = cursorMedicamentos.getInt(3);

                Medicamento medicamento = new Medicamento(idMedicamento, nombre, dosis, userId);

                int totalRecordatorios = 0;
                int recordatoriosCompletados = 0;

                // Consulta para obtener el número total de recordatorios para el medicamento
                String queryTotal = "SELECT COUNT(*) FROM Recordatorio WHERE idMedicamento = ? AND idUsuario = ?";
                Cursor cursorTotal = db.rawQuery(queryTotal, new String[]{String.valueOf(idMedicamento), String.valueOf(idUsuario)});
                if (cursorTotal.moveToFirst()) {
                    totalRecordatorios = cursorTotal.getInt(0);
                }
                cursorTotal.close();

                // Consulta para obtener el número de recordatorios completados para el medicamento
                String queryCompletados = "SELECT COUNT(*) FROM Recordatorio WHERE idMedicamento = ? AND idUsuario = ? AND idEstado = 2";
                Cursor cursorCompletados = db.rawQuery(queryCompletados, new String[]{String.valueOf(idMedicamento), String.valueOf(idUsuario)});
                if (cursorCompletados.moveToFirst()) {
                    recordatoriosCompletados = cursorCompletados.getInt(0);
                }
                cursorCompletados.close();

                // Calcular el porcentaje de cumplimiento para este medicamento
                int porcentajeCumplimiento = 0;
                if (totalRecordatorios > 0) {
                    porcentajeCumplimiento = (recordatoriosCompletados * 100) / totalRecordatorios;
                }

                medicamento.setPorcentajeMedicamento(porcentajeCumplimiento);

                // Añadir el medicamento con el porcentaje de cumplimiento a la lista
                medicamentos.add(medicamento);

            } while (cursorMedicamentos.moveToNext());
        }
        cursorMedicamentos.close();

        return medicamentos;
    }


}
