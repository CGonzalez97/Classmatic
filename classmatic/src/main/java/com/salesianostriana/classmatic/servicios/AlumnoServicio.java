package com.salesianostriana.classmatic.servicios;

import com.salesianostriana.classmatic.entidades.Alumno;
import com.salesianostriana.classmatic.entidades.Asignatura;
import com.salesianostriana.classmatic.entidades.Horario;
import com.salesianostriana.classmatic.repositorios.AlumnoRepositorio;
import com.salesianostriana.classmatic.servicios.base.ServicioBaseImp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class AlumnoServicio extends ServicioBaseImp<Alumno, Long, AlumnoRepositorio> {

    private final AlumnoRepositorio alumnoRepositorio;

    public List<List<Asignatura>> crearHorarioAlumno(Alumno al, HorarioServicio horarioServicio){
        List<Asignatura> listaAsignaturas=new ArrayList<Asignatura>();
        List<Horario>listaHorarios=new ArrayList<Horario>();

        //Creo lista horarios y asignaturas
        /*for(Asignatura as: al.getAsignaturas()){
            listaAsignaturas.add(as);

        }*/
        listaAsignaturas.addAll(al.getAsignaturas());

        for(Asignatura a : listaAsignaturas){
            listaHorarios.addAll(a.getHorarios());
            /*for(Horario h : a.getHorarios()){
                listaHorarios.add(h);
            }*/
        }
        List<Integer>listaHoras=new ArrayList<Integer>();
        for(Horario horario : listaHorarios){
            listaHoras.addAll(horarioServicio.obtenerHoras(horario));
        }


        List<List<Asignatura>>listaCompleta= new ArrayList();
        for(int i=0;i<5;i++){
            listaCompleta.add(new ArrayList<Asignatura>());
        }
        for(List l : listaCompleta){//relleno las 6 h de lo 5 d con falso
            for(int i=0;i<6;i++){
                l.add(false);
            }
        }

        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                for(Horario h : listaHorarios){
                    if(h.getDia()==(i+1)){
                        for(int hora : h.getHoras()){
                            if(hora==(j+1)){
                                listaCompleta.get(i).set(j,h.getAsignatura());
                                //System.out.println("Asignatura introducida "+h.getAsignatura().getNombre());
                                //System.out.println("Asignatura almacenada "+listaCompleta.get(i).get(j).getNombre());
                            }
                        }
                    }
                }
            }
        }
        /*
        for(List dia : listaCompleta){
            for(Asignatura as : dia){
                System.out.println();
            }
        }*/

        return listaCompleta;
    }



}
