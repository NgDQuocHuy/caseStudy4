package com.quochuy.services.locationRegion;


import com.quochuy.models.LocationRegion;
import com.quochuy.repositorys.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationRegionServiceImpl implements ILocationRegionService {

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public LocationRegion getById(Long id) {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return locationRegionRepository.findById(id);
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {
        return locationRegionRepository.save(locationRegion);
    }

    @Override
    public void remove(Long id) {
        locationRegionRepository.deleteById(id);
    }
}
