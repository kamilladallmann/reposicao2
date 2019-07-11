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
    private List<DepartamentoDTO> departamentos;

    public ServicoProfessor(){

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
            pr.setSiape(professor.getSiape());
            pr.setCampus(professor.getCampus());
        });

        return ResponseEntity.of(professorExistente);
    }
}

