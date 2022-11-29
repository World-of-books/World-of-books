package pl.books.author;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private ModelMapper modelMapper;

    private AuthorRepository authorRepository;

    public AuthorEntity createAuthor(AuthorPublicationsDTO authorPublicationsDTO) {
        authorPublicationsDTO.setPublications(new HashSet<>());
        authorPublicationsDTO.setAudiobooks(new ArrayList<>());
        authorPublicationsDTO.setBooks(new ArrayList<>());
        AuthorEntity authorEntity = convertAuthorPublicationsDtoToAuthorEntity(authorPublicationsDTO);
        return authorRepository.save(authorEntity);
    }

    public AuthorPublicationsDTO getAuthorById(Long id) {
        AuthorEntity author = authorRepository.findById(id).orElse(null);// stream + converter
        return convertAuthorEntityToAuthorPublicationsDto(author);
    }

    public List<AuthorPublicationsDTO> getAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertAuthorEntityToAuthorPublicationsDto)
                .collect(Collectors.toList());
    }

    private AuthorPublicationsDTO convertAuthorEntityToAuthorPublicationsDto(AuthorEntity authorEntity){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorPublicationsDTO authorPublicationsDTO = new AuthorPublicationsDTO();
        authorPublicationsDTO = modelMapper.map(authorEntity, AuthorPublicationsDTO.class);
        return authorPublicationsDTO;
    }

    private AuthorEntity convertAuthorPublicationsDtoToAuthorEntity(AuthorPublicationsDTO authorPublicationsDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity = modelMapper.map(authorPublicationsDTO, AuthorEntity.class);
        return authorEntity;
    }

    public AuthorPublicationsDTO updateAuthor(Long id, AuthorPublicationsDTO authorDTO) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Author not found");
        });
        authorEntity.setFirstName(authorDTO.getFirstName());
        authorEntity.setLastName(authorDTO.getLastName());
        authorRepository.save(authorEntity);
        return convertAuthorEntityToAuthorPublicationsDto(authorEntity);
    }

    public String deleteAuthorById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Author not found");
        });
        authorRepository.deleteById(id);
        return "Author got deleted";
    }
}
