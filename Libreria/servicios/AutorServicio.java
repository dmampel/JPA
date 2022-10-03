/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libreria.servicios;

import Libreria.entidades.Autor;
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
public class AutorServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA-LibreriaPU");
    EntityManager em = emf.createEntityManager();

    public Autor crearEIngresarAutor() throws Exception {

        try {

            Autor a = new Autor();
            System.out.println("Vamos a registrar al nuevo Autor.");
            System.out.println("Por favor ingrese la siguiente información.");
            System.out.println("Nombre completo.");
            String nombre = leer.next().toUpperCase();
            if(consultarAutorPorNombre(nombre) != null){
                throw new Exception("Este autor ya está registrado.");
            }else{
                a.setNombre(nombre);
            }
            
            System.out.println("El ID será autogenerado.");
            System.out.println("El estado: ACTIVO.");
            a.setEstado(true);
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            return a;
        } catch (Exception e) {
            throw e;
        }
    }

    public void darBajaAutor(int id) throws Exception {
        try {

            Autor a = em.find(Autor.class, id);
            em.getTransaction().begin();
            a.setEstado(false);
            em.getTransaction().commit();

        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarAutor() throws Exception {

        try {

            System.out.println("Ingresá el ID del autor que querés modificar.");
            int id = leer.nextInt();
            System.out.println("¿Qué atributo te gustaría modificar?");
            System.out.println("1- Nombre.");
            System.out.println("2- Estado. (ACTIVO/DE BAJA)");
            int respuesta = leer.nextInt();

            Autor a = em.find(Autor.class, id);
            switch (respuesta) {
                case 1:
                    System.out.println("El nombre actual es: " + a.getNombre());
                    System.out.println("Ingresá el nombre nuevo.");
                    String nombreNuevo = leer.next();
                    em.getTransaction().begin();
                    a.setNombre(nombreNuevo);
                    em.getTransaction().commit();
                    System.out.println("Nombre acualizado: " + a.getNombre());
                    break;
                case 2:
                    if (a.isEstado() == true) {
                        System.out.println("Estado actual: ACTIVO.");
                        System.out.println("Modificando....");
                        em.getTransaction().begin();
                        a.setEstado(false);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: DE BAJA.");

                    } else {
                        System.out.println("Estado actual: DE BAJA.");
                        System.out.println("Modificando...");
                        em.getTransaction().begin();
                        a.setEstado(true);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: ACTIVO.");
                        break;
                    }

            }

        } catch (Exception e) {
            throw e;

        }
    }

    public void consultarAutorPorId() throws Exception {

        try {
            System.out.println("Ingresá el ID del autor que querés consultar.");
            int id = leer.nextInt();
            Autor a = (Autor) em.find(Autor.class, id);
            System.out.println("Id: " + a.getId());
            System.out.println("Nombre: " + a.getNombre());
            if (a.isEstado() == true) {
                System.out.println("Estado: ACTIVO.");
            } else {
                System.out.println("Estado: DE BAJA.");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public Autor consultarAutorPorNombre(String nombre) throws Exception {
        try {
            Autor a = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            return a;
        } catch (Exception e) {
            throw e;
        }
        
        
    }

    public void consultarAutorPorEstado() throws Exception {
        try {
            System.out.println("1- Mostrar autores activos.");
            System.out.println("2- Consultar autores que están de baja.");
            int respuesta = leer.nextInt();
            Collection<Autor> activos = new ArrayList();
            Collection<Autor> deBaja = new ArrayList();

            switch (respuesta) {
                case 1:
                    activos = (Collection<Autor>) em.createQuery("SELECT a FROM Autor a WHERE a.estado = :estado").setParameter("estado", true).getResultList();
                    for (Autor aux : activos) {
                        System.out.println("ID: " + aux.getId());
                        System.out.println("Nombre " + aux.getNombre());
                        if (aux.isEstado() == true) {
                            System.out.println("Estado: ACTIVO.");
                        } else {
                            System.out.println("Estado: DE BAJA.");
                        }
                    }
                    break;
                case 2:
                    deBaja = (Collection<Autor>) em.createQuery("SELECT a FROM Autor a WHERE a.estado = :estado").setParameter("estado", false).getResultList();
                    for (Autor aux : deBaja) {
                        System.out.println("ID: " + aux.getId());
                        System.out.println("Nombre " + aux.getNombre());
                        if (aux.isEstado() == true) {
                            System.out.println("Estado: ACTIVO.");
                        } else {
                            System.out.println("Estado: DE BAJA.");
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
