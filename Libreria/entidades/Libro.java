/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libreria.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author Delfina
 */
@Entity
public class Libro implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;
    private String titulo;
    private int año;
    private int totalEjemplares;
    private int ejemplaresPrestados;
    private int ejemplaresDisponibles;
    @OneToOne
    private Autor autor;
    @OneToOne
    private Editorial editorial;
    private boolean estado;

    public Libro() {
    }

    public Libro(long isbn, String titulo, int año, int totalEjemplares, int ejemplaresPrestados, int ejemplaresDisponibles, Autor autor, Editorial editorial, boolean estado) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.año = año;
        this.totalEjemplares = totalEjemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getTotalEjemplares() {
        return totalEjemplares;
    }

    public void setTotalEjemplares(int totalEjemplares) {
        this.totalEjemplares = totalEjemplares;
    }

    public int getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(int ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public int getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(int ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Libro{" + "isbn=" + isbn + ", titulo=" + titulo + ", a\u00f1o=" + año + ", totalEjemplares=" + totalEjemplares + ", ejemplaresPrestados=" + ejemplaresPrestados + ", ejemplaresDisponibles=" + ejemplaresDisponibles + ", autor=" + autor + ", editorial=" + editorial + ", estado=" + estado + '}';
    }
    
    
}
