package sanitizerdispenser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.security.core.userdetails.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.MongoClients;

import sanitizerdispenser.model.Devices;
import sanitizerdispenser.model.SanitizerLevel;
import sanitizerdispenser.model.UpdateLevel;
import sanitizerdispenser.model.UserData;
import sanitizerdispenser.model.UserRegister;
import sanitizerdispenser.repository.DeviceRepository;
import sanitizerdispenser.repository.SanitizerLevelRepository;
import sanitizerdispenser.repository.UserRepository;

@Service
public class UserService implements Services {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	@Autowired
	private UserRepository reps;

	@Autowired
	private DeviceRepository devicereps;

	@Autowired
	private SanitizerLevelRepository sanitlevreps;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private List<UserRegister> local;
	private Iterator<UserRegister> itr;
	
	@Override
	public UserDetails login(String username) {
		boolean status = false;
		String email = "";
		String password = "";
		local = reps.findAll();
		itr = local.iterator();
		while (itr.hasNext()) {
			UserRegister udata = itr.next();
			email = udata.getEmail();
			password = udata.getPassword();
			if (udata.isEnabled()) {
				if (email.equalsIgnoreCase(username)) {
					status = true;
					break;
				}
			}
		}
		if (status) {
			return new User(username, password, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	@Override
	public ResponseEntity<Object> registerNewUser(UserData userdata) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		local = reps.findAll();
		itr = local.iterator();
		while (itr.hasNext()) {
			UserRegister existingdata = itr.next();
			if (existingdata.getEmail().equals(userdata.getEmail()) && existingdata.isEnabled()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else if(existingdata.getEmail().equals(userdata.getEmail()) && getHours(existingdata.getTime())>24){
				reps.delete(existingdata);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		String token = UUID.randomUUID().toString();
		UserRegister data = new UserRegister();
		data.setEmail(userdata.getEmail());
		data.setName(userdata.getName());
		data.setPassword(encoder.encode(userdata.getPassword()));
		data.setEnabled(true);
		data.setTime(sdf.format(timestamp));
		data.setToken(token);
		reps.save(data);
		try {
			return ResponseEntity.status(200).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<Object> regDevice(Devices devices) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Devices dev = new Devices();
		SanitizerLevel sanitizerlevel = new SanitizerLevel();
		dev.setAddress(devices.getAddress());
		dev.setCity(devices.getCity());
		dev.setId(devices.getId());
		dev.setStatus(devices.isStatus());
		devicereps.save(dev);
		sanitizerlevel.setId(devices.getId());
		sanitizerlevel.setLevelPercent(100);
		sanitizerlevel.setStatus(dev.isStatus());
		sanitizerlevel.setTime(sdf.format(timestamp));
		sanitlevreps.save(sanitizerlevel);
		return ResponseEntity.status(200).body(dev);
	}
	
	@Override
	public ResponseEntity<SanitizerLevel> getLevel(@RequestParam int id) {
		try {
			Query query = new Query(Criteria.where("_id").is(id));
			MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://api_beehive"), "UserData");
			SanitizerLevel locOb = mongoOps.findOne(query, SanitizerLevel.class);
			return ResponseEntity.status(200).body(locOb);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void sendMail(String alert) {
		try {
			MimeMessage message = new MimeMessage(javaMailService());
			message.setFrom(new InternetAddress("chandeesh64@yahoo.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("dariusmatt07@gmail.com"));
			message.setSubject("Sanitizer Level Running Low");
			message.setText(alert);
			Transport.send(message);
		} catch (MessagingException ex) {
			throw new NullPointerException();
		}
	}

	public Session javaMailService() {

		String host = "smtp.mail.yahoo.com";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.trust", "*");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("chandeesh64@yahoo.com", "sepxlqetqtiacptu");
			}
		});
		session.setDebug(true);
		return session;
	}

	@Override
	public List<Devices> getLocations() {
		// TODO Auto-generated method stub
		return devicereps.findAll();
	}

	@Override
	public ResponseEntity<Object> updateLevel(UpdateLevel updatelevel) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			Query query = new Query(Criteria.where("_id").is(1));
			MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://api_beehive"), "UserData");
			SanitizerLevel locOb = mongoOps.findOne(query, SanitizerLevel.class);
			Devices dev = mongoOps.findOne(query, Devices.class);
			locOb.setLevelPercent(Integer.parseInt(updatelevel.getPercent()));
			locOb.setTime(sdf.format(timestamp));
			if(Integer.parseInt(updatelevel.getPercent())<20) {
				locOb.setStatus(false);
				sendMail("Santizer level at " +dev.getAddress()+ " is running low. Please consider refilling it");
			} else {
				locOb.setStatus(true);
			}
			sanitlevreps.save(locOb);
			return ResponseEntity.status(200).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public long getHours(String date) {
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Date d1 = sdf.parse(sdf.format(timestamp));
			Date d2 = sdf.parse(date);
			long difference_In_Time = d1.getTime() - d2.getTime();
			long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
			return difference_In_Days * 24 + difference_In_Hours;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
