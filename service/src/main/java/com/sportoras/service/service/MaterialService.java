package com.sportoras.service.service;

import com.sportoras.database.entity.Material;
import com.sportoras.database.repository.MaterialRepository;
import com.sportoras.service.dto.Material.MaterialDto;
import com.sportoras.service.dto.productDto.ProductBasicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "materials")
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MaterialService {

    private final MaterialRepository materialRepository;

    public Material findById(Long materialId) {
        return materialRepository.findMaterialById(materialId);
    }

    @Cacheable
    public List<MaterialDto> allMaterials() {
        return materialRepository.findAll().stream()
                .map(it -> new MaterialDto(it.getId(), it.getName(), it.getDescription(),it.getProducts()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Material saveMaterial(MaterialDto materialDto) {
        return materialRepository.save(Material.builder()
                .name(materialDto.getName())
                .description(materialDto.getDescription())
                .build());
    }
}