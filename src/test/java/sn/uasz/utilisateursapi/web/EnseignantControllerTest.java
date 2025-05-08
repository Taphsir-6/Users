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
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.enums.Grade;
import sn.uasz.utilisateursapi.services.EnseignantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste le contrôleur REST pour les actions liées aux enseignants.
 * Utilise MockMvc pour tester les requêtes HTTP simulées.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EnseignantControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Utilisé pour simuler les requêtes HTTP

    @Autowired
    private ObjectMapper objectMapper;  // Sérialiseur/désérialiseur JSON pour les tests

    @MockBean
    private EnseignantService enseignantService;  // Service simulé pour interagir avec les enseignants

    private EnseignantDTO enseignantDTO;  // Objet représentant un enseignant à utiliser dans les tests

    /**
     * Configuration initiale avant chaque test.
     * Crée un enseignant fictif et des rôles pour les tests.
     */
    @BeforeEach
    void setUp() {
        // Création de rôles fictifs
        Role role = new Role();
        role.setId(1L);
        role.setLibelle("ROLE_USER");

        List<Role> roles = List.of(role);

        enseignantDTO = new EnseignantDTO(
                1L,
                "Fall",
                "Moussa",
                "moussa.fall@uasz.sn",
                "770000000",
                "MAT123",
                Grade.PROFESSEUR,
                roles,
                "admin",
                LocalDate.now(),
                true
        );
    }

    /**
     * Test de l'ajout d'un enseignant.
     * Vérifie que l'enseignant est bien créé et que les données sont retournées.
     */
    @Test
    void testAjouterEnseignant() throws Exception {
        // Simulation du service pour ajouter un enseignant
        Mockito.when(enseignantService.ajouterEnseignant(any(EnseignantDTO.class))).thenReturn(enseignantDTO);

        mockMvc.perform(post("/api/enseignants")  // Requête POST pour ajouter un enseignant
                        .contentType(MediaType.APPLICATION_JSON)  // Spécifie le type de contenu JSON
                        .content(objectMapper.writeValueAsString(enseignantDTO)))  // Sérialise l'enseignant en JSON
                .andExpect(status().isCreated())  // Vérifie que le code de statut HTTP est 201 (Créé)
                .andExpect(jsonPath("$.nom", is("Fall")));  // Vérifie que le nom de l'enseignant est correct
    }

    /**
     * Test de la récupération de la liste des enseignants.
     * Vérifie que tous les enseignants sont retournés correctement.
     */
    @Test
    void testListerEnseignants() throws Exception {
        List<EnseignantDTO> enseignants = List.of(enseignantDTO);
        // Simulation du service pour lister les enseignants
        Mockito.when(enseignantService.listerTousEnseignants()).thenReturn(enseignants);

        mockMvc.perform(get("/api/enseignants"))  // Requête GET pour récupérer tous les enseignants
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$[0].email", is("moussa.fall@uasz.sn")));  // Vérifie que l'email du premier enseignant est correct
    }

    /**
     * Test de la récupération d'un enseignant par ID.
     * Vérifie que l'enseignant est bien retourné par son ID.
     */
    @Test
    void testObtenirEnseignant() throws Exception {
        // Simulation du service pour obtenir un enseignant par ID
        Mockito.when(enseignantService.obtenirEnseignantParId(1L)).thenReturn(enseignantDTO);

        mockMvc.perform(get("/api/enseignants/1"))  // Requête GET pour récupérer l'enseignant avec ID 1
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$.prenom", is("Moussa")));  // Vérifie que le prénom de l'enseignant est correct
    }

    /**
     * Test de la modification des informations d'un enseignant.
     * Vérifie que l'enseignant est modifié avec succès.
     */
    @Test
    void testModifierEnseignant() throws Exception {
        // Simulation du service pour modifier un enseignant
        Mockito.when(enseignantService.modifierEnseignant(eq(1L), any(EnseignantDTO.class))).thenReturn(enseignantDTO);

        mockMvc.perform(put("/api/enseignants/1")  // Requête PUT pour modifier un enseignant avec ID 1
                        .contentType(MediaType.APPLICATION_JSON)  // Spécifie le type de contenu JSON
                        .content(objectMapper.writeValueAsString(enseignantDTO)))  // Sérialise l'enseignant modifié en JSON
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$.matricule", is("MAT123")));  // Vérifie que le matricule de l'enseignant est correct
    }

    /**
     * Test de la suppression d'un enseignant.
     * Vérifie que l'enseignant est supprimé correctement.
     */
    @Test
    void testSupprimerEnseignant() throws Exception {
        mockMvc.perform(delete("/api/enseignants/1"))  // Requête DELETE pour supprimer l'enseignant avec ID 1
                .andExpect(status().isNoContent());  // Vérifie que le code de statut HTTP est 204 (Pas de contenu)
    }

    /**
     * Test de la recherche d'un enseignant par son nom.
     * Vérifie que les enseignants correspondant au nom sont retournés.
     */
    @Test
    void testRechercherParNom() throws Exception {
        List<EnseignantDTO> enseignants = List.of(enseignantDTO);
        // Simulation du service pour rechercher les enseignants par nom
        Mockito.when(enseignantService.rechercherEnseignantsParNom("Fall")).thenReturn(enseignants);

        mockMvc.perform(get("/api/enseignants/recherche?nom=Fall"))  // Requête GET pour rechercher par nom
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$[0].nom", is("Fall")));  // Vérifie que le nom retourné est "Fall"
    }

    /**
     * Test de l'activation d'un enseignant.
     * Vérifie que l'enseignant est activé correctement.
     */
    @Test
    void testActiverEnseignant() throws Exception {
        // Simulation du service pour activer un enseignant
        Mockito.when(enseignantService.activerEnseignant(1L)).thenReturn(enseignantDTO);

        mockMvc.perform(put("/api/enseignants/1/activer"))  // Requête PUT pour activer un enseignant
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$.actif", is(true)));  // Vérifie que l'enseignant est marqué comme actif
    }

    /**
     * Test de la désactivation d'un enseignant.
     * Vérifie que l'enseignant est désactivé correctement.
     */
    @Test
    void testDesactiverEnseignant() throws Exception {
        // Création d'un enseignant désactivé
        EnseignantDTO enseignantInactif = new EnseignantDTO(
                enseignantDTO.id(),
                enseignantDTO.nom(),
                enseignantDTO.prenom(),
                enseignantDTO.email(),
                enseignantDTO.telephone(),
                enseignantDTO.matricule(),
                enseignantDTO.grade(),
                enseignantDTO.roles(),
                enseignantDTO.createBy(),
                enseignantDTO.createAt(),
                false
        );

        // Simulation du service pour désactiver un enseignant
        Mockito.when(enseignantService.desactiverEnseignant(1L)).thenReturn(enseignantInactif);

        mockMvc.perform(put("/api/enseignants/1/desactiver"))  // Requête PUT pour désactiver un enseignant
                .andExpect(status().isOk())  // Vérifie que le code de statut HTTP est 200 (OK)
                .andExpect(jsonPath("$.actif", is(false)));  // Vérifie que l'enseignant est marqué comme inactif
    }
}
