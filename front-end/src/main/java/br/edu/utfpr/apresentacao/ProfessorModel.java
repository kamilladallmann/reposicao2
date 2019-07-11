package br.edu.utfpr.apresentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorModel {
    private int id;
    private String nome;
    private String siape;
    private DepartamentoModel departamento;
    private int departamentoId;
    private String campus;
}
