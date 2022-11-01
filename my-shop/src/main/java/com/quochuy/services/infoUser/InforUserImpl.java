package com.quochuy.services.infoUser;

import com.quochuy.models.InfoUser;
import com.quochuy.repositorys.InfoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class InforUserImpl implements IInforUser{
    @Autowired
    private InfoUserRepository infoUserRepository;

    @Override
    public List<InfoUser> findAll() {
        return infoUserRepository.findAll();
    }

    @Override
    public InfoUser getById(Long id) {
        return infoUserRepository.getById(id);
    }

    @Override
    public Optional<InfoUser> findById(Long id) {
        return infoUserRepository.findById(id);
    }

    @Override
    public InfoUser save(InfoUser infoUser) {
        return infoUserRepository.save(infoUser);
    }

    @Override
    public void remove(Long id) {

    }
}
