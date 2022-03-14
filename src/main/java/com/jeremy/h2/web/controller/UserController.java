package com.jeremy.h2.web.controller;

import com.jeremy.h2.web.entity.Users;
import com.jeremy.h2.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Api(tags = "User")
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

	private static final String json = "application/json;charset=UTF-8";
	private final AtomicInteger atomicInteger = new AtomicInteger(0);
	
	@Autowired
	private UserService userService;

	@ApiOperation(value = "取得使用者", notes = "列出所有使用者")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value="/getAllUsers", produces = json)
	public List<Users> getAllUsers(){
		return userService.findAll();
	}

	@Retryable(include = {NoResultException.class},
			maxAttempts = 3,
			backoff = @Backoff(value = 2000))
	@GetMapping(value="/retry", produces = json)
	public Users retry(){
		int count = atomicInteger.incrementAndGet();
		log.info("count = " + count);
		if (count < 5) {
			throw new NoResultException();
		} else {
			return new Users();
		}
	}

	@GetMapping(value="/users/{id}" ,produces = json)
	public Users getById(@PathVariable("id") Long id) {
		return userService.getOne(id);

	}

	@GetMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		Optional<Users> userData = userService.findById(id);
		userService.delete(userData.get());
	}

	@Recover
	public Users recover(NoResultException e) {
		log.info("get NoResultException & return null");
		return null;
	}

}
