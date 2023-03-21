package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Incluye_Taller;
import com.example.demo.repository.RepositorioIncluye_Taller;

@Service
@Transactional
public class Incluye_TallerServiceImpl implements Incluye_TallerService{

	@Autowired
	RepositorioIncluye_Taller repIncluye_Taller;
	
	@Override
    public List<Incluye_Taller> buscarTodosLosIncluye_Taller(){
    	return repIncluye_Taller.findAll();
    }

    @Override
    public Incluye_Taller BuscarIncluye_TallerPorId(int id) {
    	return repIncluye_Taller.findById(id).get();
    }

    @Override
    public void Guardar(Incluye_Taller incluye_Taller) {
    	repIncluye_Taller.save(incluye_Taller);
    }

    @Override
    public void BorrarIncluye_TallerPorId(int id) {
    	repIncluye_Taller.deleteById(id);
    }
}
