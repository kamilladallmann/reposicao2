package br.edu.utfpr.servico;

import br.edu.utfpr.dto.CursoDTO;
import br.edu.utfpr.dto.DepartamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class ServicoCurso {

    private List<CursoDTO> cursos;

    public ServicoCurso(){

        cursos = Stream.of(
                CursoDTO.builder()
                        .id(1)
                        .nome("Engenharia de Software")
                        .departamento(DepartamentoDTO.builder()
                                .id(1)
                                .nome("DACOMP")
                                .build())
                .build()
        ).collect(Collectors.toList());
    }

    @GetMapping("/servico/curso")
    public ResponseEntity<List<CursoDTO>> listar() {
        return ResponseEntity.ok(cursos);
    }

    @GetMapping ("/servico/curso/{id}")
    public ResponseEntity<CursoDTO> listarPorId(@PathVariable int id) {
        Optional<CursoDTO> cursoEncontrado = cursos.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(cursoEncontrado);
    }

    @PostMapping("/servico/curso")
    public ResponseEntity<CursoDTO> criar (@RequestBody CursoDTO curso) {

        curso.setId(cursos.size() + 1);
        cursos.add(curso);

        return ResponseEntity.status(201).body(curso);
    }

    @DeleteMapping ("/servico/curso/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (cursos.removeIf(curso -> curso.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/curso/{id}")
    public ResponseEntity<CursoDTO> alterar (@PathVariable int id, @RequestBody CursoDTO curso){
        Optional<CursoDTO> cursoExistente = cursos.stream().filter(c -> c.getId() == id).findAny();

        cursoExistente.ifPresent(c -> {
            c.setNome(curso.getNome());
            c.setDepartamento(curso.getDepartamento());
        });

        return ResponseEntity.of(cursoExistente);
    }

}

