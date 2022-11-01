package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.books.author.AuthorEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ScientificPaperTransformerTest {

    @TestConfiguration
    static class TransformerTestConfig {
        @Bean
        ScientificPaperTransformer scientificPaperTransformer() {
            return new ScientificPaperTransformer();
        }
    }

    @Autowired
    ScientificPaperTransformer transformer;

    @ParameterizedTest
    @ValueSource(strings = {"20-120-2999", "23-10-1990", "1990-123-122", "1999:20:30"})
    void transforming_to_entity_should_throw_an_exception_related_to_date(String date){
        //when
        ScientificPaperDTO dto = new ScientificPaperDTO(12L, "Test", "test", null,
                "austronomy", "test", true, 123, date, null);
        //when&then
        assertThrows(IllegalArgumentException.class, ()-> transformer.toEntity(dto, null));
    }

    @ParameterizedTest
    @ArgumentsSource(TransformingToEntitySuccessProvider.class)
    void transforming_dto_to_entity_should_return_valid_entity(ScientificPaperDTO dto, List<AuthorEntity> authors, ScientificPaperEntity entity){
        //when
        ScientificPaperEntity result = transformer.toEntity(dto, authors);

        //then
        assertEquals(entity, result);
    }

    @ParameterizedTest
    @ArgumentsSource(TransformingToDtoProviderSuccess.class)
    void transforming_entity_to_dto_should_return_valid_dto(ScientificPaperEntity entity, ScientificPaperDTO dto) {
        //when
        ScientificPaperDTO result = transformer.toDTO(entity);

        //then
        assertEquals(dto, result);
    }

    @Test
    void should_throw_an_exception_when_incorrect_field_of_study_provided() {
        //given
        ScientificPaperDTO dto = new ScientificPaperDTO(12L, "Test", "desc", null, "wrong", "test", false, 123, null, 1);
        //when && then
        assertThrows(IllegalArgumentException.class, ()-> transformer.toEntity(dto, null));
    }
}