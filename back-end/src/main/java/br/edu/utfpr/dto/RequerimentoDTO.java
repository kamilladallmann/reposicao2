package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class RequerimentoDTO {
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
