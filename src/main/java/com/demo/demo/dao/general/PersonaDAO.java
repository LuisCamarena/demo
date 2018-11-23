package com.demo.demo.dao.general;

import com.demo.demo.model.Persona;
import java.util.List;
import pe.albatross.octavia.dynatable.DynatableFilter;
import pe.albatross.octavia.easydao.EasyDAO;

public interface PersonaDAO extends EasyDAO<Persona> {

    List<Persona> allByDynatable(DynatableFilter filter);

}
