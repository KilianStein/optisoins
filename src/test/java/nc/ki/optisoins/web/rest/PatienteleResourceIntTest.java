package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Patientele;
import nc.ki.optisoins.repository.PatienteleRepository;
import nc.ki.optisoins.service.PatienteleService;
import nc.ki.optisoins.service.dto.PatienteleDTO;
import nc.ki.optisoins.service.mapper.PatienteleMapper;
import nc.ki.optisoins.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static nc.ki.optisoins.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PatienteleResource REST controller.
 *
 * @see PatienteleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class PatienteleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PatienteleRepository patienteleRepository;

    @Autowired
    private PatienteleMapper patienteleMapper;

    @Autowired
    private PatienteleService patienteleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatienteleMockMvc;

    private Patientele patientele;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatienteleResource patienteleResource = new PatienteleResource(patienteleService);
        this.restPatienteleMockMvc = MockMvcBuilders.standaloneSetup(patienteleResource)
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
    public static Patientele createEntity(EntityManager em) {
        Patientele patientele = new Patientele()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION);
        return patientele;
    }

    @Before
    public void initTest() {
        patientele = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientele() throws Exception {
        int databaseSizeBeforeCreate = patienteleRepository.findAll().size();

        // Create the Patientele
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(patientele);
        restPatienteleMockMvc.perform(post("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isCreated());

        // Validate the Patientele in the database
        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeCreate + 1);
        Patientele testPatientele = patienteleList.get(patienteleList.size() - 1);
        assertThat(testPatientele.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPatientele.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPatienteleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patienteleRepository.findAll().size();

        // Create the Patientele with an existing ID
        patientele.setId(1L);
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(patientele);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatienteleMockMvc.perform(post("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patientele in the database
        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = patienteleRepository.findAll().size();
        // set the field null
        patientele.setNom(null);

        // Create the Patientele, which fails.
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(patientele);

        restPatienteleMockMvc.perform(post("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isBadRequest());

        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = patienteleRepository.findAll().size();
        // set the field null
        patientele.setDescription(null);

        // Create the Patientele, which fails.
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(patientele);

        restPatienteleMockMvc.perform(post("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isBadRequest());

        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatienteles() throws Exception {
        // Initialize the database
        patienteleRepository.saveAndFlush(patientele);

        // Get all the patienteleList
        restPatienteleMockMvc.perform(get("/api/patienteles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientele.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPatientele() throws Exception {
        // Initialize the database
        patienteleRepository.saveAndFlush(patientele);

        // Get the patientele
        restPatienteleMockMvc.perform(get("/api/patienteles/{id}", patientele.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientele.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatientele() throws Exception {
        // Get the patientele
        restPatienteleMockMvc.perform(get("/api/patienteles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientele() throws Exception {
        // Initialize the database
        patienteleRepository.saveAndFlush(patientele);

        int databaseSizeBeforeUpdate = patienteleRepository.findAll().size();

        // Update the patientele
        Patientele updatedPatientele = patienteleRepository.findById(patientele.getId()).get();
        // Disconnect from session so that the updates on updatedPatientele are not directly saved in db
        em.detach(updatedPatientele);
        updatedPatientele
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(updatedPatientele);

        restPatienteleMockMvc.perform(put("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isOk());

        // Validate the Patientele in the database
        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeUpdate);
        Patientele testPatientele = patienteleList.get(patienteleList.size() - 1);
        assertThat(testPatientele.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatientele.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientele() throws Exception {
        int databaseSizeBeforeUpdate = patienteleRepository.findAll().size();

        // Create the Patientele
        PatienteleDTO patienteleDTO = patienteleMapper.toDto(patientele);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatienteleMockMvc.perform(put("/api/patienteles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patienteleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patientele in the database
        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatientele() throws Exception {
        // Initialize the database
        patienteleRepository.saveAndFlush(patientele);

        int databaseSizeBeforeDelete = patienteleRepository.findAll().size();

        // Get the patientele
        restPatienteleMockMvc.perform(delete("/api/patienteles/{id}", patientele.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patientele> patienteleList = patienteleRepository.findAll();
        assertThat(patienteleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patientele.class);
        Patientele patientele1 = new Patientele();
        patientele1.setId(1L);
        Patientele patientele2 = new Patientele();
        patientele2.setId(patientele1.getId());
        assertThat(patientele1).isEqualTo(patientele2);
        patientele2.setId(2L);
        assertThat(patientele1).isNotEqualTo(patientele2);
        patientele1.setId(null);
        assertThat(patientele1).isNotEqualTo(patientele2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatienteleDTO.class);
        PatienteleDTO patienteleDTO1 = new PatienteleDTO();
        patienteleDTO1.setId(1L);
        PatienteleDTO patienteleDTO2 = new PatienteleDTO();
        assertThat(patienteleDTO1).isNotEqualTo(patienteleDTO2);
        patienteleDTO2.setId(patienteleDTO1.getId());
        assertThat(patienteleDTO1).isEqualTo(patienteleDTO2);
        patienteleDTO2.setId(2L);
        assertThat(patienteleDTO1).isNotEqualTo(patienteleDTO2);
        patienteleDTO1.setId(null);
        assertThat(patienteleDTO1).isNotEqualTo(patienteleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patienteleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patienteleMapper.fromId(null)).isNull();
    }
}
