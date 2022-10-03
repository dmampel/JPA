/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libreria.servicios;

import Libreria.entidades.Editorial;
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
public class EditorialServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA-LibreriaPU");
    EntityManager em = emf.createEntityManager();

    public Editorial crearEINgresarEditorial() throws Exception {
        try {
            Editorial e = new Editorial();
            System.out.println("Vamos a registrar una nueva editorial a la base de datos.");
            System.out.println("Ingrese el nombre.");
            String nombre = leer.next().toUpperCase();
            
            if(consultarEditorialPorNombre(nombre) != null){
                throw new Exception("Esta editorial ya está registrada.");
            }else{
                e.setNombre(nombre);
            }
            System.out.println("El ID será autogenerado al ingresar a la base.");
            System.out.println("El estado: ACTIVO.");
            e.setEstado(true);
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void darDeBajaEditorial(int id) throws Exception {
        try {
            
            Editorial e = em.find(Editorial.class, id);
            em.getTransaction().begin();
            e.setEstado(false);
            em.getTransaction().commit();

        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarEditorial() throws Exception {

        try {

            System.out.println("Ingresá el ID de la editorial que querés modificar.");
            int id = leer.nextInt();
            System.out.println("¿Qué atributo te gustaría modificar?");
            System.out.println("1- Nombre.");
            System.out.println("2- Estado. (ACTIVO/DE BAJA)");
            int respuesta = leer.nextInt();

            Editorial e = em.find(Editorial.class, id);
            switch (respuesta) {
                case 1:
                    System.out.println("El nombre actual es: " + e.getNombre());
                    System.out.println("Ingresá el nombre nuevo.");
                    String nombreNuevo = leer.next();
                    em.getTransaction().begin();
                    e.setNombre(nombreNuevo);
                    em.getTransaction().commit();
                    System.out.println("Nombre acualizado: " + e.getNombre());
                    break;
                case 2:
                    if (e.isEstado() == true) {
                        System.out.println("Estado actual: ACTIVO.");
                        System.out.println("Modificando....");
                        em.getTransaction().begin();
                        e.setEstado(false);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: DE BAJA.");
                    } else {
                        System.out.println("Estado actual: DE BAJA.");
                        System.out.println("Modificando...");
                        em.getTransaction().begin();
                        e.setEstado(true);
                        em.getTransaction().commit();
                        System.out.println("Estado actualizado: ACTIVO.");
                        break;
                    }

            }

        } catch (Exception e) {
            throw e;

        }
    }

    public void consultarEditorialPorId() throws Exception {

        try {
            System.out.println("Ingresá el ID de la editorial que querés consultar.");
            int id = leer.nextInt();
            Editorial e = (Editorial) em.find(Editorial.class, id);
            System.out.println("Id: " + e.getId());
            System.out.println("Nombre: " + e.getNombre());
            if (e.isEstado() == true) {
                System.out.println("Estado: ACTIVO.");
            } else {
                System.out.println("Estado: DE BAJA.");
            }

        } catch (Exception e) {
            throw e;
        }
    }
    
    public Editorial consultarEditorialPorNombre(String nombre) throws Exception {
        try {
            Editorial e = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            return e;
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    public void consultarEditorialPorEstado() throws Exception{
        try {
            System.out.println("1- Mostrar autores activos.");
            System.out.println("2- Consultar autores que están de baja.");
            int respuesta = leer.nextInt();
            Collection<Editorial> activos = new ArrayList();
            Collection<Editorial> deBaja = new ArrayList();
            
            switch (respuesta) {
                case 1:
                    activos = (Collection<Editorial>) em.createQuery("SELECT e FROM Editorial e WHERE e.estado = :estado").setParameter("estado", true).getResultList();
                    for (Editorial aux : activos) {
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
                    deBaja = (Collection<Editorial>) em.createQuery("SELECT e FROM Editorial e WHERE e.estado = :estado").setParameter("estado", false).getResultList();
                    for (Editorial aux : deBaja) {
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
