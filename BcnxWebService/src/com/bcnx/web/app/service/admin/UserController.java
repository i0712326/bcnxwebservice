package com.bcnx.web.app.service.admin;

import java.util.List;

import javax.ws.rs.Consumes;
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
import com.bcnx.web.app.service.entity.ErrMsg;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Role;
import com.bcnx.web.app.service.entity.User;
@Path("/user")
public class UserController {
	@POST
	@Path("/save")
	@Produces("application/json")
	@Consumes("application/json")
	public Response save(User user) {
		UserService userService = (UserService) BcnxApplicationContext
				.getBean("userService");
		MemberService memberService = (MemberService) BcnxApplicationContext
				.getBean("memberService");
		RoleService roleService = (RoleService) BcnxApplicationContext
				.getBean("roleService");

		SendMailService sendMailService = (SendMailService) BcnxApplicationContext
				.getBean("sendMailService");
		Member member = new Member();
		member.setMemId(user.getMember().getMemId());
		member = memberService.getMember(member);
		Role role = new Role();
		role.setRoleId(user.getRole().getRoleId());
		role = roleService.getRole(role);
		String passwd = PasswdGenerator.generate();
		sendMailService.sendMail(user.getEmail(),passwd);
		user.setPasswd(Encryptography.encrypt(passwd));
		user.setMember(member);
		user.setRole(role);
		userService.save(user);
		user.setPasswd("");
		return Response.status(200).entity(user).build();
	}
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
	@PUT
	@Path("/update")
	@Produces("application/json")
	public Response updatePasswd(@FormParam("userId") String userId,
			@FormParam("passwd") String passwd,
			@FormParam("nPasswd") String nPasswd,
			@FormParam("cPasswd") String cPasswd) {
		UserService userService = (UserService) BcnxApplicationContext.getBean("userService");
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		boolean check = Encryptography.checkPasswd(passwd, user.getPasswd());
		if(!check){
			return Response.status(401).entity(new ErrMsg("407","invalid current password")).build();
		}
		if (!nPasswd.equals(cPasswd))
			return Response.status(401).entity(new ErrMsg("406","new password and confirm password is no match")).build();
		userService.updatePasswd(user);
		return Response.status(200).entity(new ErrMsg("200","password change successful")).build();
	}
	// log on service
	@PUT
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logon(User user){
		UserService service =  (UserService) BcnxApplicationContext.getBean("userService"); 
		String passwd = user.getPasswd();
		user = service.getUser(user);
		boolean check = Encryptography.checkPasswd(passwd, user.getPasswd());
		String status = user.getStatus();
		int state = user.getState();
		int count = user.getCount();
		
		if(!check){
			return Response.status(401).entity(new ErrMsg("400","invalid userId/password")).build();
		}
		if(!status.equals("A"))
		{
			return Response.status(401).entity(new ErrMsg("401","inactive user")).build();
		}
		if(state!=0){
			return Response.status(401).entity(new ErrMsg("402","current user is loggin in")).build();
		}
		if(count ==0 ||(count%31 == 0)){
			return Response.status(401).entity(new ErrMsg("403","required user change password")).build();
		}
		count = count+1;
		user.setCount(count);
		service.update(user);
		user.setPasswd("");
		return Response.status(200).entity(new ErrMsg("200","logging in successful")).build();
	}
}
