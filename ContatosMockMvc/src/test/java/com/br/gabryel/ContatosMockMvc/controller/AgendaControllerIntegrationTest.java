package com.br.gabryel.ContatosMockMvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Description;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import com.br.gabryel.ContatosMockMvc.model.Contato;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class AgendaControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestEntityManager testEntityManager;

	private Contato contato;

	@BeforeEach
	public void start() throws Exception {

		contato = new Contato("41", "123451234", "Chefe");
		testEntityManager.persist(contato);
	}

	@AfterEach
	public void stop() {

		testEntityManager.getEntityManager().createQuery("DELETE FROM Contato").executeUpdate();
	}

	@Test
	public void deveMostrarTodosOsContatos() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/************* Utilizando MockMvcResultMatchers ************************/

	@Test
	@Description("utilizando MockMvcResultMatchers ")
	public void verificarStatusRequsicaoMockMvcResultMatchers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		StatusResultMatchers statusResult = MockMvcResultMatchers.status();

		resultActions.andExpect(statusResult.isOk());

		resultActions.andExpect(statusResult.is(200));

		resultActions.andExpect(statusResult.is(Matchers.is(200)));
	}

	@Test
	@Description("utilizando MockMvcResultMatchers ")
	public void verificarViewRequisicaoMockMvcResultMatchers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		ViewResultMatchers viewResult = MockMvcResultMatchers.view();

		resultActions.andExpect(viewResult.name("agenda"));

		resultActions.andExpect(viewResult.name(Matchers.is("agenda")));
	}

	@Test
	@Description("utilizando MockMvcResultMatchers ")
	public void verificarResultadoRequisicaoMockMvcResultMatchers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		ModelResultMatchers modelResult = MockMvcResultMatchers.model();

		resultActions.andExpect(modelResult.attributeExists("contatos"));

		resultActions.andExpect(modelResult.attribute("contatos", Matchers.hasSize(1)));

		resultActions.andExpect(modelResult.attribute("contatos",
				Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(contato.getId())),
						Matchers.hasProperty("nome", Matchers.is(contato.getNome())),
						Matchers.hasProperty("ddd", Matchers.is(contato.getDdd())),
						Matchers.hasProperty("telefone", Matchers.is(contato.getTelefone()))))));

	}

	/************* Utilizando MockMvcResultHandlers ************************/

	@Test
	@Description("utilizando MockMvcResultHandlers")
	public void verificarStatusRequsicaoMockMvcResultHandlers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		resultActions.andDo(MockMvcResultHandlers.print());

		Integer status = resultActions.andReturn().getResponse().getStatus();

		assertEquals(200, status);
	}

	@Test
	@Description("utilizando MockMvcResultHandlers ")
	public void verificarViewRequisicaoMockMvcResultHandlers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		resultActions.andDo(MockMvcResultHandlers.print());

		ModelAndView mav = resultActions.andReturn().getModelAndView();

		assertEquals("agenda", mav.getViewName());
	}

	@Test
	@Description("utilizando MockMvcResultHandlers ")
	public void verificarResultadoRequisicaoMockMvcResultHandlers() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		resultActions.andDo(MockMvcResultHandlers.print());

		ModelAndView mav = resultActions.andReturn().getModelAndView();

		@SuppressWarnings("unchecked")
		List<Contato> contatos = (List<Contato>) mav.getModel().get("contatos");

		assertEquals(1, contatos.size());
		assertTrue(contatos.contains(contato));
	}

	/*************
	 * Utilizando Todos os conseitos anteriores juntos
	 ************************/

	@Test
	public void deveMostrarTodosOsContatosFormaStatic() throws Exception {

		mockMvc.perform(get("/agenda/")).andExpect(status().isOk()).andExpect(view().name("agenda"))
				.andExpect(model().attribute("contatos", Matchers.hasSize(1)))
				.andExpect(model().attribute("contatos",
						Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(contato.getId())),
								Matchers.hasProperty("nome", Matchers.is(contato.getNome())),
								Matchers.hasProperty("ddd", Matchers.is(contato.getDdd())),
								Matchers.hasProperty("telefone", Matchers.is(contato.getTelefone()))))))
				.andDo(print());
	}

	@Test
	public void deveMostrarUmContato() throws Exception {

		// Buscando objeto persistindo  anteriormente 
		Query queryContato = testEntityManager.getEntityManager().createQuery("SELECT c FROM Contato c");
		Contato resultContato = (Contato) queryContato.getResultList().get(0);

		mockMvc.perform(get("/agenda/contato/{id}", resultContato.getId())).andExpect(status().isOk())
				.andExpect(view().name("agenda/contato"))
				.andExpect(model().attribute("contato", Matchers.any(Contato.class)))
				.andExpect(model().attribute("contato", contato)).andDo(print());
	}

	@Test
	public void removerDeveExcluirContato() throws Exception {
		
		// Buscando objeto persistindo  anteriormente 
		Query queryContato = testEntityManager.getEntityManager().createQuery("SELECT c FROM Contato c");
		Contato resultContato = (Contato) queryContato.getResultList().get(0);
		
		mockMvc.perform(delete("/agenda/remover/{id}", resultContato.getId())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:agenda/"))
				.andExpect(flash().attribute("successMessage", "Contato removido com sucesso")).andDo(print());

		Query query = testEntityManager.getEntityManager().createQuery("SELECT c FROM Contato c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> query.getResultList().get(0));
	}

}