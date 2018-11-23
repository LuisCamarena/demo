package com.demo.demo.controller;

import com.demo.demo.model.Persona;
import com.demo.demo.zhelper.JsonResponse;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.albatross.octavia.dynatable.DynatableFilter;
import pe.albatross.octavia.dynatable.DynatableResponse;
import pe.albatross.zelpers.miscelanea.ExceptionHandler;
import pe.albatross.zelpers.miscelanea.JsonHelper;
import pe.albatross.zelpers.miscelanea.PhobosException;

@Controller
@RequestMapping("/")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IndexService service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        List<Persona> personas = new ArrayList();//service.allPersonas();
        model.addAttribute("lista", personas);

        return "indexz";
    }

    @ResponseBody
    @RequestMapping("allPersonas")
    public JsonResponse allPersonas() {

        JsonResponse response = new JsonResponse();
        List<Persona> personas = new ArrayList();//service.allPersonas();
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        response.setSuccess(false);
        try {
            for (Persona persona : personas) {
                ObjectNode json = new ObjectNode(JsonNodeFactory.instance);
                json.put("id", persona.getId());
                json.put("paterno", persona.getPaterno());
                json.put("materno", persona.getMaterno());
                json.put("nombres", persona.getNombres());
                json.put("sexo", persona.getSexo());
                arrayNode.add(json);
            }
            response.setData(arrayNode);
            response.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error en el llamado");
        }

        return response;

    }

    @ResponseBody
    @RequestMapping("list")
    public DynatableResponse list(DynatableFilter filter, HttpSession session) {
        DynatableResponse json = new DynatableResponse();
        try {

            JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
            List<Persona> personas = service.allByDynatable(filter);
//            List<Aporte> aportes = aporteService.allByDynatable(filter);
            ArrayNode array = new ArrayNode(jsonFactory);

            for (Persona pesona : personas) {
                array.add(JsonHelper.createJson(pesona, jsonFactory, true,
                        new String[]{
                            "*"
                        }));
            }

            json.setData(array);
            json.setTotal(filter.getTotal());
            json.setFiltered(filter.getFiltered());
        } catch (Exception e) {
            e.printStackTrace();
            json.setTotal(0);
        }
        return json;
    }

    @RequestMapping("prueba")
    public String prueba(Model model) {

        List<Persona> personas = new ArrayList();//service.allPersonas();
        model.addAttribute("lista", personas);

        return "prueba";
    }

    @ResponseBody
    @RequestMapping("save")
    public JsonResponse save(@RequestBody Persona persona, HttpSession session) {
        JsonResponse response = new JsonResponse();
        response.setSuccess(Boolean.FALSE);
        try {
//            DataSessionBienestar ds = (DataSessionBienestar) session.getAttribute(ConstantineBienestar.SESSION_USUARIO);
//            ObjectUtil.printAttr(persona);
//            persona.setCicloAcademico(ds.getCicloAcademico());
            if (persona.getId() == null) {
                service.save(persona);
                response.setMessage("Persona creada.");

            } else {
                service.update(persona);
                response.setMessage("Persona actualizada");
            }
            response.setSuccess(Boolean.TRUE);

        } catch (PhobosException e) {
            ExceptionHandler.handlePhobosEx(e, (RedirectAttributes) response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResponse delete(@RequestBody Persona persona) {
        JsonResponse response = new JsonResponse();
        try {
            service.delete(persona.getId());
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Persona eliminada.");

        } catch (PhobosException e) {
            ExceptionHandler.handlePhobosEx(e, (RedirectAttributes) response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
