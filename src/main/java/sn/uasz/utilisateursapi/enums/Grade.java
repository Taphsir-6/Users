package sn.uasz.utilisateursapi.enums;

/**
 * Représente les différents grades académiques possibles pour un enseignant.

 * Cette énumération permet de standardiser les titres professionnels
 * utilisés dans les entités, DTOs et traitements métier.

 * Chaque valeur représente un statut académique couramment rencontré
 * dans les universités francophones, notamment dans le contexte sénégalais.
 */
public enum Grade {

    /** Professeur titulaire d'une chaire (plus haut grade universitaire) */
    PROFESSEUR_TITULAIRE,

    /** Professeur sans chaire mais de rang élevé */
    PROFESSEUR,

    /** Professeur assimilé, généralement reconnu pour son expertise équivalente */
    PROFESSEUR_ASSIMILE,

    /** Enseignant vacataire, intervenant à temps partiel ou sur contrat temporaire */
    VACATAIRE,

    /** Assistant d’enseignement ou chercheur en début de carrière académique */
    ASSISTANT,

    /** Doctorant intervenant dans l’enseignement ou la recherche */
    DOCTORANT,

    /** Maître de conférences, rang intermédiaire entre assistant et professeur */
    MAITRE_DE_CONFERENCES,

    /** Chargé de cours, souvent non permanent ou contractuel avec mission pédagogique spécifique */
    CHARGE_DE_COURS
}
