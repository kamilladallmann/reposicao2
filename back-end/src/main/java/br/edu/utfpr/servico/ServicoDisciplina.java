package br.edu.utfpr.servico;

import br.edu.utfpr.dto.CursoDTO;
import br.edu.utfpr.dto.DepartamentoDTO;
import br.edu.utfpr.dto.DisciplinaDTO;
import br.edu.utfpr.dto.ProfessorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoDisciplina {

    private List<DisciplinaDTO> disciplinas;

    public ServicoDisciplina(){

        disciplinas = Stream.of(
                DisciplinaDTO.builder()
                        .id(1)
                        .nome("Arquitetura de Software")
                        .curso("Engenharia de Software")
                        .professor("Gabriel Costa")
                .build()).collect(Collectors.toList());

    }

    @GetMapping("/servico/disciplina")
    public ResponseEntity<List<DisciplinaDTO>> listar() {
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/servico/disciplina/{id}")
    public ResponseEntity<DisciplinaDTO> listarPorId(@PathVariable int id) {
        Optional<DisciplinaDTO> disciplinaEncontrada = disciplinas.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(disciplinaEncontrada);
    }

    @PostMapping("/servico/disciplina")
    public ResponseEntity<DisciplinaDTO> criar (@RequestBody DisciplinaDTO disciplina) {

        disciplina.setId(disciplinas.size() + 1);
        disciplinas.add(disciplina);

        return ResponseEntity.status(201).body(disciplina);
    }

    @DeleteMapping ("/servico/disciplina/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (disciplinas.removeIf(disciplina -> disciplina.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/disciplina/{id}")
    public ResponseEntity<DisciplinaDTO> alterar (@PathVariable int id, @RequestBody DisciplinaDTO disciplina){
        Optional<DisciplinaDTO> disciplinaExistente = disciplinas.stream().filter(c -> c.getId() == id).findAny();

        disciplinaExistente.ifPresent(d -> {
            d.setNome(disciplina.getNome());
            d.setCurso(disciplina.getCurso());
            d.setProfessor(disciplina.getProfessor());
        });

        return ResponseEntity.of(disciplinaExistente);
    }
}

