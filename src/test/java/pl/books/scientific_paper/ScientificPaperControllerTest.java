package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ScientificPaperControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ScientificPaperService scientificPaperService;

    @Test
    void get_scientific_paper_by_id_should_return_response_OK_and_response_body_of_found_paper() throws Exception {
        //given
        Long id = 99L;
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(id, "Test", "Test", new ArrayList<>(), FieldOfStudy.ASTRONOMY.name(), "Test university", true, 123, LocalDate.of(2022, 12, 12).toString(), 1);
        Mockito.when(scientificPaperService.getScientificPaperById(id)).thenReturn(scientificPaperDTO);

        //when
        ResultActions perform = mockMvc.perform(get("/papers/" + id).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.field", equalTo("ASTRONOMY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pages", equalTo(123)));
    }

    @Test
    void adding_scientific_paper_with_no_authors_should_return_bad_request() throws Exception {
        //given
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(null, "test", "test", new ArrayList<>(), "astronomy", "string", true, 123, "1999-10-10", null);
        Mockito.when(scientificPaperService.addNewScientificPaper(scientificPaperDTO)).thenThrow(new IllegalArgumentException("Scientific paper has to have at least one author!"));
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
        Mockito.verify(scientificPaperService, Mockito.never()).addNewScientificPaper(Mockito.any());
    }

    @Test
    void adding_proper_scientific_paper_should_return_code_200_and_dto() throws Exception {
        //given
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(112L, "Test", "Test", List.of(new ScientificPaperAuthorDTO(1L)), "astronomy", "Test university", true, 123, "1999-10-10", null);
        Mockito.when(scientificPaperService.addNewScientificPaper(scientificPaperDTO)).thenReturn(scientificPaperDTO);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(112)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.university", equalTo("Test university")));
    }

    @Test
    void removing_non_existing_paper_should_return_not_found() throws Exception {
        //given
        long id = 12L;
        Mockito.when(scientificPaperService.removeScientificPaper(id)).thenThrow(new NoSuchElementException("No paper found with id: " + id));

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
        long id = 12L;
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(id, "Test", "Test", List.of(new ScientificPaperAuthorDTO(1L)), "astronomy", "Test university", true, 123, "1999-10-10", null);
        Mockito.when(scientificPaperService.removeScientificPaper(id)).thenReturn(scientificPaperDTO);

        //when
        ResultActions perform = mockMvc.perform(delete("/papers/" + id).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(12)));
    }


}