package br.edu.fatec.bd_android_2025.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.bd_android_2025.model.Aluno;
import br.edu.fatec.bd_android_2025.util.ConnectionFactory;

    public class AlunoDAO {
        private ConnectionFactory conexao;
        private SQLiteDatabase banco;
        public AlunoDAO(Context context) {
            //Conexao com o banco de dados
            conexao = new ConnectionFactory(context, "banco.db", null, 1);
            // permissão DDL
            banco = conexao.getWritableDatabase();
        }
        // método inserir
        public long insert(Aluno aluno){
            ContentValues values = new ContentValues();
            values.put("nome", aluno.getNome());
            values.put("cpf", aluno.getCpf());
            values.put("telefone", aluno.getTelefone());
            return(banco.insert("aluno", null, values));
        }
        public List<Aluno> obterTodos() {
            List<Aluno> alunos = new ArrayList<>();
            Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf",
                            "telefone"},
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                Aluno a = new Aluno();
                a.setId(cursor.getInt(0));
                a.setNome((cursor.getString(1)));
                a.setCpf((cursor.getString(2)));
                a.setTelefone((cursor.getString(3)));
                alunos.add(a);
            }
            return alunos;
        }
        public Aluno read(String nome) {
            String args[] = {nome};
            Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                    "nome=?", args, null, null, null);
            cursor.moveToFirst();
            Aluno aluno = new Aluno();
            if(cursor.getCount() > 0){
                aluno.setId(cursor.getInt(0));
                aluno.setNome((cursor.getString(1)));
                aluno.setCpf((cursor.getString(2)));
                aluno.setTelefone((cursor.getString(3)));
            }
            return aluno;
        }
        // Médoto Excluir
        public void delete(int id){
            banco.delete("aluno", "id = ?", new String[]{String.valueOf(id)});
            Log.d("AlunoDAO", "Aluno with id " + id + " deleted");
        }
        //Alterar
        public void update(Aluno aluno){
            ContentValues values = new ContentValues();
            values.put("nome", aluno.getNome());
            values.put("cpf" , aluno.getCpf());
            values.put("telefone", aluno.getTelefone());
            String args[] = {aluno.getId().toString()};
            banco.update("aluno", values,"id=?",args);
        }
        // Method to check if CPF already exists
        public boolean cpfExists(String cpf) {
            String[] columns = {"cpf"};
            String selection = "cpf = ?";
            String[] selectionArgs = {cpf};

            Cursor cursor = banco.query("aluno", columns, selection, selectionArgs, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

    }

