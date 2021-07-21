package sanitizerdispenser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sanitizerdispenser.model.Devices;
import sanitizerdispenser.model.SanitizerLevel;
import sanitizerdispenser.model.UpdateLevel;
import sanitizerdispenser.model.User;
import sanitizerdispenser.model.UserData;

@RestController
@CrossOrigin()
@RequestMapping(path = "/sanitizerres")
public class ContollerClass {

	@Autowired
	Services userservice;

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public UserDetails loginUser(@RequestBody User user) {
		return userservice.login(user.getEmail());
	}

	@PutMapping("/adddevice")
	public ResponseEntity<Object> registerDevice(@RequestBody Devices device) {
		return userservice.regDevice(device);
	}

	@PutMapping("/registeruser")
	public ResponseEntity<Object> registerUser(@RequestBody UserData userdata) {
		return userservice.registerNewUser(userdata);
	}
	
	@GetMapping("/getDetails")
	public List<Devices> confirmuser() {
		return userservice.getLocations();
	}
	
	@PutMapping("/updatelevel")
	public ResponseEntity<Object> updatelevel(@RequestBody UpdateLevel updatelevel) {
		return userservice.updateLevel(updatelevel);
	}
	
	@GetMapping("/getLevel")
	public ResponseEntity<SanitizerLevel> getLevel(@RequestParam int id) {
		return userservice.getLevel(id);
	}

}