package pl.books.borrow_publication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowEntityController {

   private final BorrowEntityService borrowEntityService;

    public BorrowEntityController(BorrowEntityService borrowEntityService) {
        this.borrowEntityService = borrowEntityService;
    }

    @GetMapping
    List<BorrowDTO> getAll() {
        return borrowEntityService.getAllBorrows();
    }
}
