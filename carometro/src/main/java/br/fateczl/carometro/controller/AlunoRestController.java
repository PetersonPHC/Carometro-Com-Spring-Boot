package br.fateczl.carometro.controller;

import br.fateczl.carometro.model.entities.Aluno;
import br.fateczl.carometro.service.implementations.AlunoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoRestController {

    @Autowired
    private AlunoServiceImp alunoService;


    @GetMapping
    public ResponseEntity<Iterable<Aluno>> buscarTodosOsAlunos(){
        return ResponseEntity.ok(alunoService.buscarTodos());
    }

    @GetMapping("/{ra}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable String ra) throws ClassNotFoundException {
        return ResponseEntity.ok(alunoService.buscar(ra));
    }

    @PostMapping
    public ResponseEntity<Aluno> inserirAluno(@RequestBody Aluno aluno) throws ClassNotFoundException {
        Aluno novoAluno = alunoService.inserir(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{ra}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable String ra, @RequestBody Aluno novosDados) throws ClassNotFoundException {
        Aluno alunoAtualizado = alunoService.atualizar(ra, novosDados);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Aluno> deletarAluno(@PathVariable String name) throws ClassNotFoundException {
        alunoService.deletar(name);
        return ResponseEntity.ok().build();
    }

}
