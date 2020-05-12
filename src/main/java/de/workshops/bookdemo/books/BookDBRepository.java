package de.workshops.bookdemo.books;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Repository
public interface BookDBRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByIsbn(String isbn);

}
