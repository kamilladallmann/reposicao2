package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplinaDTO {
    private int id;
    private String nome;
    private String professor;
    private String curso;
}
