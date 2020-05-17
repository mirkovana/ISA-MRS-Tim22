package com.example.KCApp.service;

import java.util.List;
import com.example.KCApp.beans.Authority;


public interface AuthorityService {
	List<Authority> findById(Integer id);
	List<Authority> findByname(String name);
	Authority findOne(Integer id);
}
