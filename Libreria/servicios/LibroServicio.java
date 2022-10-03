/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libreria.servicios;

import Libreria.entidades.Autor;
import Libreria.entidades.Editorial;
import Libreria.entidades.Libro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Delfina
 */
public class LibroServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA-LibreriaPU");
    EntityManager em = emf.createEntityManager();
    AutorServicio as = new AutorServicio();
    EditorialServicio es = new EditorialServicio();

    public Libro crearEIngresarLibro() throws Exception {

        try {
            Libro l = new Libro();
            System.out.println("Vamos a ingresar un resistro de libro nuevo a la base de datos.");
            System.out.println("Ingrese la siguiente información:");
            System.out.println("Título del libro.");
            String titulo = leer.next().toUpperCase();
            if(consultarTitulo(titulo) != null){
                throw new Exception("Esta editorial ya está registrada.");
            }else{
                l.setTitulo(titulo);
            }   
            
            System.out.println("Año de publicación.");
            l.setAño(leer.nextInt());
            System.out.println("Cantidad total de ejemplares impresos.");
            l.setTotalEjemplares(leer.nextInt());
            Autor a = as.crearEIngresarAutor();
            l.setAutor(a);
            Editorial e = es.crearEINgresarEditorial();
            l.setEditorial(e);
            System.out.println("El ISBN se autogenera al momento de ingresar a la base.");
            System.out.println("Todavía no hay libros prestados.");
            l.setEstado(true);
            l.setEjemplaresPrestados(0);
            l.setEjemplaresDisponibles(l.getTotalEjemplares());
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
            return l;

        } catch (Exception e) {
            throw e;
        }
    }
    
    public Libro consultarTitulo(String titulo) throws Exception {
        try {
            Libro e = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo").setParameter("titulo", titulo).getSingleResult();
            return e;
        } catch (Exception e) {
            throw e;
        }
        
        
    }

    public void darBajaLibro() throws Exception {
        try {
            System.out.println("Ingresaste para dar de baja un libro.");
            System.out.println("Te recordamos que al dar de baja el libro también se dará de baja al autor y editorial.");
            System.out.println("Ingresá el ISBN del libro que querés dar de baja.");
            long isbn = leer.nextLong();

            Libro l = em.find(Libro.class, isbn);
            em.getTransaction().begin();
            l.setEstado(false);
            as.darBajaAutor(l.getAutor().getId());
            es.darDeBajaEditorial(l.getEditorial().getId());
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarLibro() throws Exception {

        try {
            System.out.println("Ingresá el ISBN del libro que querés modificar.");
            long isbn = leer.nextLong();
            System.out.println("Selecciona el atributo a modificar.");
            System.out.println("1- Título.");
            System.out.println("2- Año de publicación.");
            System.out.println("3- Cantidad total de ejemplares.");
            System.out.println("4- Cantidad de ejemplares prestados.");
            System.out.println("5- Cantidad de ejemplares disponibles.");
            System.out.println("6- Aspectos del Autor.");
            System.out.println("7- Aspectos de la Editorial.");
            System.out.println("8- Estado. (ACTIVO/DE BAJA)");
            int respuesta = leer.nextInt();
            Libro l = em.find(Libro.class, isbn);
            switch (respuesta) {
                case 1:
                    System.out.println("Título actual: " + l.getTitulo());
                    System.out.println("Ingresá el nuevo título por favor.");
                    String nuevoTitulo = leer.next();
                    em.getTransaction().begin();
                    l.setTitulo(nuevoTitulo);
                    em.getTransaction().commit();
                    System.out.println("Título actual: " + l.getTitulo());
                    break;
                case 2:
                    System.out.println("Año actual: " + l.getAño());
                    System.out.println("Ingresá el nuevo año por favor.");
                    int nuevoAño = leer.nextInt();
                    em.getTransaction().begin();
                    l.setAño(nuevoAño);
                    em.getTransaction().commit();
                    System.out.println("Año actual: " + l.getAño());
                    break;
                case 3:
                    System.out.println("Cantidad total de ejemplares actual: " + l.getTotalEjemplares());
                    System.out.println("Ingrese la nueva cantidad por favor.");
                    int cantidadNueva = leer.nextInt();
                    em.getTransaction().begin();
                    l.setTotalEjemplares(cantidadNueva);
                    em.getTransaction().commit();
                    System.out.println("Cantidad total de ejemplares actual: " + l.getTotalEjemplares());
                    break;
                case 4:
                    System.out.println("Cantidad de ejemplares prestados actual: " + l.getEjemplaresPrestados());
                    System.out.println("Ingrese la nueva cantidad por favor.");
                    int prestados = leer.nextInt();
                    em.getTransaction().begin();
                    l.setEjemplaresPrestados(prestados);
                    em.getTransaction().commit();
                    System.out.println("Cantidad total de ejemplares prestados actual: " + l.getEjemplaresPrestados());
                    break;
                case 5:
                    System.out.println("Cantidad de ejemplares disponibles actual: " + l.getEjemplaresDisponibles());
                    System.out.println("Ingrese la nueva cantidad por favor.");
                    int disponibles = leer.nextInt();
                    em.getTransaction().begin();
                    l.setEjemplaresDisponibles(disponibles);
                    em.getTransaction().commit();
                    System.out.println("Cantidad total de ejemplares disponibles actual: " + l.getEjemplaresDisponibles());
                    break;
                case 6:
                    as.modificarAutor();
                    break;
                case 7:
                    es.modificarEditorial();
                    break;
                case 8:
                    if (l.isEstado() == true) {
                        System.out.println("Estado actual: ACTIVO.");
                        System.out.println("Modificando....");
                        em.getTransaction().begin();
                        l.setEstado(false);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: DE BAJA.");
                    } else {
                        System.out.println("Estado actual: DE BAJA.");
                        System.out.println("Modificando...");
                        em.getTransaction().begin();
                        l.setEstado(true);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: ACTIVO.");
                        break;
                    }
            }
        } catch (Exception e) {
            throw e;

        }
    }

    public void consultarDisponibilidad(long isbn) throws Exception {

        try {
            Libro l = em.find(Libro.class, isbn);
            if (l.getEjemplaresDisponibles() == 0) {
                System.out.println("Lo sentimos. No quedan ejemplares de '" + l.getTitulo() + "' disponibles.");
            } else {
                System.out.println("Enhorabuena! Hay ejemplares disponibles. Tomá uno.");
                em.getTransaction().begin();
                l.setEjemplaresDisponibles(l.getEjemplaresDisponibles() - 1);
                l.setEjemplaresPrestados(l.getEjemplaresPrestados() + 1);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void alquilarLibro() throws Exception {
        try {
            System.out.println("Indicá el isbn del libro que te gustaría alquilar.");
            long isbn = leer.nextLong();
            System.out.println("Ok, aguardá un momento, vamos a chequear la disponibilidad.");
            consultarDisponibilidad(isbn);
        } catch (Exception e) {
            throw e;
        }
    }

    public void devolverLibro() throws Exception {
        try {
            System.out.println("Ingresá el isbn del libro que quieres devolver.");
            long isbn = leer.nextLong();
            Libro l = em.find(Libro.class, isbn);
            em.getTransaction().begin();
            l.setEjemplaresPrestados(l.getEjemplaresPrestados() - 1);
            l.setEjemplaresDisponibles(l.getEjemplaresDisponibles() + 1);
            em.getTransaction().commit();
            System.out.println("Libro devuelto. Muchas gracias!");

        } catch (Exception e) {
            throw e;
        }
    }

    public void consultarLibrosPorAutor() throws Exception {

        try {
            System.out.println("Nombre del Autor.");
            String autor = leer.next().toUpperCase();
            Collection<Libro> libros = new ArrayList();
            libros = (Collection<Libro>) em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre = :autor").setParameter("autor", autor).getResultList();
            System.out.println("Libros del autor " + autor);
            for (Libro aux : libros) {
                System.out.println(aux.getTitulo());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void consultarLibrosPorEditorial() throws Exception {

        try {
            System.out.println("Nombre de la Editorial.");
            String editorial = leer.next().toUpperCase();
            Collection<Libro> libros = new ArrayList();
            libros = (Collection<Libro>) em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre = :editorial").setParameter("editorial", editorial).getResultList();
            System.out.println("Libros de la editorial " + editorial);
            for (Libro aux : libros) {
                System.out.println(aux.getTitulo());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void consultarLibrosDisponibles() throws Exception {

        try {
            Collection<Libro> libros = new ArrayList();
            libros = (Collection<Libro>) em.createQuery("SELECT l FROM Libro l WHERE l.ejemplaresDisponibles > :disponibles").setParameter("disponibles", 0).getResultList();
            System.out.println("Libros disponibles:");
            for (Libro aux : libros) {
                System.out.println(aux.getTitulo());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void menu() throws Exception {

        try {
            int respuesta;
            do {
                System.out.println("Hola, bienvenido a la Libreria.");
                System.out.println("MENÚ...");
                System.out.println("1- Ingresar un nuevo libro.");
                System.out.println("2- Dar de baja un libro.");
                System.out.println("3- Modificar un libro.");
                System.out.println("4- Alquilar un libro.");
                System.out.println("5- Devolver un libro.");
                System.out.println("6- Consultar todos los libros disponibles.");
                System.out.println("7- Consultar libros por autor.");
                System.out.println("8- Consultar libros por editorial.");
                System.out.println("9- No hacer nada.");
                System.out.println("10- QUIERO SALIR POR FAVOR ODIO LEER.");
                System.out.println("¿Qué operación te gustaría realizar?");
                respuesta = leer.nextInt();

                switch (respuesta) {
                    case 1:
                        Libro l = crearEIngresarLibro();
                        break;
                    case 2:
                        darBajaLibro();
                        break;
                    case 3:
                        modificarLibro();
                        break;
                    case 4:
                        alquilarLibro();
                        break;
                    case 5:
                        devolverLibro();
                        break;
                    case 6:
                        consultarLibrosDisponibles();
                        break;
                    case 7:
                        consultarLibrosPorAutor();
                        break;
                    case 8:
                        consultarLibrosPorEditorial();
                        break;
                    case 9:
                        System.out.println("Nada.");
                        break;
                    case 10:
                        System.out.println("Ok, saliendo.");
                }

            } while (respuesta != 10);

        } catch (Exception e) {
            throw e;
        }
    }
}
