package com.salesianostriana.classmatic.servicios;

import com.salesianostriana.classmatic.entidades.*;
import com.salesianostriana.classmatic.repositorios.ProfesorRepositorio;
import com.salesianostriana.classmatic.servicios.base.ServicioBaseImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProfesorServicio extends ServicioBaseImp<Profesor,Long, ProfesorRepositorio> {

    private final ProfesorRepositorio profesorReporitorio;

    public void editarAlumno(Alumno a, Alumno al, AlumnoServicio alumnoServicio) {
        a.setNombre(al.getNombre());
        a.setApellidos(al.getApellidos());
        a.setEmail(al.getEmail());
        a.setFechaNacimiento(al.getFechaNacimiento());
        alumnoServicio.edit(a);

    }

    public void editarProfesor(Profesor p, Profesor pr, ProfesorServicio profesorServicio) {
        p.setNombre(pr.getNombre());
        p.setApellidos(pr.getApellidos());
        p.setEmail(pr.getEmail());
        p.setFechaNacimiento(pr.getFechaNacimiento());
        p.setEsJefe(pr.isEsJefe());
        profesorServicio.edit(p);

    }

    public void editarTitulo(Titulo t, Titulo tit, TituloServicio tituloServicio){
        t.setNombre(tit.getNombre());
        t.setNivelAcademico(tit.getNivelAcademico());
        tituloServicio.edit(t);
    }

    public void borrarTitulo(TituloServicio tituloServicio, CursoServicio cursoServicio,
                             AlumnoServicio alumnoServicio, AsignaturaServicio asignaturaServicio,
                             Long id){

        List<Alumno> listaAl=new ArrayList<Alumno>();
        List<Asignatura> listaAs=new ArrayList<Asignatura>();
        List<Curso>listaCu=new ArrayList<Curso>();
        Titulo t=tituloServicio.findById(id);
       //desvincular titulo y sus cursos
        for(int j=0;j<t.getCursos().size();j++){

                //desvinculo curso y sus asignaturas
                if(!t.getCursos().get(j).getAsignaturas().isEmpty()){
                    for(int i=0;i<t.getCursos().get(j).getAsignaturas().size();i++){


                        listaAs.add(t.getCursos().get(j).getAsignaturas().get(i));
                    }
                    for(Asignatura as : listaAs){
                        t.getCursos().get(j).removeAsignatura(as);
                        asignaturaServicio.edit(as);
                    }
                }
                if(!t.getCursos().get(j).getAlumnos().isEmpty()){
                    for(int i=0;i<t.getCursos().get(j).getAlumnos().size();i++){


                        listaAl.add(t.getCursos().get(j).getAlumnos().get(i));

                    }
                    for(Alumno al : listaAl){
                        t.getCursos().get(j).removeAlumno(al);
                        alumnoServicio.edit(al);
                    }
                }
                listaCu.add(t.getCursos().get(j));
        }
        for(Curso cu : listaCu){
            t.removeCurso(cu);
            cursoServicio.edit(cu);
            cursoServicio.delete(cu);
        }
        tituloServicio.delete(t);
    }

    public void anyadirCurso(CursoServicio cursoServicio, TituloServicio tituloServicio, Curso curso, Long id){
        cursoServicio.save(curso);
        tituloServicio.findById(id).addCurso(curso);
        cursoServicio.edit(curso);
    }

    public void editarCurso(Curso c, Curso cur, CursoServicio cursoServicio){
        c.setNombre(cur.getNombre());
        cursoServicio.edit(c);
    }
}




