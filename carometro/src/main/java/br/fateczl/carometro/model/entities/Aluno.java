package br.fateczl.carometro.model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Aluno implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Transient
    private static final DateTimeFormatter  ANO_MES_DIA = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    @Transient
    private static  final DateTimeFormatter  ANO_MES = DateTimeFormatter.ofPattern("yyyy/MM");

    @Id
    private String ra;
    private String nome;
    private String curso;
    private String semestreConclusao;
    private String foto;

    @ElementCollection
    @CollectionTable(name = "aluno_links", joinColumns = @JoinColumn(name = "aluno_ra"))
    @MapKeyColumn(name = "tipo_link")
    @Column(name = "url")
    private Map<String, String> links;

    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Historico historico;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @MapKeyColumn(name = "tipo")
    private Map<String, Comentario> comentarios;
    
    public Aluno(String ra) {
        this.ra = ra;
    }

    public Aluno() {
        links = new HashMap<String, String>();
        comentarios = new HashMap<String, Comentario>();
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSemestreConclusao() {
        return  semestreConclusao;
    }

    public void setSemestreConclusao(String semestreConclusao) {

        this.semestreConclusao = semestreConclusao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public void putLink(String chave, String link) {
        links.put(chave, link);
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
        historico.setAluno(this);
    }

    public Map<String, Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Map<String, Comentario> comentarios) {
        this.comentarios = comentarios;
        comentarios.values().forEach(c -> c.setAluno(this));
    }

    public void putComentarios(Comentario comentario) {
        this.comentarios.put(comentario.getTipo().toString(), comentario);
        comentario.setAluno(this);
    }

    @Override
    public String toString() {
        StringBuilder comentariosStr = new StringBuilder();
        for(String tipo : comentarios.keySet()){
            comentariosStr.append(tipo);
            comentariosStr.append(": ");
            comentariosStr.append(comentarios.get(tipo));
        }
        return "Aluno [ra=" + ra + ", nome=" + nome + ", curso=" + curso + ", semestreConclusao=" + getSemestreConclusao()
                + ", foto=" + foto + ", links=" + links + ",\nhistorico=" + historico + ", \ncomentarios={"
                + comentariosStr.toString() + "}]";
    }

}
