package al.golocal.service;

import al.golocal.dto.SiteDto;
import al.golocal.entity.Address;
import al.golocal.entity.Site;
import al.golocal.mapper.GenericMapper;
import al.golocal.repository.AddressRepository;
import al.golocal.repository.SiteRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;
    private final GenericMapper genericMapper;
    private final AddressRepository addressRepository;
    private final UserService userService;

    public SiteDto getSiteById(Long id) {
        SiteDto sp = null;
        try {
            sp = genericMapper.toDto(siteRepository.findById(id).orElse(null), SiteDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sp;
    }
    public List<SiteDto> getSiteByCategoryId(Long categoryId) {
        return genericMapper.toDtoList(siteRepository.findSiteByCategoryId(categoryId), SiteDto.class);
    }

    public List<SiteDto> getSiteByUser() {
        return genericMapper.toDtoList(siteRepository.findByUserId(userService.getUserId()), SiteDto.class);
    }

    public List<SiteDto> getAllActiveSites() {
        return genericMapper.toDtoList(siteRepository.findSiteByStatus(1), SiteDto.class);
    }

    public SiteDto save(SiteDto siteDto) {
        Address address = siteDto.getAddress();
        if(address != null) {
            try {
                addressRepository.save(address);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(siteDto.getStatus() == null) {
            siteDto.setStatus(1);
        }
        try {
            al.golocal.entity.Site s = genericMapper.toEntity(siteDto, al.golocal.entity.Site.class);
            siteDto = genericMapper.toDto(siteRepository.save(s), SiteDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return siteDto;
    }

    public SiteDto update(SiteDto siteDto) {
        al.golocal.entity.Site sp = siteRepository.save(genericMapper.toEntity(siteDto, al.golocal.entity.Site.class));
        return genericMapper.toDto(sp, SiteDto.class);
    }

    @Transactional
    public void delete(Long id) {
        siteRepository.deleteById(id);
    }

    @Transactional
    public void disable(Long id) {
        try {
            siteRepository.disableById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void enable(Long id) {
        try {
            siteRepository.enableById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Haversine formula to calculate distance between two points on the Earth
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    // Method to find sites within the radius and sort them by distance
    public List<SiteDto> getSitesWithinRadius(double latitude, double longitude, double radius) {
        List<Site> allSites = new ArrayList<>();
        siteRepository.findAll().forEach(site -> {
            // Adding each site to the ArrayList
            allSites.add(site);
        });
        List<Site> filteredSites = allSites.stream()
                .filter(site -> {
                    double distance = haversine(latitude, longitude, site.getAddress().getLatitude(), site.getAddress().getLongitude());
                    return distance <= radius; // Filter sites within the radius
                })
                .sorted(Comparator.comparingDouble(site -> haversine(latitude, longitude, site.getAddress().getLatitude(), site.getAddress().getLongitude())))
                .collect(Collectors.toList()); // Sort by distance
        return genericMapper.toDtoList(filteredSites, SiteDto.class);
    }
}
