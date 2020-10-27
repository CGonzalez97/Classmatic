package com.salesianostriana.classmatic.controladores;

import com.salesianostriana.classmatic.entidades.Alumno;
import com.salesianostriana.classmatic.entidades.Asignatura;
import com.salesianostriana.classmatic.entidades.SituacionExcepcional;
import com.salesianostriana.classmatic.entidades.Usuario;
import com.salesianostriana.classmatic.servicios.AlumnoServicio;
import com.salesianostriana.classmatic.servicios.AsignaturaServicio;
import com.salesianostriana.classmatic.servicios.CursoServicio;
import com.salesianostriana.classmatic.servicios.HorarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/alumno/")
public class AlumnoController {

    @Autowired
    AlumnoServicio alumnoServicio;

    @Autowired
    HorarioServicio horarioServicio;

    @Autowired
    AsignaturaServicio asignaturaServicio;

    @Autowired
    CursoServicio cursoServicio;

    /*@GetMapping("/alumnoIni")
    public String inicioAlumno(){
        return "alumno/alumnoIni";
    }*/


    @GetMapping("/alumnoIni")
    public String accederHorario(@AuthenticationPrincipal Alumno alumno, Model model){
        List<List<Asignatura>>listaCompleta=new ArrayList<List<Asignatura>>();
        listaCompleta=alumnoServicio.crearHorarioAlumno(alumno, horarioServicio);
        model.addAttribute("horario",listaCompleta);
        return "alumno/alumnoHorario";
    }

    @GetMapping("/convalidaciones")
    public String accederConvalidaciones(@AuthenticationPrincipal Alumno alumno, Model model){
        List<Asignatura>asignaturas;
        asignaturas=alumnoServicio.sacarAsignaturas(alumno, asignaturaServicio, cursoServicio);
        model.addAttribute("asignaturas",asignaturas);
        return "alumno/alumnoConvalidaciones";
    }

    @GetMapping("/convilidacionde/{i}")
    public String convalidarAsignatura(@PathVariable Long id, Model model, @AuthenticationPrincipal Alumno alumno){
        SituacionExcepcional sit=new SituacionExcepcional();
        sit.setAsignatura(asignaturaServicio.findById(id));
        sit.setFechaSolicitud(LocalDate.now());
        sit.setResuelta(false);
        sit.setAlumno(alumno);
        sit.setFechaResolucion(null);
        model.addAttribute("convalidacion", new SituacionExcepcional());
        model.addAttribute("asignatura",asignaturaServicio.findById(id));
        return "alumno/alumnoconvalidar";
    }

    @PostMapping("/convalidar/{i}")
    public String convalidar(@RequestParam("archivo") MultipartFile archivo, @PathVariable Long id,
                             Model model, @AuthenticationPrincipal Alumno alumno){
        if(archivo.isEmpty()){
            return convalidarAsignatura( id,  model,  alumno);
        }else{
            //implementar tratamiento de fichero
            //String rutaArchivo =
            return accederConvalidaciones( alumno,  model);
        }
    }

    @GetMapping("/ampliaciones")
    public String accederAmpliacion(){
        return "alumno/alumnoAmpliaciones";
    }
}
