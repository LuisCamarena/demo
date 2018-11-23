package com.demo.demo.controller;

import com.demo.demo.model.Persona;
import java.util.List;
import pe.albatross.octavia.dynatable.DynatableFilter;

public interface IndexService {

    public List<Persona> allByDynatable(DynatableFilter filter);

    void save(Persona persona);

    void update(Persona persona);

    void delete(Long id);

}
