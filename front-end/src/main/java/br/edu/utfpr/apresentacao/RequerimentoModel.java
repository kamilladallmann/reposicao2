package br.edu.utfpr.apresentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequerimentoModel {
    private int id;
    private String dataAusencia;
    private String dataReposicao;
    private String professor;
    private String coordenador;
    private String disciplina;
    private String aulasParaRepor;
    private int qtddAulasTotal;
    private String alunosConcordaram;
    private String motivoAusencia;
    private double porcentagemConcordam;
    private String campus;
    private String departamento;
}
