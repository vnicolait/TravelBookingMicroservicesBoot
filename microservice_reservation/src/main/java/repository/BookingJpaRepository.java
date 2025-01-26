package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.BookingEntity;
import java.util.List;


public interface BookingJpaRepository extends JpaRepository<BookingEntity, Integer>{

	List<BookingEntity> findByIdUser(int idUser);
}
