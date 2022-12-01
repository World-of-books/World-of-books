package pl.books;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.books.app_user.AppUser;
import pl.books.app_user.AppUserRepository;
import pl.books.app_user.UserRole;
import pl.books.app_user.UserRoleRepository;
import pl.books.audiobook.AudiobookEntity;
import pl.books.audiobook.AudiobookRepository;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;
import pl.books.borrow_publication.BorrowEntity;
import pl.books.borrow_publication.BorrowEntityRepository;
import pl.books.borrow_publication.PublicationType;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;
import pl.books.scientific_paper.ScientificPaperRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class Initializer {

    private final AuthorRepository authorRepository;
    private final ScientificPaperRepository scientificPaperRepository;
    private final AudiobookRepository audiobookRepository;
    private final UserRoleRepository userRoleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BorrowEntityRepository borrowEntityRepository;


    @PostConstruct
    public void run() {

        AuthorEntity hawking = new AuthorEntity(1L, "Stephen", "Hawking");
        authorRepository.save(hawking);

        AuthorEntity hengstberger = new AuthorEntity(2L, "Franz", "Hengstberger");
        authorRepository.save(hengstberger);

        AuthorEntity patterson = new AuthorEntity(3L, "Wade", "Patterson");
        authorRepository.save(patterson);

        AuthorEntity thomas = new AuthorEntity(4L, "Ralph", "Thomas");
        authorRepository.save(thomas);

        AuthorEntity grumezescu = new AuthorEntity(5L, "Alexandru", "Grumezescu");
        authorRepository.save(grumezescu);

        AuthorEntity dean = new AuthorEntity(6L, "Andrew", "Dean");
        authorRepository.save(dean);

        ScientificPaperEntity babyUniverses = new ScientificPaperEntity(7L, "Baby Universes And The Nonrenormalizability Of Gravity.", new HashSet<>(), "Baby Universes And The Nonrenormalizability Of Gravity.",
                FieldOfStudy.PHYSIC, "University", false, 189, LocalDate.of(1988, 5, 5), 10);
        hawking.getPublications().add(babyUniverses);
        babyUniverses.getAuthors().add(hawking);
        scientificPaperRepository.save(babyUniverses);

        ScientificPaperEntity absolute_radiometry = new ScientificPaperEntity(8L, "Absolute Radiometry", Set.of(hengstberger), "Electrically Calibrated Thermal Detectors of Optical Radiation",
                FieldOfStudy.PHYSIC, "National Physical Research Laboratory, Council for Scientific and Industrial Research, Pretoria, South Africa", false, 117, LocalDate.of(1989, 1, 1), 10);
        hengstberger.getPublications().add(absolute_radiometry);
        scientificPaperRepository.save(absolute_radiometry);

        ScientificPaperEntity accelerator_health_physics = new ScientificPaperEntity(9L, "Accelerator Health Physics", Set.of(patterson, thomas), "Accelerator Health Physics tackles the importance of health physics in the field of nuclear physics, especially to those involved with the use of particle accelerators.",
                FieldOfStudy.PHYSIC, "LAWRENCE BERKELEY LABORATORY, UNIVERSITY OF CALIFORNIA, BERKELEY, CALIFORNIA", false, 92, LocalDate.of(1971, 1, 1), 10);
        patterson.getPublications().add(accelerator_health_physics);
        thomas.getPublications().add(accelerator_health_physics);
        scientificPaperRepository.save(accelerator_health_physics);

        ScientificPaperEntity nanoarchitectonics_in_biomedicine = new ScientificPaperEntity(10L, "Nanoarchitectonics in Biomedicine", Set.of(grumezescu), "The book brings together recent applications and discusses the advantages and disadvantages of each process, offering international perspectives on the technologies based on these findings",
                FieldOfStudy.HEALTH, "Faculty of Applied Chemistry and Materials Science, University Politehnica of Bucharest, Bucharest, Romani", false, 54, LocalDate.of(2019, 1, 1), 10);
        grumezescu.getPublications().add(nanoarchitectonics_in_biomedicine);
        scientificPaperRepository.save(nanoarchitectonics_in_biomedicine);

        ScientificPaperEntity wages_and_earnings = new ScientificPaperEntity(11L, "Wages and Earnings", Set.of(dean), "Wages and Earnings is a review of statistical sources, both official and non-official, on wages and earnings in Britain.",
                FieldOfStudy.MATHEMATICS, "Organisation for Economic Cooperation and Development formerly at the National Institute of Economic and Social Research", false, 161, LocalDate.of(1980, 1, 1), 10);
        dean.getPublications().add(wages_and_earnings);
        scientificPaperRepository.save(wages_and_earnings);


        AudiobookEntity panTadeuszAudio = new AudiobookEntity(12L, "Pan Tateusz", Set.of(hawking), "lektura szkolna", false, 43200, LocalDate.of(2005, 02, 14), "3424", "PWN");
        audiobookRepository.save(panTadeuszAudio);
        hawking.getAudiobooks().add(panTadeuszAudio);
        authorRepository.save(hawking);

        UserRole user = new UserRole(13L, "user");
        UserRole admin = new UserRole(14L, "admin");
        userRoleRepository.save(user);
        userRoleRepository.save(admin);

        AppUser appUserPioter = new AppUser(15L, "pioter", "pioter@pioter.com", "pioter", "88051099999", LocalDate.of(1988, 05, 10), List.of(user));
        appUserPioter.setUserPassword(passwordEncoder.encode(appUserPioter.getUserPassword()));
        appUserRepository.save(appUserPioter);

        BorrowEntity borrow_baby_universe_pioter = new BorrowEntity(16L, babyUniverses, PublicationType.SCIENTIFIC_PAPER, appUserPioter, LocalDate.of(2022, 10, 27), LocalDate.of(2022, 11, 20));
        borrowEntityRepository.save(borrow_baby_universe_pioter);

        BorrowEntity borrow_tadeusz_audio_pioter = new BorrowEntity(17L, panTadeuszAudio, PublicationType.AUDIOBOOK, appUserPioter, LocalDate.of(2022, 11, 27), LocalDate.of(2022, 12, 20));
        borrowEntityRepository.save(borrow_tadeusz_audio_pioter);

        List<BorrowEntity> all = borrowEntityRepository.findAll();
        System.out.println(all.get(0));
        System.out.println(all.get(1));

        AppUser appUserAdmin = new AppUser(18L, "admin", "admin@admin.com", "admin", "88101000009", LocalDate.of(1988, 10, 10), List.of(user, admin));
        appUserAdmin.setUserPassword(passwordEncoder.encode(appUserAdmin.getUserPassword()));
        appUserRepository.save(appUserAdmin);

        AuthorEntity varghese = authorRepository.save(new AuthorEntity("George", "Varghese"));

        ScientificPaperEntity network_algorithmics = scientificPaperRepository.save(new ScientificPaperEntity("Network Algorithmics", Set.of(), "An Interdisciplinary Approach to Designing Fast Networked Devices",
                FieldOfStudy.COMPUTER_SCIENCE, "University of California, San Diego", false, 465, LocalDate.of(2005, 1, 1)));
        network_algorithmics.setAuthors(Set.of(varghese));
        scientificPaperRepository.save(network_algorithmics);

        AuthorEntity s_long = authorRepository.save(new AuthorEntity("Sarah", "Long"));

        ScientificPaperEntity pediatricInfectiousDiseases = scientificPaperRepository.save(new ScientificPaperEntity("Principles and Practice of Pediatric Infectious Diseases", Set.of(), "Presented publication provides the comprehensive and actionable coverage you need to understand, diagnose, and manage the ever-changing, high-risk clinical problems caused by infectious diseases in children and adolescents.",
                FieldOfStudy.HEALTH, "University College of Medicine, Philadelphia", false, 1712, LocalDate.of(2012, 1, 1)));
        pediatricInfectiousDiseases.setAuthors(Set.of(s_long));
        scientificPaperRepository.save(pediatricInfectiousDiseases);

        AuthorEntity kaminski = authorRepository.save(new AuthorEntity("Amy", "Kaminski"));

        ScientificPaperEntity space_science = scientificPaperRepository.save(new ScientificPaperEntity("Space Science and Public Engagement", Set.of(), "Presented publication critically examines the many dimensions of public engagement with space science by exploring case studies that show a spectrum of public engagement formats",
                FieldOfStudy.ASTRONOMY, "University Unknown", false, 273, LocalDate.of(2021, 1, 1)));
        space_science.setAuthors(Set.of(kaminski));
        scientificPaperRepository.save(space_science);

        AuthorEntity zhao = authorRepository.save(new AuthorEntity("Yanchang", "Zhao"));
        AuthorEntity cen = authorRepository.save(new AuthorEntity("Yonghua", "Cen"));

        ScientificPaperEntity data_mining = scientificPaperRepository.save(new ScientificPaperEntity("Data Mining Applications with R", Set.of(), "Data Mining Applications with R is a great resource for researchers and professionals to understand the wide use of R, a free software environment for statistical computing and graphics, in solving different problems in industry.",
                FieldOfStudy.MATHEMATICS, "University of Science and Technology, China", false, 470, LocalDate.of(2014, 1, 1)));
        data_mining.setAuthors(Set.of(zhao, cen));
        scientificPaperRepository.save(data_mining);

        AuthorEntity sergeev = authorRepository.save(new AuthorEntity("G.B.", "Sergeev"));

        ScientificPaperEntity nanochemistry = scientificPaperRepository.save(new ScientificPaperEntity("Nanochemistry", Set.of(), "This book is devoted to nanochemistry: a branch of the actively developing interdisciplinary field of nanoscience. This branch of science studies the processes to production and reactions of nanoparticles and their compounds",
                FieldOfStudy.CHEMISTRY, "University of Moscow, Russian Federation", false, 249, LocalDate.of(2006, 1, 1)));
        nanochemistry.setAuthors(Set.of(sergeev));
        scientificPaperRepository.save(nanochemistry);

    }
}
