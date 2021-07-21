package sanitizerdispenser.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sanitizerdispenser.model.Devices;

public interface DeviceRepository extends MongoRepository<Devices,String>{

}
