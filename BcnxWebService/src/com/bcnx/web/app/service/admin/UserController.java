package com.bcnx.web.app.service.admin;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.Encryptography;
import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.PasswdGenerator;
import com.bcnx.web.app.service.RoleService;
import com.bcnx.web.app.service.SendMailService;
import com.bcnx.web.app.service.UserService;
import com.bcnx.web.app.service.entity.Error;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Role;
import com.bcnx.web.app.service.entity.User;
@Path("/user")
public class UserController {
	@RolesAllowed("ADM")
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@FormParam("userId") String userId,
			@FormParam("name") String name, @FormParam("email") String email,
			@FormParam("memId") String memId, @FormParam("roleId") String roleId) {
		UserService userService = (UserService) BcnxApplicationContext
				.getBean("userService");
		MemberService memberService = (MemberService) BcnxApplicationContext
				.getBean("memberService");
		RoleService roleService = (RoleService) BcnxApplicationContext
				.getBean("roleService");
		PasswdGenerator generator = (PasswdGenerator) BcnxApplicationContext
				.getBean("passwdGenerator");
		Encryptography encryptography = (Encryptography) BcnxApplicationContext
				.getBean("tripleDES");
		SendMailService sendMailService = (SendMailService) BcnxApplicationContext
				.getBean("sendMailService");
		Member member = new Member();
		member.setMemId(memId);
		member = memberService.getMember(member);
		Role role = new Role();
		role.setRoleId(roleId);
		role = roleService.getRole(role);
		User user = new User();
		user.setUserId(userId);
		String passwd = generator.generate();
		sendMailService.sendMail(email,passwd);
		// send email for the password notification
		user.setPasswd(encryptography.encrypt(passwd));
		user.setName(name);
		user.setEmail(email);
		user.setMember(member);
		user.setRole(role);
		userService.save(user);
		String result = user.toString();
		return Response.status(200).entity(result).build();
	}
	@RolesAllowed("ADM")
	@GET
	@Path("/get/{userId}")
	@Produces("application/json")
	public Response getUser(@PathParam("userId") String userId){
		UserService service = (UserService) BcnxApplicationContext.getBean("userService");
		User user = new User();
		user.setUserId(userId);
		user = service.getUser(user);
		return Response.status(200).entity(user).build();
	}
	@RolesAllowed("ADM")
	@GET
	@Path("/get/{first}/{max}")
	@Produces("application/json")
	public Response getUsers(@PathParam("first") String first,
			@PathParam("max") String max) {
		UserService service = (UserService) BcnxApplicationContext
				.getBean("userService");
		List<User> users = service.getUsers(Integer.parseInt(first),
				Integer.parseInt(max));
		return Response.status(200).entity(users).build();
	}
	@RolesAllowed("ADM")
	@GET
	@Path("/get/{userId}/{first}/{max}")
	@Produces("application/json")
	public Response getUsers(@PathParam("userId") String userId,
			@PathParam("first") String first, @PathParam("max") String max) {
		UserService service = (UserService) BcnxApplicationContext
				.getBean("userService");
		User user = new User();
		user.setUserId(userId);
		List<User> users = service.getUsers(user, Integer.parseInt(first), Integer.parseInt(max));
		return Response.status(200).entity(users).build();
	}
	@RolesAllowed("ADM")
	@PUT
	@Path("/update")
	@Produces("application/json")
	public Response updatePasswd(@FormParam("userId") String userId,
			@FormParam("passwd") String passwd,
			@FormParam("nPasswd") String nPasswd,
			@FormParam("cPasswd") String cPasswd) {
		UserService userService = (UserService) BcnxApplicationContext
				.getBean("userService");
		Encryptography encryptography = (Encryptography) BcnxApplicationContext
				.getBean("tripleDES");
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		String psswd = encryptography.encrypt(passwd);
		user.setPasswd(psswd);
		
		Error error = new Error();
		error.setCode("1001");
		error.setMessage("Can not process data");
		if (!nPasswd.equals(cPasswd))
			return Response.status(500).entity(error).build();
		userService.updatePasswd(user);
		return Response.status(200).entity(user).build();
	}
}
