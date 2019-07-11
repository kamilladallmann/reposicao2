package br.edu.utfpr.apresentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaModel {
    private int id;
    private String codigo;
    private String nome;
    private ProfessorModel professor;
    private CursoModel curso;

}
