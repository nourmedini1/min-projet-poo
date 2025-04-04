package tn.isi.management.service.items;

import org.springframework.stereotype.Service;
import tn.isi.management.domain.entities.Profile;

import java.util.List;

@Service
public interface ProfileService {

    Profile addProfile(Profile profile);

    Profile updateProfile(Profile profile);

    void deleteProfile(Integer id);

    Profile getProfileById(Integer id);

    Profile getProfileByLabel(String label);

    List<Profile> getAllProfiles();


}
