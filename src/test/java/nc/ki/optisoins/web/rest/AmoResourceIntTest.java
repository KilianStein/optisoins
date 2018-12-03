package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Amo;
import nc.ki.optisoins.repository.AmoRepository;
import nc.ki.optisoins.service.AmoService;
import nc.ki.optisoins.service.dto.AmoDTO;
import nc.ki.optisoins.service.mapper.AmoMapper;
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
 * Test class for the AmoResource REST controller.
 *
 * @see AmoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class AmoResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TARIF = 1;
    private static final Integer UPDATED_TARIF = 2;

    @Autowired
    private AmoRepository amoRepository;

    @Autowired
    private AmoMapper amoMapper;

    @Autowired
    private AmoService amoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmoMockMvc;

    private Amo amo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmoResource amoResource = new AmoResource(amoService);
        this.restAmoMockMvc = MockMvcBuilders.standaloneSetup(amoResource)
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
    public static Amo createEntity(EntityManager em) {
        Amo amo = new Amo()
            .code(DEFAULT_CODE)
            .tarif(DEFAULT_TARIF);
        return amo;
    }

    @Before
    public void initTest() {
        amo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmo() throws Exception {
        int databaseSizeBeforeCreate = amoRepository.findAll().size();

        // Create the Amo
        AmoDTO amoDTO = amoMapper.toDto(amo);
        restAmoMockMvc.perform(post("/api/amos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amoDTO)))
            .andExpect(status().isCreated());

        // Validate the Amo in the database
        List<Amo> amoList = amoRepository.findAll();
        assertThat(amoList).hasSize(databaseSizeBeforeCreate + 1);
        Amo testAmo = amoList.get(amoList.size() - 1);
        assertThat(testAmo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAmo.getTarif()).isEqualTo(DEFAULT_TARIF);
    }

    @Test
    @Transactional
    public void createAmoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amoRepository.findAll().size();

        // Create the Amo with an existing ID
        amo.setId(1L);
        AmoDTO amoDTO = amoMapper.toDto(amo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmoMockMvc.perform(post("/api/amos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amo in the database
        List<Amo> amoList = amoRepository.findAll();
        assertThat(amoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmos() throws Exception {
        // Initialize the database
        amoRepository.saveAndFlush(amo);

        // Get all the amoList
        restAmoMockMvc.perform(get("/api/amos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amo.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF)));
    }
    
    @Test
    @Transactional
    public void getAmo() throws Exception {
        // Initialize the database
        amoRepository.saveAndFlush(amo);

        // Get the amo
        restAmoMockMvc.perform(get("/api/amos/{id}", amo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF));
    }

    @Test
    @Transactional
    public void getNonExistingAmo() throws Exception {
        // Get the amo
        restAmoMockMvc.perform(get("/api/amos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmo() throws Exception {
        // Initialize the database
        amoRepository.saveAndFlush(amo);

        int databaseSizeBeforeUpdate = amoRepository.findAll().size();

        // Update the amo
        Amo updatedAmo = amoRepository.findById(amo.getId()).get();
        // Disconnect from session so that the updates on updatedAmo are not directly saved in db
        em.detach(updatedAmo);
        updatedAmo
            .code(UPDATED_CODE)
            .tarif(UPDATED_TARIF);
        AmoDTO amoDTO = amoMapper.toDto(updatedAmo);

        restAmoMockMvc.perform(put("/api/amos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amoDTO)))
            .andExpect(status().isOk());

        // Validate the Amo in the database
        List<Amo> amoList = amoRepository.findAll();
        assertThat(amoList).hasSize(databaseSizeBeforeUpdate);
        Amo testAmo = amoList.get(amoList.size() - 1);
        assertThat(testAmo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAmo.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    public void updateNonExistingAmo() throws Exception {
        int databaseSizeBeforeUpdate = amoRepository.findAll().size();

        // Create the Amo
        AmoDTO amoDTO = amoMapper.toDto(amo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmoMockMvc.perform(put("/api/amos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amo in the database
        List<Amo> amoList = amoRepository.findAll();
        assertThat(amoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmo() throws Exception {
        // Initialize the database
        amoRepository.saveAndFlush(amo);

        int databaseSizeBeforeDelete = amoRepository.findAll().size();

        // Get the amo
        restAmoMockMvc.perform(delete("/api/amos/{id}", amo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amo> amoList = amoRepository.findAll();
        assertThat(amoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amo.class);
        Amo amo1 = new Amo();
        amo1.setId(1L);
        Amo amo2 = new Amo();
        amo2.setId(amo1.getId());
        assertThat(amo1).isEqualTo(amo2);
        amo2.setId(2L);
        assertThat(amo1).isNotEqualTo(amo2);
        amo1.setId(null);
        assertThat(amo1).isNotEqualTo(amo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmoDTO.class);
        AmoDTO amoDTO1 = new AmoDTO();
        amoDTO1.setId(1L);
        AmoDTO amoDTO2 = new AmoDTO();
        assertThat(amoDTO1).isNotEqualTo(amoDTO2);
        amoDTO2.setId(amoDTO1.getId());
        assertThat(amoDTO1).isEqualTo(amoDTO2);
        amoDTO2.setId(2L);
        assertThat(amoDTO1).isNotEqualTo(amoDTO2);
        amoDTO1.setId(null);
        assertThat(amoDTO1).isNotEqualTo(amoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amoMapper.fromId(null)).isNull();
    }
}
