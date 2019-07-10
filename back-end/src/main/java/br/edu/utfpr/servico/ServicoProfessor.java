package br.edu.utfpr.servico;

import br.edu.utfpr.dto.DepartamentoDTO;
import br.edu.utfpr.dto.ProfessorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoProfessor {

    private List<ProfessorDTO> professores;

    public ServicoProfessor(){

        professores = Stream.of(
                ProfessorDTO
                        .builder()
                        .id(1)
                        .nome("Gabriel Costa")
                        .siape("1111")
                        .departamento("DACOMP")
                        .campus("Cornélio Procópio")
                        .build()
        ).collect(Collectors.toList());
    }

    @GetMapping("/servico/professor")
    public ResponseEntity<List<ProfessorDTO>> listar() {
        return ResponseEntity.ok(professores);
    }

    @GetMapping ("/servico/professor/{id}")
    public ResponseEntity<ProfessorDTO> listarPorId(@PathVariable int id) {
        Optional<ProfessorDTO> professorEncontrado = professores.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(professorEncontrado);
    }

    @PostMapping("/servico/professor")
    public ResponseEntity<ProfessorDTO> criar (@RequestBody ProfessorDTO professor) {

        professor.setId(professores.size() + 1);
        professores.add(professor);

        return ResponseEntity.status(201).body(professor);
    }

    @DeleteMapping ("/servico/professor/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (professores.removeIf(professor -> professor.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/professor/{id}")
    public ResponseEntity<ProfessorDTO> alterar (@PathVariable int id, @RequestBody ProfessorDTO professor){
        Optional<ProfessorDTO> professorExistente = professores.stream().filter(c -> c.getId() == id).findAny();

        professorExistente.ifPresent(pr -> {
            pr.setNome(professor.getNome());
            pr.setDepartamento(professor.getDepartamento());
            pr.setSiape(professor.getSiape());
            pr.setCampus(professor.getCampus());
        });

        return ResponseEntity.of(professorExistente);
    }
}

