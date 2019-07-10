package br.edu.utfpr.servico;

import br.edu.utfpr.dto.ProfessorDTO;
import br.edu.utfpr.dto.RequerimentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoRequerimento {

    private List<RequerimentoDTO> requerimentos;

    public ServicoRequerimento(){

        requerimentos = Stream.of(
                RequerimentoDTO
                        .builder()
                        .id(1)
                        .dataAusencia("25/05/2019, 26/05/2019")
                        .dataReposicao("05/06/2019, 06/06/2019")
                        .aulasParaRepor("Introdução à RV, Aprofundamento em RV")
                        .qtddAulasTotal(5)
                        .disciplina("Realidade Virtual")
                        .professor("Eduardo Damasceno")
                        .coordenador("Eduardo Cotrin")
                        .motivoAusencia("Apresentação de artigo")
                        .alunosConcordaram("Kamilla Dallmann Nunes, Gustavo Iotti, Thiago Meneghin")
                        .porcentagemConcordam(100.0)
                        .departamento("DACOMP")
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

        requerimento.setId(requerimentos.size() + 1);
        requerimentos.add(requerimento);

        return ResponseEntity.status(201).body(requerimento);
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
            r.setDepartamento(requerimento.getDepartamento());
            r.setCampus(requerimento.getCampus());
        });

        return ResponseEntity.of(requerimentoExistente);
    }
}
