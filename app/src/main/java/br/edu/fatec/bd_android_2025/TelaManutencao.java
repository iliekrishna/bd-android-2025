package br.edu.fatec.bd_android_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.fatec.bd_android_2025.dao.AlunoDAO;
import br.edu.fatec.bd_android_2025.model.Aluno;

public class TelaManutencao extends AppCompatActivity {

    private AlunoDAO alunoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_manutencao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtId = findViewById(R.id.edtId);
        EditText edtNome = findViewById(R.id.edtNome);
        EditText edtCpf = findViewById(R.id.edtCpf);
        EditText edtTelefone = findViewById(R.id.edtTelefone);
        Button btnExcluir = findViewById(R.id.btnExcluir);
        Button btnAlterar = findViewById(R.id.btnAlterar);

        alunoDAO = new AlunoDAO(this);

        if (getIntent() != null) {
            int id = getIntent().getIntExtra("id", -1); // Default to -1 if not found
            String nome = getIntent().getStringExtra("nome");
            String cpf = getIntent().getStringExtra("cpf");
            String telefone = getIntent().getStringExtra("telefone");

            // Set the data to the EditText fields
            edtId.setText(String.valueOf(id));
            edtNome.setText(nome);
            edtCpf.setText(cpf);
            edtTelefone.setText(telefone);
        }

        int id = Integer.parseInt(String.valueOf(edtId.getText()));

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the student from the database
                alunoDAO.delete(id);
                Toast.makeText(TelaManutencao.this, "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show();

                // Finish the activity to go back to the previous one
                setResult(RESULT_OK);
                finish();
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve updated data from EditText fields
                String nome = edtNome.getText().toString().trim();
                String cpf = edtCpf.getText().toString().trim();
                String telefone = edtTelefone.getText().toString().trim();
                int id = Integer.parseInt(edtId.getText().toString()); // Get the ID from the EditText

                // Create an Aluno object with the updated data
                Aluno alunoToUpdate = new Aluno();
                alunoToUpdate.setId(id);
                alunoToUpdate.setNome(nome);
                alunoToUpdate.setCpf(cpf);
                alunoToUpdate.setTelefone(telefone);

                // Call the update method in AlunoDAO
                alunoDAO.update(alunoToUpdate);

                // Show a success message
                Toast.makeText(TelaManutencao.this, "Aluno alterado com sucesso!", Toast.LENGTH_SHORT).show();

                // Set the result and finish the activity
                setResult(RESULT_OK);
                finish();

            }
        });
    }

    public void voltar(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
