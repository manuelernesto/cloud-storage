package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final HashService hashService;
    private final CredentialMapper credentialMapper;

    public CredentialService(HashService hashService, CredentialMapper credentialMapper) {
        this.hashService = hashService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials(Integer userid) {
        return credentialMapper.getCredentials(userid);
    }

    public void saveCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(hashedPassword);

        if (credential.getCredentialid() != null)
            credentialMapper.update(credential);
        else
            credentialMapper.insert(credential);

    }
}
