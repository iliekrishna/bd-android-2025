package br.edu.fatec.bd_android_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import br.edu.fatec.bd_android_2025.dao.AlunoDAO;
import br.edu.fatec.bd_android_2025.model.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView listViewAlunos;
    private AlunoDAO alunoDAO;
    private List<Aluno> alunos;

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

        alunoDAO = new AlunoDAO(this);
        alunos = alunoDAO.obterTodos(); // Fetching all students
        if (alunos != null && !alunos.isEmpty()) {
            // Set the item click listener
            listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Start the TelaAlterarExcluir activity
                    Aluno selectedAluno = alunos.get(position);

                    Intent intent = new Intent(MainActivity.this, TelaManutencao.class);

                    intent.putExtra("id", selectedAluno.getId()); // Pass the ID
                    intent.putExtra("nome", selectedAluno.getNome()); // Pass the name
                    intent.putExtra("cpf", selectedAluno.getCpf()); // Pass the CPF
                    intent.putExtra("telefone", selectedAluno.getTelefone()); // Pass the phone number

                    startActivityForResult(intent, 1); // Start TelaAlterarExcluir with a request code
                    updateListView();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void mostrarTexto() {
        Toast.makeText(getApplicationContext(), "Novo!!!", Toast.LENGTH_LONG).show();
    }

    public void sair(MenuItem item) {
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Novo) {
            // Lógica para o item "Novo"
            startActivity(new Intent(this, TelaCadastro.class));
            return true;
        } else if (item.getItemId() == R.id.Sair) {
            finish(); // ou outra lógica para sair
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void updateListView() {
        alunoDAO = new AlunoDAO(this);
        alunos = alunoDAO.obterTodos(); // Fetching all students

        if (alunos != null && !alunos.isEmpty()) {
            listViewAlunos = findViewById(R.id.listViewAlunos);
            ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
            listViewAlunos.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nenhum aluno encontrado!", Toast.LENGTH_SHORT).show();
            listViewAlunos = findViewById(R.id.listViewAlunos);
            ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
            listViewAlunos.setAdapter(adapter);
        }
    }

    public void updateListView(View view) {
        alunoDAO = new AlunoDAO(this);
        alunos = alunoDAO.obterTodos(); // Fetching all students

        if (alunos != null && !alunos.isEmpty()) {
            listViewAlunos = findViewById(R.id.listViewAlunos);
            ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
            listViewAlunos.setAdapter(adapter);
        } else {
            Toast.makeText(MainActivity.this, "Nenhum aluno encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateListView(); // Refresh the list after deletion
        }
    }

}