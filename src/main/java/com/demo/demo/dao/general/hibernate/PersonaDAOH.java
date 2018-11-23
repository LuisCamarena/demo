package com.demo.demo.dao.general.hibernate;

import com.demo.demo.dao.general.PersonaDAO;
import com.demo.demo.model.Persona;
import java.util.List;
import org.springframework.stereotype.Repository;
import pe.albatross.octavia.dynatable.DynatableFilter;
import pe.albatross.octavia.dynatable.DynatableSql;
import pe.albatross.octavia.easydao.AbstractEasyDAO;

@Repository
public class PersonaDAOH extends AbstractEasyDAO<Persona> implements PersonaDAO {

    public PersonaDAOH() {
        super();
        setClazz(Persona.class);
    }

    @Override
    public List<Persona> allByDynatable(DynatableFilter filter) {
       DynatableSql sql = new DynatableSql(filter)
                .from(Persona.class, "p")
//                .join("modalidadEstudio mo")
                .searchFields("p.nombres", "p.paterno","p.materno")
                .orderBy("p.paterno asc");
        return all(sql);
    }

}
