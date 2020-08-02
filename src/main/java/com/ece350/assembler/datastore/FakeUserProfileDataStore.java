package com.ece350.assembler.datastore;

import com.ece350.assembler.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("4e0dbeba-0e0d-4345-b9b5-a37195bdeeb0"),"maxsmith",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("a363db3a-acdc-4e25-b393-2c2a22022bd5"),"lebronjames",null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
