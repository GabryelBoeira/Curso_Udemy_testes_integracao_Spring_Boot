package com.br.gabryel.ContatosMockMvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.gabryel.ContatosMockMvc.model.Contato;
import com.br.gabryel.ContatosMockMvc.servive.ContatoService;

@Controller
@RequestMapping("/agenda")
public class AgendaController {
	
	@Autowired
	private ContatoService contatoService;
	
	@GetMapping("/")
	public ModelAndView getContatos(ModelAndView mav) {
		
		mav.addObject("contatos", contatoService.buscarContatos());
		return mav;
	}
	
	@GetMapping("/contato/{id}")
	public ModelAndView getContato(@PathVariable("id") Long id, ModelAndView mav) {
		
		mav.setViewName("agenda/contato");
		mav.addObject("contato", contatoService.buscarContato(id));
		return mav;
	}
	
	@DeleteMapping("/remover/{id}")
	public ModelAndView deleteContato(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
		
		contatoService.remover(id);
		redirectAttributes.addFlashAttribute("successMessage", "Contato removido com sucesso");
		return new ModelAndView("redirect:agenda/");
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro(ModelAndView mav) {
		
		mav.addObject("contato", new Contato());
		return mav;
	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Contato contato, BindingResult bibBindingResult, ModelAndView mav) {
		
		if(!bibBindingResult.hasErrors()) {
			contatoService.inserir(contato);
			mav.addObject("successMessage", "Contato cadastrado com sucesso");
		}
		
		return mav;		
	}
}
