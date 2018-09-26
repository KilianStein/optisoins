package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Telephone;
import nc.ki.optisoins.repository.TelephoneRepository;
import nc.ki.optisoins.service.TelephoneService;
import nc.ki.optisoins.service.dto.TelephoneDTO;
import nc.ki.optisoins.service.mapper.TelephoneMapper;
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

import nc.ki.optisoins.domain.enumeration.TypeTelephone;
/**
 * Test class for the TelephoneResource REST controller.
 *
 * @see TelephoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class TelephoneResourceIntTest {

    private static final String DEFAULT_NUMERO_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEPHONE = "BBBBBBBBBB";

    private static final TypeTelephone DEFAULT_TYPE = TypeTelephone.PERSONNEL;
    private static final TypeTelephone UPDATED_TYPE = TypeTelephone.PROFESSIONEL;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private TelephoneMapper telephoneMapper;
    
    @Autowired
    private TelephoneService telephoneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTelephoneMockMvc;

    private Telephone telephone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TelephoneResource telephoneResource = new TelephoneResource(telephoneService);
        this.restTelephoneMockMvc = MockMvcBuilders.standaloneSetup(telephoneResource)
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
    public static Telephone createEntity(EntityManager em) {
        Telephone telephone = new Telephone()
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .type(DEFAULT_TYPE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return telephone;
    }

    @Before
    public void initTest() {
        telephone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelephone() throws Exception {
        int databaseSizeBeforeCreate = telephoneRepository.findAll().size();

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);
        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telephone testTelephone = telephoneList.get(telephoneList.size() - 1);
        assertThat(testTelephone.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testTelephone.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephone.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createTelephoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telephoneRepository.findAll().size();

        // Create the Telephone with an existing ID
        telephone.setId(1L);
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneRepository.findAll().size();
        // set the field null
        telephone.setNumeroTelephone(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneRepository.findAll().size();
        // set the field null
        telephone.setType(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelephones() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        // Get all the telephoneList
        restTelephoneMockMvc.perform(get("/api/telephones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephone.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTelephone() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        // Get the telephone
        restTelephoneMockMvc.perform(get("/api/telephones/{id}", telephone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(telephone.getId().intValue()))
            .andExpect(jsonPath("$.numeroTelephone").value(DEFAULT_NUMERO_TELEPHONE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTelephone() throws Exception {
        // Get the telephone
        restTelephoneMockMvc.perform(get("/api/telephones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelephone() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        int databaseSizeBeforeUpdate = telephoneRepository.findAll().size();

        // Update the telephone
        Telephone updatedTelephone = telephoneRepository.findById(telephone.getId()).get();
        // Disconnect from session so that the updates on updatedTelephone are not directly saved in db
        em.detach(updatedTelephone);
        updatedTelephone
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .type(UPDATED_TYPE)
            .commentaire(UPDATED_COMMENTAIRE);
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(updatedTelephone);

        restTelephoneMockMvc.perform(put("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isOk());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeUpdate);
        Telephone testTelephone = telephoneList.get(telephoneList.size() - 1);
        assertThat(testTelephone.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testTelephone.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephone.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTelephone() throws Exception {
        int databaseSizeBeforeUpdate = telephoneRepository.findAll().size();

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneMockMvc.perform(put("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelephone() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        int databaseSizeBeforeDelete = telephoneRepository.findAll().size();

        // Get the telephone
        restTelephoneMockMvc.perform(delete("/api/telephones/{id}", telephone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telephone.class);
        Telephone telephone1 = new Telephone();
        telephone1.setId(1L);
        Telephone telephone2 = new Telephone();
        telephone2.setId(telephone1.getId());
        assertThat(telephone1).isEqualTo(telephone2);
        telephone2.setId(2L);
        assertThat(telephone1).isNotEqualTo(telephone2);
        telephone1.setId(null);
        assertThat(telephone1).isNotEqualTo(telephone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephoneDTO.class);
        TelephoneDTO telephoneDTO1 = new TelephoneDTO();
        telephoneDTO1.setId(1L);
        TelephoneDTO telephoneDTO2 = new TelephoneDTO();
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
        telephoneDTO2.setId(telephoneDTO1.getId());
        assertThat(telephoneDTO1).isEqualTo(telephoneDTO2);
        telephoneDTO2.setId(2L);
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
        telephoneDTO1.setId(null);
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(telephoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(telephoneMapper.fromId(null)).isNull();
    }
}
