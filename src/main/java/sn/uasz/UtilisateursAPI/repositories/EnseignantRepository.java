package sn.uasz.UtilisateursAPI.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sn.uasz.UtilisateursAPI.entities.Enseignant;

@RepositoryRestResource
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
        import sn.uasz.demoJPA.entities.Enseignant;
import sn.uasz.demoJPA.repositories.EnseignantRepository;
import org.springframework.data.domain.PageRequest;

import java.util.stream.IntStream;


@Controller
public class EnseignantController {
    @Autowired
    private EnseignantRepository enseignantRepository;
    @GetMapping("/enseignant/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Enseignant> enseignants = enseignantRepository.findByPrenomContainsIgnoreCaseOrNomContainsIgnoreCase(keyword,keyword, PageRequest.of(page, size));

        model.addAttribute("listeEnseignants", enseignants.getContent());
        model.addAttribute("pages", IntStream.range(0, enseignants.getTotalPages()).toArray()); // Correction ici
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "enseignant/enseignants";
    }

    @RequestMapping(value = "/enseignant/admin/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String form(Model model) {
        model.addAttribute("enseignant", new Enseignant());
        return "/enseignant/formEnseignant";
    }
    @PostMapping("/enseignant/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Enseignant p) {
        enseignantRepository.save(p);
        return "redirect:/enseignant/index";
    }
    @RequestMapping("/enseignant/editEnseignant")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editEnseignant(Model model, @RequestParam(name ="id") Long id) {
        Enseignant enseignant=enseignantRepository.findById(id).orElse(null);
        if(enseignant==null) throw new RuntimeException("Enseignant introuvable !!!");
        model.addAttribute("enseignant", enseignant);

        return "/enseignant/editEnseignant";
    }
    @GetMapping("/enseignant/deleteEnseignant")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam(name ="id") Long id, String keyword, int page) {
        enseignantRepository.deleteById(id);
        return "redirect:/enseignant/index?page=" + page+"&keyword=" + keyword;
    }
}*/
