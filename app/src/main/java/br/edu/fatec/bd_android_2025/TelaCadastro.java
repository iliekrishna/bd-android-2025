package br.edu.fatec.bd_android_2025;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.fatec.bd_android_2025.dao.AlunoDAO;
import br.edu.fatec.bd_android_2025.model.Aluno;

public class TelaCadastro extends AppCompatActivity {

    private EditText edtId;
    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtTelefone;
    private AlunoDAO dao;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtId = findViewById(R.id.edtId);

        edtId.setEnabled(false);
        edtId.setFocusable(false);
        edtId.setClickable(false);
        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
    }

    public void salvar(View view) {
        // Ensure that EditText fields are not null or empty
        String nome = edtNome.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();

        aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setCpf(cpf);
        aluno.setTelefone(telefone);

        // Open the DB
        dao = new AlunoDAO(this);

        if (dao.cpfExists(aluno.getCpf())) {
            // If CPF exists, show a message
            Toast.makeText(getApplicationContext(),
                    "CPF já cadastrado. Tente novamente.", Toast.LENGTH_LONG).show();

            return;
        }
        aluno = new Aluno();
        aluno.setNome(edtNome.getText().toString());
        aluno.setCpf(edtCpf.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());

        // abrir o BD
        dao = new AlunoDAO(this);
        // Salvar
        long id = dao.insert(aluno);
        Toast.makeText(getApplicationContext(),
                "Aluno inserido com o ID "+id, Toast.LENGTH_LONG).show();
    }

    public void limpar(View view) {
        Log.e("TelaNovo", "Falha grotesca!");

        edtId.setText("");
        edtNome.setText("");
        edtCpf.setText("");
        edtTelefone.setText("");
    }

    public void voltar(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}


