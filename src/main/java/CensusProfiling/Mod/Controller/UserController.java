package CensusProfiling.Mod.Controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import CensusProfiling.Mod.exception.NoAccessException;
import CensusProfiling.Mod.model.User;
import CensusProfiling.Mod.model.UserFamilyMember;
import CensusProfiling.Mod.secure.SecureService;
import CensusProfiling.Mod.services.UserService;



@RestController
public class UserController {
	
	public static final Logger LOG = LoggerFactory.getLogger(User.class);
	
	@Autowired
	private UserService service ;
	
	
	@Autowired
	SecureService appUserService;
	
	////User Registration
	@PostMapping("/registerUser")
	public User userRegister(@RequestBody User user) {
		LOG.info("User Register");
			return service.userRegister(user);
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User appUser) {
		LOG.info("loginController");
		LOG.info(appUser.getEmail());
		LOG.info(appUser.getPassword());

		User appUser2 = appUserService.login(appUser);
		if ((appUser.getEmail().equals(appUser2.getEmail())
				&& (appUser.getPassword().equals(appUser2.getPassword())))) {
			return appUser2;
		}
		return null;
	}
	
	@GetMapping("/logout")
	public String logout() {
		LOG.info("logoutController");
		return appUserService.logout();
	}
	////Add New Family Member
	@PostMapping("/addmember")
	public UserFamilyMember regMember(@RequestBody UserFamilyMember user) {
		LOG.info("Member add");
		if (appUserService.loginStatus().getRole().toString().equals("USER")) {
			return service.addMember(user);
		}
		else {
			throw new NoAccessException("You dont have access");
		}
	}
	
	////Delete Family Member by Name
	@DeleteMapping("/deleteMemberByFirstName/{name}")
	public void deleteMember(@PathVariable(value = "name") String name) {
		LOG.info("Delete Family Member by First name");
		if (appUserService.loginStatus().getRole().toString().equals("USER")) {
			service.deleteMember(name);
		}else {
			throw new NoAccessException("You dont have access");
		}
	}

	
	
	//get family members of particular User
		@GetMapping("/getAllMembers/{user_uid}")
		public List<UserFamilyMember> getFamilyMembers(@PathVariable(value = "user_uid") int user_uid) {
			LOG.info("Get All Family Members of a particular User ");		
			if (appUserService.loginStatus().getRole().toString().equals("USER")) {
				return service.findFamilyMembersByUserId(user_uid);
			}
			else {
				throw new NoAccessException("You dont have access");
			}
		}
		
		////update user profile
		@PutMapping("/updateInfo")
		public User updateUserProfile( @RequestBody User user) {
			LOG.info("Update User Info");
			if (appUserService.loginStatus().getRole().toString().equals("USER")) { 
			return service.updateUserProfile(user);
			}
			else {
				throw new NoAccessException("You dont have access");
			}
		}
		
		//// Update User's Family Member
		@PutMapping("/updateFamilyMemInfo")
		public UserFamilyMember updateMemInfo( @RequestBody UserFamilyMember member) {
			LOG.info("Update Member Details");
			if (appUserService.loginStatus().getRole().toString().equals("USER")) {
			return service.updateMemberInfo(member);
			}
			else {
				throw new NoAccessException("You dont have access");
			}
		}
		
	////Delete Family Member BY ID
		@DeleteMapping("/deleteMemberById/{mem_id}")
		public void deleteMember(@PathVariable("mem_id") int mem_id) {
			LOG.info("Delete Member Using ID");
			if (appUserService.loginStatus().getRole().toString().equals("USER")) {
				service.deleteMemberById(mem_id);
			}else {
				throw new NoAccessException("You dont have access");
			}
			
		}
		
	
	///Get Member by Id
	@GetMapping("/getMemberById/{mid}")
	public UserFamilyMember getMemberById(@PathVariable(value = "mid") int mid) {
		LOG.info("Getting Family Member by ID");
		if (appUserService.loginStatus().getRole().toString().equals("USER")) {
		return service.findMemberById(mid);
		}
		else {
			throw new NoAccessException("You dont have access");
		}
	}


	
	
	
	

}
