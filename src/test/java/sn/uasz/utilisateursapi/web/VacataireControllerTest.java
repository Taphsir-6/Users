package sn.uasz.utilisateursapi.web;

import com.fasterxml.jackson.databind.ObjectMapper; // Pour convertir les objets en JSON
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.exceptions.VacataireNotFoundException;
import sn.uasz.utilisateursapi.services.VacataireService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given; // Style BDD pour Mockito
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de test d'intégration pour {@link sn.uasz.utilisateursapi.web.VacataireController}.
 * <p>
 * Vérifie le bon fonctionnement de tous les endpoints REST liés à la gestion des vacataires :
 * <ul>
 *   <li>Création, récupération, modification, suppression</li>
 *   <li>Désactivation et réactivation</li>
 *   <li>Gestion des erreurs et statuts HTTP</li>
 * </ul>
 * Utilise {@link MockMvc} pour simuler les requêtes HTTP et {@link MockBean} pour isoler le service métier.
 * </p>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
// Cible le test sur VacataireController uniquement
@WebMvcTest(VacataireController.class)
@AutoConfigureMockMvc(addFilters = false)
class VacataireControllerTest {

    @Autowired
    private MockMvc mockMvc; // Pour simuler les requêtes HTTP

    @MockBean // Crée un mock du service et l'injecte dans le contexte de test
    private VacataireService vacataireService;

    @Autowired(required = false)
    private ObjectMapper objectMapper; // Utilitaire Spring pour la conversion JSON <-> Java

    private VacataireDTO vacataireDTO;
    private VacataireDTO vacataireDTOUpdated;

    // Méthode exécutée avant chaque test pour initialiser les données communes
    @BeforeEach
    void setUp() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        vacataireDTO = new VacataireDTO();
        vacataireDTO.setId(1L);
        vacataireDTO.setNom("Diop");
        vacataireDTO.setPrenom("Omar");
        vacataireDTO.setEmail("omar.diop@example.com");
        // Initialisez d'autres champs si nécessaire

