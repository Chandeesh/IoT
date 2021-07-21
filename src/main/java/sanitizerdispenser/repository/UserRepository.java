package sanitizerdispenser.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sanitizerdispenser.model.UserRegister;

public interface UserRepository extends MongoRepository<UserRegister,String>{

}
