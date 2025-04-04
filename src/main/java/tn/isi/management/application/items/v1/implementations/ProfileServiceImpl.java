package tn.isi.management.application.items.v1.implementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Profile;
import tn.isi.management.domain.repositories.ProfileRepository;
import tn.isi.management.service.items.ProfileService;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public Profile addProfile(Profile profile) {
        return profileRepository.createProfile(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(Integer id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Profile getProfileById(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public Profile getProfileByLabel(String label) {
        return profileRepository.findByLabel(label).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
