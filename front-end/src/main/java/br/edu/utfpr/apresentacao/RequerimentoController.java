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
public class RequerimentoController {

    private DisciplinaModel arrayDisciplinas[] =  new DisciplinaModel[0];
    private ProfessorModel arrayProfessores[] =  new ProfessorModel[0];

    @GetMapping("/requerimento")
    public String inicial(Model data) throws JsonSyntaxException, UnirestException {

        RequerimentoModel arrayRequerimentos[] = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/requerimento")
                                .asJson()
                                .getBody()
                                .toString(),
                        RequerimentoModel[].class
                );

        data.addAttribute("requerimentos", arrayRequerimentos);

        arrayProfessores = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/professor")
                                .asJson()
                                .getBody()
                                .toString(),
                        ProfessorModel[].class
                );

        data.addAttribute("professores", arrayProfessores);


        arrayDisciplinas = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/disciplina")
                                .asJson()
                                .getBody()
                                .toString(),
                        DisciplinaModel[].class
                );

        data.addAttribute("disciplinas", arrayDisciplinas);

        return "requerimento-view";
    }

    @PostMapping("/requerimento/criar")
    public String criar(RequerimentoModel requerimento) throws UnirestException {

        for(int i=0;i <= arrayDisciplinas.length -1;i++) {
            if(arrayDisciplinas[i].getId() == requerimento.getDisciplinaId())
                requerimento.setDisciplina(arrayDisciplinas[i]);
        }

        for(int i=0;i <= arrayProfessores.length -1;i++) {
            if(arrayProfessores[i].getId() == requerimento.getProfessorId())
                requerimento.setProfessor(arrayProfessores[i]);
        }

        Unirest.post("http://localhost:9000/servico/requerimento")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(requerimento, RequerimentoModel.class))
                .asJson();

        return "redirect:/requerimento";
    }

    @GetMapping ("/requerimento/excluir")
    public String excluir (@RequestParam int id) throws UnirestException {

        Unirest
                .delete("http://localhost:9000/servico/requerimento/{id}")
                .routeParam("id", String.valueOf(id))
                .asJson();

        return "redirect:/requerimento";
    }

    @GetMapping ("/requerimento/prepara-alterar")
    public String preparaAlterar (@RequestParam int id, Model data) throws JsonSyntaxException, UnirestException {


        arrayDisciplinas = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/disciplina")
                                .asJson()
                                .getBody()
                                .toString(),
                        DisciplinaModel[].class
                );

        arrayProfessores = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/professor")
                                .asJson()
                                .getBody()
                                .toString(),
                        ProfessorModel[].class
                );
        

        RequerimentoModel arrayRequerimentos[] = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/requerimento")
                                .asJson()
                                .getBody()
                                .toString(),
                        RequerimentoModel[].class
                );

        Optional<RequerimentoModel> requerimentoExistenteOptional = Arrays.stream(arrayRequerimentos).filter(p -> p.getId() ==  id).findAny();
        RequerimentoModel requerimentoExistente = requerimentoExistenteOptional.get();

        data.addAttribute("requerimentoAtual", requerimentoExistente);
        data.addAttribute("professores", arrayProfessores);
        data.addAttribute("disciplinas", arrayDisciplinas);
        data.addAttribute("requerimentos", arrayRequerimentos);

        return "requerimento-view-alterar";
    }

    @PostMapping ("/requerimento/alterar")
    public String alterar (RequerimentoModel requerimentoAlterado) throws UnirestException {

        Unirest
                .put("http://localhost:9000/servico/requerimento/{id}")
                .routeParam("id", String.valueOf(requerimentoAlterado.getId()))
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new Gson().toJson(requerimentoAlterado, RequerimentoModel.class))
                .asJson();

        return "redirect:/requerimento";
    }
}
