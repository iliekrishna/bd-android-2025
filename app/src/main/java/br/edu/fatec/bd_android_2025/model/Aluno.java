package br.edu.fatec.bd_android_2025.model;

import androidx.annotation.NonNull;

public class Aluno {
        // Atributos
        private Integer id;
        private String nome;
        private String cpf;
        private String telefone;

        // construtores

        public Aluno() {
        }

        public Aluno(Integer id, String nome, String cpf, String telefone) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        @NonNull
        @Override
        public String toString() {
            return "\n" + id + "\n" + nome +  "\n" + cpf + "\n" + telefone + "\n";
        }
}