        vacataireDTOUpdated = new VacataireDTO();
        vacataireDTOUpdated.setId(1L);
        vacataireDTOUpdated.setNom("Diop");
        vacataireDTOUpdated.setPrenom("Omar Updated");
        vacataireDTOUpdated.setEmail("omar.updated@example.com");
    }

    @Test
    void creerVacataire_shouldReturnCreatedAndVacataireDTO() throws Exception {
        // Arrange: Configurer le comportement du mock service
        given(vacataireService.creerVacataire(any(VacataireDTO.class))).willReturn(vacataireDTO);

        // Act: Exécuter la requête POST simulée
        ResultActions response = mockMvc.perform(post("/api/vacataires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacataireDTO))); // Convertir l'objet en JSON

        // Assert: Vérifier la réponse
        response.andExpect(status().isCreated()) // Statut HTTP 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(vacataireDTO.getId().intValue()))) // Vérifier l'ID dans le JSON
                .andExpect(jsonPath("$.nom", is(vacataireDTO.getNom()))) // Vérifier le nom
                .andExpect(jsonPath("$.email", is(vacataireDTO.getEmail()))); // Vérifier l'email
    }

    @Test
    void getVacataire_whenExists_shouldReturnOkAndVacataireDTO() throws Exception {
        // Arrange
        long vacataireId = 1L;
        given(vacataireService.getVacataire(vacataireId)).willReturn(vacataireDTO);

        // Act
        ResultActions response = mockMvc.perform(get("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isOk()) // Statut HTTP 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(vacataireDTO.getId().intValue())))
                .andExpect(jsonPath("$.nom", is(vacataireDTO.getNom())));
    }

    @Test
    void getVacataire_whenNotFoundByServiceNull_shouldReturnNotFound() throws Exception {
        // Arrange: Cas où le service retourne null (test de la logique interne du contrôleur)
        long vacataireId = 99L;
        given(vacataireService.getVacataire(vacataireId)).willReturn(null);

        // Act
        ResultActions response = mockMvc.perform(get("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isNotFound()); // Statut HTTP 404
    }

    @Test
    void getVacataire_whenNotFoundByServiceException_shouldReturnNotFound() throws Exception {
        // Arrange: Cas où le service lève une exception (test du try-catch dans le contrôleur)
        long vacataireId = 99L;
        given(vacataireService.getVacataire(vacataireId)).willThrow(new VacataireNotFoundException("Vacataire non trouvé avec l'ID: " + vacataireId));

        // Act
        ResultActions response = mockMvc.perform(get("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isNotFound()); // Statut HTTP 404
    }

    @Test
    void mettreAJourVacataire_whenExists_shouldReturnOkAndUpdatedDTO() throws Exception {
        // Arrange
        long vacataireId = 1L;
        given(vacataireService.mettreAJourVacataire(eq(vacataireId), any(VacataireDTO.class)))
                .willReturn(vacataireDTOUpdated);

        // Act
        ResultActions response = mockMvc.perform(put("/api/vacataires/{id}", vacataireId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacataireDTOUpdated)));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(vacataireDTOUpdated.getId().intValue())))
                .andExpect(jsonPath("$.prenom", is(vacataireDTOUpdated.getPrenom()))) // Vérifier le champ mis à jour
                .andExpect(jsonPath("$.email", is(vacataireDTOUpdated.getEmail())));
    }

    @Test
    void mettreAJourVacataire_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange: Test de l'exception handler via le service
        long vacataireId = 99L;
        String errorMessage = "Impossible de mettre à jour, vacataire non trouvé: " + vacataireId;
        given(vacataireService.mettreAJourVacataire(eq(vacataireId), any(VacataireDTO.class)))
                .willThrow(new VacataireNotFoundException(errorMessage));

        // Act
        ResultActions response = mockMvc.perform(put("/api/vacataires/{id}", vacataireId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacataireDTO))); // Le contenu n'importe pas vraiment ici

        // Assert
        response.andExpect(status().isNotFound()) // Statut 404 géré par @ExceptionHandler
                .andExpect(content().string(errorMessage)); // Vérifie le message d'erreur dans le corps
    }


    @Test
    void supprimerVacataire_whenExists_shouldReturnNoContent() throws Exception {
        // Arrange
        long vacataireId = 1L;
        // Configuration pour le cas où la suppression réussit (le service retourne true)
        given(vacataireService.supprimerVacataire(vacataireId)).willReturn(true);

        // Act
        ResultActions response = mockMvc.perform(delete("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isNoContent()); // Statut HTTP 204
    }

    @Test
    void supprimerVacataire_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange
        long vacataireId = 99L;
        // Configuration pour le cas où la suppression échoue (le service retourne false)
        given(vacataireService.supprimerVacataire(vacataireId)).willReturn(false);

        // Act
        ResultActions response = mockMvc.perform(delete("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isNotFound()); // Statut HTTP 404
    }


    @Test
    void getAllVacatairesActifs_shouldReturnOkAndListOfDTOs() throws Exception {
        // Arrange
        List<VacataireDTO> listVacataires = Collections.singletonList(vacataireDTO);
        given(vacataireService.getAllVacatairesActifs()).willReturn(listVacataires);

        // Act
        ResultActions response = mockMvc.perform(get("/api/vacataires"));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(listVacataires.size()))) // Vérifie la taille de la liste JSON
                .andExpect(jsonPath("$[0].id", is(vacataireDTO.getId().intValue()))); // Vérifie l'ID du premier élément
    }

    @Test
    void desactiverVacataire_whenExists_shouldReturnOkAndDTO() throws Exception {
        // Arrange
        long vacataireId = 1L;
        VacataireDTO deactivatedDto = new VacataireDTO(); // Simule le DTO retourné après désactivation
        deactivatedDto.setId(vacataireId);
        deactivatedDto.setActif(false); // Supposons qu'il y a un champ actif
        given(vacataireService.desactiverVacataire(vacataireId)).willReturn(deactivatedDto);

        // Act
        ResultActions response = mockMvc.perform(post("/api/vacataires/{id}/desactiver", vacataireId));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(deactivatedDto.getId().intValue())));
        // Ajoutez .andExpect(jsonPath("$.actif", is(false))); si le champ existe et est retourné
    }

    @Test
    void desactiverVacataire_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange
        long vacataireId = 99L;
        String errorMessage = "Impossible de désactiver, vacataire non trouvé: " + vacataireId;
        given(vacataireService.desactiverVacataire(vacataireId)).willThrow(new VacataireNotFoundException(errorMessage));

        // Act
        ResultActions response = mockMvc.perform(post("/api/vacataires/{id}/desactiver", vacataireId));

        // Assert
        response.andExpect(status().isNotFound()) // Géré par @ExceptionHandler
                .andExpect(content().string(errorMessage));
    }

    @Test
    void reactivierVacataire_whenExists_shouldReturnOkAndDTO() throws Exception {
        // Arrange
        long vacataireId = 1L;
        VacataireDTO reactivatedDto = new VacataireDTO();
        reactivatedDto.setId(vacataireId);
        reactivatedDto.setActif(true); // Supposons qu'il y a un champ actif
        given(vacataireService.reactivierVacataire(vacataireId)).willReturn(reactivatedDto);

        // Act
        ResultActions response = mockMvc.perform(post("/api/vacataires/{id}/reactiver", vacataireId));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(reactivatedDto.getId().intValue())));
        // Ajoutez .andExpect(jsonPath("$.actif", is(true))); si le champ existe et est retourné
    }

    @Test
    void reactivierVacataire_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange
        long vacataireId = 99L;
        String errorMessage = "Impossible de réactiver, vacataire non trouvé: " + vacataireId;
        given(vacataireService.reactivierVacataire(vacataireId)).willThrow(new VacataireNotFoundException(errorMessage));

        // Act
        ResultActions response = mockMvc.perform(post("/api/vacataires/{id}/reactiver", vacataireId));

        // Assert
        response.andExpect(status().isNotFound()) // Géré par @ExceptionHandler
                .andExpect(content().string(errorMessage));
    }

    @Test
    void handleGenericException_shouldReturnInternalServerError() throws Exception {
        // Arrange: Forcer une exception générique depuis le service
        long vacataireId = 1L;
        given(vacataireService.getVacataire(vacataireId)).willThrow(new RuntimeException("Erreur BDD inattendue"));

        // Act
        ResultActions response = mockMvc.perform(get("/api/vacataires/{id}", vacataireId));

        // Assert
        response.andExpect(status().isInternalServerError()) // Statut HTTP 500 géré par le deuxième @ExceptionHandler
                .andExpect(content().string("Une erreur inattendue est survenue")); // Vérifie le message générique
    }

    @Test
    void contextLoads() {
        // Test vide pour vérifier le chargement du contexte Spring
    }
}