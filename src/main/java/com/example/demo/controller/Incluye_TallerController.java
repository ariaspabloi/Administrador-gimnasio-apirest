package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Incluye_Taller;
import com.example.demo.service.Incluye_TallerService;

@RestController
@RequestMapping("/incluyen_taller")
public class Incluye_TallerController {
	
	@Autowired
	Incluye_TallerService incluye_TallerService;
	
	@GetMapping("")
	public List<Incluye_Taller> list(){
		return incluye_TallerService.buscarTodosLosIncluye_Taller();
	}

}
