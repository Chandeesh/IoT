package sanitizerdispenser.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sanitizerdispenser.model.SanitizerLevel;


public interface SanitizerLevelRepository extends MongoRepository<SanitizerLevel,String>{

}