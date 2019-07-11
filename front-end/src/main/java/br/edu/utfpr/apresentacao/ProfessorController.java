package br.edu.utfpr.apresentacao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class ProfessorController {


    @GetMapping("/professor")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        ProfessorModel arrayProfessores[] = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/professor")
                                .asJson()
                                .getBody()
                                .toString(),
                        ProfessorModel[].class
                );

        data.addAttribute("professores", arrayProfessores);


        return "professor-view";
    }

    @PostMapping ("/professor/criar")
    public String criar(ProfessorModel professor) throws UnirestException {


        Unirest.post("http://localhost:9000/servico/professor")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(professor, ProfessorModel.class))
                .asJson();

        return "redirect:/professor";
    }

    @GetMapping ("/professor/excluir")
    public String excluir (@RequestParam int id) throws UnirestException {

        Unirest
                .delete("http://localhost:9000/servico/professor/{id}")
                .routeParam("id", String.valueOf(id))
                .asJson();

        return "redirect:/professor";
    }

    @GetMapping ("/professor/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {

        ProfessorModel arrayProfessores[] = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/professor")
                                .asJson()
                                .getBody()
                                .toString(),
                        ProfessorModel[].class
                );

        Optional<ProfessorModel> professorExistenteOptional = Arrays.stream(arrayProfessores).filter(p -> p.getId() ==  id).findAny();

        ProfessorModel professorExistente = professorExistenteOptional.get();

        data.addAttribute("professorAtual", professorExistente);
        data.addAttribute("professores", arrayProfessores);

        return "professor-view-alterar";
    }

    @PostMapping ("/professor/alterar")
    public String alterar (ProfessorModel professorAlterado) throws UnirestException {

        Unirest
                .put("http://localhost:9000/servico/professor/{id}")
                .routeParam("id", String.valueOf(professorAlterado.getId()))
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(professorAlterado, ProfessorModel.class))
                .asJson();

        return "redirect:/professor";
    }
}
