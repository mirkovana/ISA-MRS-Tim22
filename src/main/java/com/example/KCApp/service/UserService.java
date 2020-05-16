package com.example.KCApp.service;

import java.util.List;

import com.example.KCApp.beans.User;
import com.example.KCApp.DTO.PacijentDTO;

public interface UserService {
    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll ();
    //bilo je UserRequers, ali nama je ta klasa identicna sa PacijentDTO
	User save(PacijentDTO userRequest);
}
