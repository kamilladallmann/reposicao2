package br.edu.utfpr.servico;

import br.edu.utfpr.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoRequerimento {

    private List<RequerimentoDTO> requerimentos;
    private List<ProfessorDTO> professores;
    private List<DepartamentoDTO> departamentos;
    private List<CursoDTO> cursos;
    private List<DisciplinaDTO> disciplinas;

    public ServicoRequerimento(){

        departamentos = Stream.of(
                DepartamentoDTO.builder().id(1).nome("DACOMP").build(),
                DepartamentoDTO.builder().id(2).nome("DAMAT").build(),
                DepartamentoDTO.builder().id(3).nome("DAELT").build()
        ).collect(Collectors.toList());

        professores = Stream.of(
              ProfessorDTO.builder().id(1).nome("Gabriel Costa").campus("Curitiba").siape("123456").build(),
              ProfessorDTO.builder().id(2).nome("Daniele Costa").campus("Cornélio Procópio").siape("67890").build(),
              ProfessorDTO.builder().id(3).nome("William Watanabe").campus("Cornélio Procópio").siape("34343").build()
        ).collect(Collectors.toList());

        cursos = Stream.of(
                CursoDTO.builder().id(1).departamento(departamentos.get(0)).nome("Engenharia de Software").build(),
                CursoDTO.builder().id(2).departamento(departamentos.get(1)).nome("Matemática").build(),
                CursoDTO.builder().id(3).departamento(departamentos.get(2)).nome("Engenharia Elétrica").build()
        ).collect(Collectors.toList());

        disciplinas = Stream.of(
                DisciplinaDTO.builder().id(1).codigo("IFL7K").nome("Arquitetura de Software").curso(cursos.get(0)).professor(professores.get(0)).build(),
                DisciplinaDTO.builder().id(2).codigo("IFL7I").nome("Programação Web 2").curso(cursos.get(0)).professor(professores.get(2)).build(),
                DisciplinaDTO.builder().id(3).codigo("CFL2A").nome("Cálculo I").curso(cursos.get(1)).professor(professores.get(1)).build()
        ).collect(Collectors.toList());

        requerimentos = Stream.of(
                RequerimentoDTO
                        .builder()
                        .id(1)
                        .dataAusencia("25/05/2019, 26/05/2019")
                        .dataReposicao("05/06/2019, 06/06/2019")
                        .aulasParaRepor("Introdução à RV, Aprofundamento em RV")
                        .qtddAulasTotal(5)
                        .disciplina(disciplinas.get(0))
                        .professor(professores.get(0))
                        .coordenador("Eduardo Cotrin")
                        .motivoAusencia("Apresentação de artigo")
                        .alunosConcordaram("Kamilla Dallmann Nunes, Gustavo Iotti, Thiago Meneghin")
                        .porcentagemConcordam(100.0)
                        .campus("Cornélio Procópio")
                        .build()
        ).collect(Collectors.toList());
    }

    @GetMapping("/servico/requerimento")
    public ResponseEntity<List<RequerimentoDTO>> listar() {
        return ResponseEntity.ok(requerimentos);
    }

    @GetMapping ("/servico/requerimento/{id}")
    public ResponseEntity<RequerimentoDTO> listarPorId(@PathVariable int id) {
        Optional<RequerimentoDTO> requerimentoEncontrado = requerimentos.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(requerimentoEncontrado);
    }

    @PostMapping("/servico/requerimento")
    public ResponseEntity<RequerimentoDTO> criar (@RequestBody RequerimentoDTO requerimento) {
        if((requerimento.getPorcentagemConcordam() < 80.0) || (requerimento.getQtddAulasTotal() > 10)){
            return ResponseEntity.status(400).body(null);
        }else{
            requerimento.setId(requerimentos.size() + 1);
            requerimentos.add(requerimento);
            return ResponseEntity.status(201).body(requerimento);
        }

    }

    @DeleteMapping("/servico/requerimento/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (requerimentos.removeIf(requerimento -> requerimento.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/requerimento/{id}")
    public ResponseEntity<RequerimentoDTO> alterar (@PathVariable int id, @RequestBody RequerimentoDTO requerimento){
        Optional<RequerimentoDTO> requerimentoExistente = requerimentos.stream().filter(c -> c.getId() == id).findAny();

        requerimentoExistente.ifPresent(r -> {
            r.setProfessor(requerimento.getProfessor());
            r.setAlunosConcordaram(requerimento.getAlunosConcordaram());
            r.setAulasParaRepor(requerimento.getAulasParaRepor());
            r.setCoordenador(requerimento.getCoordenador());
            r.setDisciplina(requerimento.getDisciplina());
            r.setMotivoAusencia(requerimento.getMotivoAusencia());
            r.setQtddAulasTotal(requerimento.getQtddAulasTotal());
            r.setDataAusencia(requerimento.getDataAusencia());
            r.setDataReposicao(requerimento.getDataReposicao());
            r.setPorcentagemConcordam(requerimento.getPorcentagemConcordam());
            r.setCampus(requerimento.getCampus());
        });

        return ResponseEntity.of(requerimentoExistente);
    }
}
