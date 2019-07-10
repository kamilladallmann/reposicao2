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

@Controller
public class RequerimentoController {

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

        return "requerimento-view";
    }

    @PostMapping("/requerimento/criar")
    public String criar(RequerimentoModel requerimento) throws UnirestException {

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

        RequerimentoModel requerimentoExistente = new Gson()
                .fromJson(
                        Unirest
                                .get("http://localhost:9000/servico/requerimento/{id}")
                                .routeParam("id", String.valueOf(id))
                                .asJson()
                                .getBody()
                                .toString(),
                        RequerimentoModel.class
                );

        data.addAttribute("requerimentoAtual", requerimentoExistente);

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
