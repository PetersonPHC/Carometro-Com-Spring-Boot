package br.fateczl.carometro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.fateczl.carometro.model.entities.Aluno;
import br.fateczl.carometro.service.implementations.AlunoServiceImp;
import jakarta.validation.Valid;

@Controller
public class AlunoHtmlController {

    @Autowired
    private AlunoServiceImp alunoService;

    @GetMapping("/alunoGet")
    public String alunoGet(Model model) {
        try {
            Aluno aluno = alunoService.buscar("1110482227778");
            model.addAttribute("a", aluno);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return "alunoGet";
    }
    
    @GetMapping("/alunoPost")
    public String alunoPost(Model model) {
    	Aluno aluno = new Aluno();
        model.addAttribute("aluno", aluno);
        return "alunoPost";
    }
    
    @PostMapping("/alunoPost")
    public String alunoPost(@ModelAttribute("aluno") Aluno aluno) {
    	alunoService.inserir(aluno);
    	return "aluno_inserido";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
    	model.addAttribute("message", "Isso é um Teste");
		return "home";
    	
    }
    
    
}