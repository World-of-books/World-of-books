package pl.books.borrow_publication;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/by-user/{id}")
    List<BorrowDTO> getAllByUserId(@PathVariable Long id){
        return borrowEntityService.getAllByUserId(id);
    }

    @GetMapping("currently-borrowed-by/{username}")
    List<BorrowDTO> getAllBorrowedByUser(@PathVariable String username) {
        return borrowEntityService.getAllBorrowedByUserId(username);
    }

    @PostMapping
    BorrowDTO borrowPublication(@RequestBody BorrowRequestDTO dto) {
        return borrowEntityService.borrowPublication(dto);
    }
}