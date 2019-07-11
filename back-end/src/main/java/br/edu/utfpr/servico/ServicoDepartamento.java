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
public class ServicoDepartamento {

    private List<DepartamentoDTO> departamentos;
    private List<ProfessorDTO> professores;

    public ServicoDepartamento(){

        departamentos = Stream.of(
                DepartamentoDTO.builder().id(1).nome("DACOMP").build(),
                DepartamentoDTO.builder().id(2).nome("DAMAT").build(),
                DepartamentoDTO.builder().id(3).nome("DAELT").build()
        ).collect(Collectors.toList());

    }

    @GetMapping("/servico/departamento")
    public ResponseEntity<List<DepartamentoDTO>> listar() {
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping ("/servico/departamento/{id}")
    public ResponseEntity<DepartamentoDTO> listarPorId(@PathVariable int id) {
        Optional<DepartamentoDTO> dptoEncontrado = departamentos.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(dptoEncontrado);
    }

    @PostMapping("/servico/departamento")
    public ResponseEntity<DepartamentoDTO> criar (@RequestBody DepartamentoDTO departamento) {

        departamento.setId(departamentos.size() + 1);
        departamentos.add(departamento);

        return ResponseEntity.status(201).body(departamento);
    }

    @DeleteMapping ("/servico/departamento/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (departamentos.removeIf(departamento -> departamento.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/departamento/{id}")
    public ResponseEntity<DepartamentoDTO> alterar (@PathVariable int id, @RequestBody DepartamentoDTO departamento){
        Optional<DepartamentoDTO> dptoExistente = departamentos.stream().filter(c -> c.getId() == id).findAny();

        dptoExistente.ifPresent(c -> {
            c.setNome(departamento.getNome());
        });

        return ResponseEntity.of(dptoExistente);
    }

}

