package sanitizerdispenser;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import sanitizerdispenser.model.Devices;
import sanitizerdispenser.model.SanitizerLevel;
import sanitizerdispenser.model.UpdateLevel;
import sanitizerdispenser.model.UserData;

public interface Services {

	public UserDetails login(String username);
	public ResponseEntity<Object> regDevice(Devices device);
	public List<Devices> getLocations();
	public ResponseEntity<Object> updateLevel(UpdateLevel updatelevel);
	public ResponseEntity<Object> registerNewUser(UserData userdata);
	public ResponseEntity<SanitizerLevel> getLevel(int id);
}