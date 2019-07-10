package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfessorDTO {
    private int id;
    private String nome;
    private String siape;
    private String departamento;
    private String campus;
}

