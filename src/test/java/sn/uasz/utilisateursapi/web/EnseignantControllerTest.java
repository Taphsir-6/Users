package sn.uasz.utilisateursapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.services.EnseignantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EnseignantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnseignantService enseignantService;

    private EnseignantDTO enseignantDTO;

    @BeforeEach
    void setUp() {
        enseignantDTO = new EnseignantDTO(
            1L,
            "Fall",
            "Moussa",
            "moussa.fall@uasz.sn",
            "770000000",
            "MAT123",
            "MCF",
            "admin",
            LocalDate.now(),
            true
        );
    }

    @Test
    void testAjouterEnseignant() throws Exception {
        Mockito.when(enseignantService.ajouterEnseignant(any(EnseignantDTO.class))).thenReturn(enseignantDTO);
        mockMvc.perform(post("/api/enseignants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enseignantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom", is("Fall")));
    }

    @Test
    void testListerEnseignants() throws Exception {
        List<EnseignantDTO> enseignants = Arrays.asList(enseignantDTO);
        Mockito.when(enseignantService.listerTousEnseignants()).thenReturn(enseignants);
        mockMvc.perform(get("/api/enseignants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("moussa.fall@uasz.sn")));
    }

    @Test
    void testObtenirEnseignant() throws Exception {
        Mockito.when(enseignantService.obtenirEnseignantParId(1L)).thenReturn(enseignantDTO);
        mockMvc.perform(get("/api/enseignants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom", is("Moussa")));
    }

    @Test
    void testModifierEnseignant() throws Exception {
        Mockito.when(enseignantService.modifierEnseignant(eq(1L), any(EnseignantDTO.class))).thenReturn(enseignantDTO);
        mockMvc.perform(put("/api/enseignants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enseignantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricule", is("MAT123")));
    }

    @Test
    void testSupprimerEnseignant() throws Exception {
        mockMvc.perform(delete("/api/enseignants/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testRechercherParNom() throws Exception {
        List<EnseignantDTO> enseignants = Arrays.asList(enseignantDTO);
        Mockito.when(enseignantService.rechercherEnseignantsParNom("Fall")).thenReturn(enseignants);
        mockMvc.perform(get("/api/enseignants/recherche?nom=Fall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom", is("Fall")));
    }

    @Test
    void testActiverEnseignant() throws Exception {
        Mockito.when(enseignantService.activerEnseignant(1L)).thenReturn(enseignantDTO);
        mockMvc.perform(put("/api/enseignants/1/activer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actif", is(true)));
    }

    @Test
    void testDesactiverEnseignant() throws Exception {
        EnseignantDTO enseignantInactif = new EnseignantDTO(
            enseignantDTO.id(),
            enseignantDTO.nom(),
            enseignantDTO.prenom(),
            enseignantDTO.email(),
            enseignantDTO.telephone(),
            enseignantDTO.matricule(),
            enseignantDTO.grade(),
            enseignantDTO.createBy(),
            enseignantDTO.createAt(),
            false
        );
        Mockito.when(enseignantService.desactiverEnseignant(1L)).thenReturn(enseignantInactif);
        mockMvc.perform(put("/api/enseignants/1/desactiver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actif", is(false)));
    }
}
