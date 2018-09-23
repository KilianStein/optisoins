package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Orthophoniste;
import nc.ki.optisoins.repository.OrthophonisteRepository;
import nc.ki.optisoins.service.OrthophonisteService;
import nc.ki.optisoins.service.dto.OrthophonisteDTO;
import nc.ki.optisoins.service.mapper.OrthophonisteMapper;
import nc.ki.optisoins.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static nc.ki.optisoins.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrthophonisteResource REST controller.
 *
 * @see OrthophonisteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class OrthophonisteResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CAFAT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CAFAT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_RIDET = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_RIDET = "BBBBBBBBBB";

    @Autowired
    private OrthophonisteRepository orthophonisteRepository;

    @Mock
    private OrthophonisteRepository orthophonisteRepositoryMock;

    @Autowired
    private OrthophonisteMapper orthophonisteMapper;
    

    @Mock
    private OrthophonisteService orthophonisteServiceMock;

    @Autowired
    private OrthophonisteService orthophonisteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrthophonisteMockMvc;

    private Orthophoniste orthophoniste;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrthophonisteResource orthophonisteResource = new OrthophonisteResource(orthophonisteService);
        this.restOrthophonisteMockMvc = MockMvcBuilders.standaloneSetup(orthophonisteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orthophoniste createEntity(EntityManager em) {
        Orthophoniste orthophoniste = new Orthophoniste()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numeroCafat(DEFAULT_NUMERO_CAFAT)
            .numeroRidet(DEFAULT_NUMERO_RIDET);
        return orthophoniste;
    }

    @Before
    public void initTest() {
        orthophoniste = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrthophoniste() throws Exception {
        int databaseSizeBeforeCreate = orthophonisteRepository.findAll().size();

        // Create the Orthophoniste
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(orthophoniste);
        restOrthophonisteMockMvc.perform(post("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isCreated());

        // Validate the Orthophoniste in the database
        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeCreate + 1);
        Orthophoniste testOrthophoniste = orthophonisteList.get(orthophonisteList.size() - 1);
        assertThat(testOrthophoniste.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testOrthophoniste.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testOrthophoniste.getNumeroCafat()).isEqualTo(DEFAULT_NUMERO_CAFAT);
        assertThat(testOrthophoniste.getNumeroRidet()).isEqualTo(DEFAULT_NUMERO_RIDET);
    }

    @Test
    @Transactional
    public void createOrthophonisteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orthophonisteRepository.findAll().size();

        // Create the Orthophoniste with an existing ID
        orthophoniste.setId(1L);
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(orthophoniste);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrthophonisteMockMvc.perform(post("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orthophoniste in the database
        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = orthophonisteRepository.findAll().size();
        // set the field null
        orthophoniste.setNom(null);

        // Create the Orthophoniste, which fails.
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(orthophoniste);

        restOrthophonisteMockMvc.perform(post("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isBadRequest());

        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = orthophonisteRepository.findAll().size();
        // set the field null
        orthophoniste.setPrenom(null);

        // Create the Orthophoniste, which fails.
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(orthophoniste);

        restOrthophonisteMockMvc.perform(post("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isBadRequest());

        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrthophonistes() throws Exception {
        // Initialize the database
        orthophonisteRepository.saveAndFlush(orthophoniste);

        // Get all the orthophonisteList
        restOrthophonisteMockMvc.perform(get("/api/orthophonistes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orthophoniste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numeroCafat").value(hasItem(DEFAULT_NUMERO_CAFAT.toString())))
            .andExpect(jsonPath("$.[*].numeroRidet").value(hasItem(DEFAULT_NUMERO_RIDET.toString())));
    }
    
    public void getAllOrthophonistesWithEagerRelationshipsIsEnabled() throws Exception {
        OrthophonisteResource orthophonisteResource = new OrthophonisteResource(orthophonisteServiceMock);
        when(orthophonisteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOrthophonisteMockMvc = MockMvcBuilders.standaloneSetup(orthophonisteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrthophonisteMockMvc.perform(get("/api/orthophonistes?eagerload=true"))
        .andExpect(status().isOk());

        verify(orthophonisteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllOrthophonistesWithEagerRelationshipsIsNotEnabled() throws Exception {
        OrthophonisteResource orthophonisteResource = new OrthophonisteResource(orthophonisteServiceMock);
            when(orthophonisteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOrthophonisteMockMvc = MockMvcBuilders.standaloneSetup(orthophonisteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrthophonisteMockMvc.perform(get("/api/orthophonistes?eagerload=true"))
        .andExpect(status().isOk());

            verify(orthophonisteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrthophoniste() throws Exception {
        // Initialize the database
        orthophonisteRepository.saveAndFlush(orthophoniste);

        // Get the orthophoniste
        restOrthophonisteMockMvc.perform(get("/api/orthophonistes/{id}", orthophoniste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orthophoniste.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numeroCafat").value(DEFAULT_NUMERO_CAFAT.toString()))
            .andExpect(jsonPath("$.numeroRidet").value(DEFAULT_NUMERO_RIDET.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrthophoniste() throws Exception {
        // Get the orthophoniste
        restOrthophonisteMockMvc.perform(get("/api/orthophonistes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrthophoniste() throws Exception {
        // Initialize the database
        orthophonisteRepository.saveAndFlush(orthophoniste);

        int databaseSizeBeforeUpdate = orthophonisteRepository.findAll().size();

        // Update the orthophoniste
        Orthophoniste updatedOrthophoniste = orthophonisteRepository.findById(orthophoniste.getId()).get();
        // Disconnect from session so that the updates on updatedOrthophoniste are not directly saved in db
        em.detach(updatedOrthophoniste);
        updatedOrthophoniste
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numeroCafat(UPDATED_NUMERO_CAFAT)
            .numeroRidet(UPDATED_NUMERO_RIDET);
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(updatedOrthophoniste);

        restOrthophonisteMockMvc.perform(put("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isOk());

        // Validate the Orthophoniste in the database
        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeUpdate);
        Orthophoniste testOrthophoniste = orthophonisteList.get(orthophonisteList.size() - 1);
        assertThat(testOrthophoniste.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testOrthophoniste.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testOrthophoniste.getNumeroCafat()).isEqualTo(UPDATED_NUMERO_CAFAT);
        assertThat(testOrthophoniste.getNumeroRidet()).isEqualTo(UPDATED_NUMERO_RIDET);
    }

    @Test
    @Transactional
    public void updateNonExistingOrthophoniste() throws Exception {
        int databaseSizeBeforeUpdate = orthophonisteRepository.findAll().size();

        // Create the Orthophoniste
        OrthophonisteDTO orthophonisteDTO = orthophonisteMapper.toDto(orthophoniste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrthophonisteMockMvc.perform(put("/api/orthophonistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orthophonisteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orthophoniste in the database
        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrthophoniste() throws Exception {
        // Initialize the database
        orthophonisteRepository.saveAndFlush(orthophoniste);

        int databaseSizeBeforeDelete = orthophonisteRepository.findAll().size();

        // Get the orthophoniste
        restOrthophonisteMockMvc.perform(delete("/api/orthophonistes/{id}", orthophoniste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orthophoniste> orthophonisteList = orthophonisteRepository.findAll();
        assertThat(orthophonisteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orthophoniste.class);
        Orthophoniste orthophoniste1 = new Orthophoniste();
        orthophoniste1.setId(1L);
        Orthophoniste orthophoniste2 = new Orthophoniste();
        orthophoniste2.setId(orthophoniste1.getId());
        assertThat(orthophoniste1).isEqualTo(orthophoniste2);
        orthophoniste2.setId(2L);
        assertThat(orthophoniste1).isNotEqualTo(orthophoniste2);
        orthophoniste1.setId(null);
        assertThat(orthophoniste1).isNotEqualTo(orthophoniste2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrthophonisteDTO.class);
        OrthophonisteDTO orthophonisteDTO1 = new OrthophonisteDTO();
        orthophonisteDTO1.setId(1L);
        OrthophonisteDTO orthophonisteDTO2 = new OrthophonisteDTO();
        assertThat(orthophonisteDTO1).isNotEqualTo(orthophonisteDTO2);
        orthophonisteDTO2.setId(orthophonisteDTO1.getId());
        assertThat(orthophonisteDTO1).isEqualTo(orthophonisteDTO2);
        orthophonisteDTO2.setId(2L);
        assertThat(orthophonisteDTO1).isNotEqualTo(orthophonisteDTO2);
        orthophonisteDTO1.setId(null);
        assertThat(orthophonisteDTO1).isNotEqualTo(orthophonisteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orthophonisteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orthophonisteMapper.fromId(null)).isNull();
    }
}
