package pl.books.author;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;


    public AuthorEntity createAuthor(AuthorDTO authorDTO) {
        AuthorEntity authorEntity = convertAuthorDtoToAuthorEntity(authorDTO);
        return authorRepository.save(authorEntity);
    }

    public AuthorDTO getAuthorById(Long id) {
        AuthorEntity author = authorRepository.findById(id).orElse(null);// stream + converter
        return convertAuthorEntityToAuthorDto(author);
    }


    public List<AuthorDTO> getAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertAuthorEntityToAuthorDto)
                .collect(Collectors.toList());
    }

    private AuthorDTO convertAuthorEntityToAuthorDto(AuthorEntity authorEntity){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO = modelMapper.map(authorEntity, AuthorDTO.class);
        return authorDTO;
    }

    private AuthorEntity convertAuthorDtoToAuthorEntity(AuthorDTO authorDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity = modelMapper.map(authorDTO, AuthorEntity.class);
        return authorEntity;
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorRepository.findById(id).get();
        AuthorDTO authorDTOToUpdate = convertAuthorEntityToAuthorDto(authorEntity);
        authorDTOToUpdate.setFirstName(authorDTO.getFirstName());
        authorDTOToUpdate.setLastName(authorDTO.getLastName());
        authorRepository.save(convertAuthorDtoToAuthorEntity(authorDTOToUpdate));
        return authorDTOToUpdate;
    }

    public String deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
        return "Author got deleted";
    }
}
