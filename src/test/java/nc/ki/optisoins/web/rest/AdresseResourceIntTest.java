package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Adresse;
import nc.ki.optisoins.repository.AdresseRepository;
import nc.ki.optisoins.service.AdresseService;
import nc.ki.optisoins.service.dto.AdresseDTO;
import nc.ki.optisoins.service.mapper.AdresseMapper;
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

import nc.ki.optisoins.domain.enumeration.TypeAdresse;
/**
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class AdresseResourceIntTest {

    private static final String DEFAULT_APPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_APPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BATIMENT = "AAAAAAAAAA";
    private static final String UPDATED_BATIMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RUE = "AAAAAAAAAA";
    private static final String UPDATED_RUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_BOITE_POSTALE = "AAAAAAAAAA";
    private static final String UPDATED_BOITE_POSTALE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final TypeAdresse DEFAULT_TYPE = TypeAdresse.POSTALE;
    private static final TypeAdresse UPDATED_TYPE = TypeAdresse.DOMICILE;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NON_VALIDE = false;
    private static final Boolean UPDATED_NON_VALIDE = true;

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseMapper adresseMapper;
    
    @Autowired
    private AdresseService adresseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresseResource adresseResource = new AdresseResource(adresseService);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
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
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .appartement(DEFAULT_APPARTEMENT)
            .batiment(DEFAULT_BATIMENT)
            .rue(DEFAULT_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .boitePostale(DEFAULT_BOITE_POSTALE)
            .commune(DEFAULT_COMMUNE)
            .type(DEFAULT_TYPE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .nonValide(DEFAULT_NON_VALIDE);
        return adresse;
    }

    @Before
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAppartement()).isEqualTo(DEFAULT_APPARTEMENT);
        assertThat(testAdresse.getBatiment()).isEqualTo(DEFAULT_BATIMENT);
        assertThat(testAdresse.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testAdresse.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdresse.getBoitePostale()).isEqualTo(DEFAULT_BOITE_POSTALE);
        assertThat(testAdresse.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testAdresse.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAdresse.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testAdresse.isNonValide()).isEqualTo(DEFAULT_NON_VALIDE);
    }

    @Test
    @Transactional
    public void createAdresseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse with an existing ID
        adresse.setId(1L);
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setType(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].appartement").value(hasItem(DEFAULT_APPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].batiment").value(hasItem(DEFAULT_BATIMENT.toString())))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].boitePostale").value(hasItem(DEFAULT_BOITE_POSTALE.toString())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].nonValide").value(hasItem(DEFAULT_NON_VALIDE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.appartement").value(DEFAULT_APPARTEMENT.toString()))
            .andExpect(jsonPath("$.batiment").value(DEFAULT_BATIMENT.toString()))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.boitePostale").value(DEFAULT_BOITE_POSTALE.toString()))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.nonValide").value(DEFAULT_NON_VALIDE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).get();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .appartement(UPDATED_APPARTEMENT)
            .batiment(UPDATED_BATIMENT)
            .rue(UPDATED_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .boitePostale(UPDATED_BOITE_POSTALE)
            .commune(UPDATED_COMMUNE)
            .type(UPDATED_TYPE)
            .commentaire(UPDATED_COMMENTAIRE)
            .nonValide(UPDATED_NON_VALIDE);
        AdresseDTO adresseDTO = adresseMapper.toDto(updatedAdresse);

        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAppartement()).isEqualTo(UPDATED_APPARTEMENT);
        assertThat(testAdresse.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testAdresse.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testAdresse.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresse.getBoitePostale()).isEqualTo(UPDATED_BOITE_POSTALE);
        assertThat(testAdresse.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testAdresse.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAdresse.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAdresse.isNonValide()).isEqualTo(UPDATED_NON_VALIDE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresse.class);
        Adresse adresse1 = new Adresse();
        adresse1.setId(1L);
        Adresse adresse2 = new Adresse();
        adresse2.setId(adresse1.getId());
        assertThat(adresse1).isEqualTo(adresse2);
        adresse2.setId(2L);
        assertThat(adresse1).isNotEqualTo(adresse2);
        adresse1.setId(null);
        assertThat(adresse1).isNotEqualTo(adresse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresseDTO.class);
        AdresseDTO adresseDTO1 = new AdresseDTO();
        adresseDTO1.setId(1L);
        AdresseDTO adresseDTO2 = new AdresseDTO();
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO2.setId(adresseDTO1.getId());
        assertThat(adresseDTO1).isEqualTo(adresseDTO2);
        adresseDTO2.setId(2L);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO1.setId(null);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adresseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adresseMapper.fromId(null)).isNull();
    }
}
