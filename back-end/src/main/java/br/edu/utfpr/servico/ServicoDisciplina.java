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
    private List<CursoDTO> cursos;
    private List<ProfessorDTO> professores;
    private List<DepartamentoDTO> departamentos;

    public ServicoDisciplina(){

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
            d.setCodigo(disciplina.getCodigo());
            d.setCurso(disciplina.getCurso());
            d.setProfessor(disciplina.getProfessor());
        });

        return ResponseEntity.of(disciplinaExistente);
    }
}

