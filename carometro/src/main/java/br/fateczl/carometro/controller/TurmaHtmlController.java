package br.fateczl.carometro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.fateczl.carometro.model.entities.Aluno;
import br.fateczl.carometro.model.entities.Curso;
import br.fateczl.carometro.model.entities.Turma;
import br.fateczl.carometro.model.enums.Enum_TurnosCursos;
import br.fateczl.carometro.model.primarykeysclass.TurmaId;
import br.fateczl.carometro.service.implementations.AlunoServiceImp;
import br.fateczl.carometro.service.implementations.CursoServiceImp;
import br.fateczl.carometro.service.implementations.TurmaServiceImp;

@Controller
public class TurmaHtmlController {

	@Autowired
	private TurmaServiceImp turmaService;

	@Autowired
	private CursoServiceImp cursoService;

	@SuppressWarnings("unused")
	@Autowired
	private AlunoServiceImp alunoService;
	
	private ArrayList<String> links = new ArrayList<>();

	// HOME
	@GetMapping("/turmaHome")
	public String home() {
		return "turmaHome";
	}

	// POST
	@GetMapping("/turmaPost")
	public String turmaPost(Model model) throws ClassNotFoundException {
		TurmaId turmaId = new TurmaId();
		model.addAttribute("turmaId", turmaId);

		List<Curso> cursos = cursoService.buscarTodos(); 
	    model.addAttribute("cursos", cursos);
	    
	    model.addAttribute("turnos", Enum_TurnosCursos.values());

		return "turmaPost";
	}

	@PostMapping("/turmaPost")
	public String turmaPost(@ModelAttribute("turmaId") TurmaId turmaId) throws ClassNotFoundException {
		Curso curso = cursoService.buscar(turmaId.getCodigoCurso());
		Turma turma = new Turma();
		turma.setTurmaId(turmaId);
		turma.setCurso(curso);
		turmaService.inserir(turma);
		return "turma_inserida";
	}
	
	// DELETE
	@GetMapping("/turmaDelete")
	public String deletarTurma(Model model) throws ClassNotFoundException {
		TurmaId turmaId = new TurmaId();
		model.addAttribute("turmaId", turmaId);

		List<Curso> cursos = cursoService.buscarTodos(); 
	    model.addAttribute("cursos", cursos);
	    
	    model.addAttribute("turnos", Enum_TurnosCursos.values());
	    
		return "turmaDelete";
	}
	
	@PostMapping("/turmaDelete")
	public String deletarTurma(@ModelAttribute("turmaId") TurmaId turmaId, Model model) throws ClassNotFoundException {
		turmaService.deletar(turmaId.getCodigoCurso(), turmaId.getAno(), turmaId.getSemestre(), turmaId.getTurno());
		model.addAttribute("message","Deletada");
		return "turma_deletada";
	}
	
	// LIST
	@GetMapping("/turmaList")
	public String listarTodasAsTurmas(Model model) throws ClassNotFoundException {
		List<Turma> turmas = turmaService.buscarTodasAsTurmasExistentes();
		for(Turma turma: turmas) {
			System.out.println("**********************" + turma.toString());
		}
		List<TurmaId> turmasId = new ArrayList<>();
		for(Turma turma : turmas) {
			turmasId.add(turma.getTurmaId());
		}
		model.addAttribute("turmasId", turmasId);
		return "turmaList";
	}

}
