package pl.books.borrow_publication;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowEntityService {

    private final BorrowEntityRepository borrowEntityRepository;
    private final BorrowTransformer borrowTransformer;

    public BorrowEntityService(BorrowEntityRepository borrowEntityRepository, BorrowTransformer borrowTransformer) {
        this.borrowEntityRepository = borrowEntityRepository;
        this.borrowTransformer = borrowTransformer;
    }

    List<BorrowDTO> getAllBorrows() {
        return borrowEntityRepository.findAll().stream()
                .map(borrowTransformer::toDto)
                .collect(Collectors.toList());
    }
}
