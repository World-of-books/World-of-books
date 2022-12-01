package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.books.WorldOfBooksApplication;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WorldOfBooksApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScientificPaperIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ScientificPaperRepository scientificPaperRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void requesting_for_all_scientific_papers_should_return_an_empty_list_if_no_papers_are_present() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/papers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", equalTo(Collections.emptyList())));
    }

    @Test
    void adding_scientific_paper_with_no_authors_should_return_bad_request() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(post("/papers").contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                  "authors": [
                                  ],
                                  "description": "test",
                                  "field": "astronomy",
                                  "forAdults": true,
                                  "name": "test",
                                  "pages": 123,
                                  "publishedDate": "1999-10-10",
                                  "university": "string"
                                }
                """));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Bad Request")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", equalTo("Scientific paper has to have at least one author!")));
    }

    @ParameterizedTest
    @ArgumentsSource(ScientificPaperControllerNotValidArgumentsProvider.class)
    void should_return_bad_request_when_not_valid_paper_provided(String json, String[] name, String[] description,
                                                                 String[] authors, String[] field, String[] university,
                                                                 String[] isForAdults, String[] pages, String[] publishedDate) throws Exception {

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/papers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Bad Request")));
        if (name != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.name").value(containsInAnyOrder(name)));
        if (description != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.description").value(containsInAnyOrder(description)));
        if (authors != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.authors").value(containsInAnyOrder(authors)));
        if (field != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.field").value(containsInAnyOrder(field)));
        if (university != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.university").value(containsInAnyOrder(university)));
        if (isForAdults != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.isForAdults").value(containsInAnyOrder(isForAdults)));
        if (pages != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.pages").value(containsInAnyOrder(pages)));
        if (publishedDate != null)
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.details.publishedDate").value(containsInAnyOrder(publishedDate)));
    }

    @Test
    void adding_proper_scientific_paper_should_return_code_200_and_dto() throws Exception {
        //given
        authorRepository.save(new AuthorEntity());

        //when
        ResultActions perform = mockMvc.perform(post("/papers").contentType(MediaType.APPLICATION_JSON).content("""
                {
                  "authors": [
                    {
                       "id": 1
                    }
                  ],
                  "description": "Test",
                  "field": "astronomy",
                  "forAdults": true,
                  "id": 112,
                  "name": "Test",
                  "pages": 123,
                  "publishedDate": "1999-10-10",
                  "university": "Test university"
                }
                """));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.university", equalTo("Test University")));
    }

    @Test
    void removing_non_existing_paper_should_return_not_found() throws Exception {
        //given
        long id = 12L;

        //when
        ResultActions perform = mockMvc.perform(delete("/papers/" + id).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Not Found")));
    }

    @Test
    void removing_existing_paper_should_return_code_ok_and_dto() throws Exception {
        //given
        long id = 2L;
        AuthorEntity author = authorRepository.save(new AuthorEntity(1L));
        ScientificPaperEntity scientificPaper = new ScientificPaperEntity(id, "Test", new HashSet<>(), "Test", FieldOfStudy.ASTRONOMY, "Test university", true, 123, LocalDate.of(2023, 1, 1), 1);
        scientificPaper.getAuthors().add(author);
        author.getPublications().add(scientificPaper);
        scientificPaperRepository.save(scientificPaper);


        //when
        ResultActions perform = mockMvc.perform(delete("/papers/" + id).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(2)));
    }
}
